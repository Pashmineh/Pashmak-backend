package com.kian.pashmak.service.mapper;

import com.kian.pashmak.domain.*;
import com.kian.pashmak.service.dto.DebtDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Debt and its DTO DebtDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface DebtMapper extends EntityMapper<DebtDTO, Debt> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.login", target = "userLogin")
    DebtDTO toDto(Debt debt);

    @Mapping(source = "userId", target = "user")
    Debt toEntity(DebtDTO debtDTO);

    default Debt fromId(Long id) {
        if (id == null) {
            return null;
        }
        Debt debt = new Debt();
        debt.setId(id);
        return debt;
    }
}
