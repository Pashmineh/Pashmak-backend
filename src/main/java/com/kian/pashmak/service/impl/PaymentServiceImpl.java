package com.kian.pashmak.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.kian.pashmak.domain.User;
import com.kian.pashmak.repository.UserRepository;
import com.kian.pashmak.service.PaymentService;
import com.kian.pashmak.domain.Payment;
import com.kian.pashmak.repository.PaymentRepository;
import com.kian.pashmak.service.dto.PaymentDTO;
import com.kian.pashmak.service.dto.push.Alert;
import com.kian.pashmak.service.dto.push.Notification;
import com.kian.pashmak.service.dto.push.Push;
import com.kian.pashmak.service.mapper.PaymentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;


import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
/**
 * Service Implementation for managing Payment.
 */
@Service
@Transactional
public class PaymentServiceImpl implements PaymentService {

    private final Logger log = LoggerFactory.getLogger(PaymentServiceImpl.class);

    private final PaymentRepository paymentRepository;
    private final UserRepository userRepository;
    private final RestTemplate restTemplate;

    private final PaymentMapper paymentMapper;

    public PaymentServiceImpl(PaymentRepository paymentRepository, UserRepository userRepository, RestTemplate restTemplate, PaymentMapper paymentMapper) {
        this.paymentRepository = paymentRepository;
        this.userRepository = userRepository;
        this.restTemplate = restTemplate;
        this.paymentMapper = paymentMapper;
    }

    /**
     * Save a payment.
     *
     * @param paymentDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public PaymentDTO save(PaymentDTO paymentDTO) {
        log.debug("Request to save Payment : {}", paymentDTO);
        Payment payment = paymentMapper.toEntity(paymentDTO);
        User user=userRepository.findOneWithAuthoritiesById(payment.getUser().getId()).get();
        user.setBalance(user.getBalance().add(payment.getAmount()));
        userRepository.save(user);
        payment = paymentRepository.save(payment);

        Push push= new Push();
        List<Notification> notif= new ArrayList<>();
        Notification notification= new Notification();
        notification.setTokens(Lists.newArrayList(user.getPushToken()));
        notification.setPlatform(user.getPlatform().equals("IOS")?1:2);
        notification.setPriority("high");
        notification.setTitle("پشمک");
        notification.setTopic("com.pashmak.app");
        notification.setMutableContent(true);
        Alert alert= new Alert();
        alert.setBody("پرداخت ثبت شد");
        alert.setActionLocKey("نمایش");
        notification.setAlert(alert);
        notif.add(notification);
        push.setNotifications(notif);
        try {
            System.out.println(new ObjectMapper().writeValueAsString(push));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        restTemplate.postForEntity(URI.create("http://178.62.20.28:8088/api/push"),push,Object.class);
        return paymentMapper.toDto(payment);
    }

    /**
     * Get all the payments.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PaymentDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Payments");
        return paymentRepository.findAll(pageable)
            .map(paymentMapper::toDto);
    }


    /**
     * Get one payment by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PaymentDTO> findOne(Long id) {
        log.debug("Request to get Payment : {}", id);
        return paymentRepository.findById(id)
            .map(paymentMapper::toDto);
    }

    /**
     * Delete the payment by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Payment : {}", id);
        paymentRepository.deleteById(id);
    }
}
