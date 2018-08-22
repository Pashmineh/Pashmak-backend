package com.kian.pashmak.service.mapper;

import com.kian.pashmak.domain.*;
import com.kian.pashmak.service.dto.DebtDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Debt and its DTO DebtDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DebtMapper extends EntityMapper<DebtDTO, Debt> {



    default Debt fromId(Long id) {
        if (id == null) {
            return null;
        }
        Debt debt = new Debt();
        debt.setId(id);
        return debt;
    }
}
