package com.kian.pashmak.service.mapper;

import com.kian.pashmak.domain.*;
import com.kian.pashmak.service.dto.PaymentDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Payment and its DTO PaymentDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface PaymentMapper extends EntityMapper<PaymentDTO, Payment> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.login", target = "userLogin")
    PaymentDTO toDto(Payment payment);

    @Mapping(source = "userId", target = "user")
    Payment toEntity(PaymentDTO paymentDTO);

    default Payment fromId(Long id) {
        if (id == null) {
            return null;
        }
        Payment payment = new Payment();
        payment.setId(id);
        return payment;
    }
}
