package com.kian.pashmak.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.kian.pashmak.domain.User;
import com.kian.pashmak.repository.UserRepository;
import com.kian.pashmak.service.DebtService;
import com.kian.pashmak.domain.Debt;
import com.kian.pashmak.repository.DebtRepository;
import com.kian.pashmak.service.dto.DebtDTO;
import com.kian.pashmak.service.dto.push.Alert;
import com.kian.pashmak.service.dto.push.Notification;
import com.kian.pashmak.service.dto.push.Push;
import com.kian.pashmak.service.mapper.DebtMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;


import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
/**
 * Service Implementation for managing Debt.
 */
@Service
@Transactional
public class DebtServiceImpl implements DebtService {

    private final Logger log = LoggerFactory.getLogger(DebtServiceImpl.class);

    private final DebtRepository debtRepository;
    private final UserRepository userRepository;
    private final RestTemplate restTemplate;

    private final DebtMapper debtMapper;

    public DebtServiceImpl(DebtRepository debtRepository, UserRepository userRepository, RestTemplate restTemplate, DebtMapper debtMapper) {
        this.debtRepository = debtRepository;
        this.userRepository = userRepository;
        this.restTemplate = restTemplate;
        this.debtMapper = debtMapper;
    }

    /**
     * Save a debt.
     *
     * @param debtDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public DebtDTO save(DebtDTO debtDTO) {
        log.debug("Request to save Debt : {}", debtDTO);
        Debt debt = debtMapper.toEntity(debtDTO);

        User user=userRepository.findOneWithAuthoritiesById(debt.getUser().getId()).get();
        user.setBalance(user.getBalance().subtract(debt.getAmount()));
        userRepository.save(user);
        debt = debtRepository.save(debt);

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
        alert.setBody(" بدهی بابت "+ debt.getReason().getTitle() +"به مبلغ "+ debt.getAmount()+" ثبت شد ");
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
        return debtMapper.toDto(debt);
    }

    /**
     * Get all the debts.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<DebtDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Debts");
        return debtRepository.findAll(pageable)
            .map(debtMapper::toDto);
    }


    /**
     * Get one debt by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<DebtDTO> findOne(Long id) {
        log.debug("Request to get Debt : {}", id);
        return debtRepository.findById(id)
            .map(debtMapper::toDto);
    }

    /**
     * Delete the debt by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Debt : {}", id);
        debtRepository.deleteById(id);
    }
}
