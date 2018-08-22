package com.kian.pashmak.service.mapper;

import com.kian.pashmak.domain.*;
import com.kian.pashmak.service.dto.CheckinDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Checkin and its DTO CheckinDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface CheckinMapper extends EntityMapper<CheckinDTO, Checkin> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.login", target = "userLogin")
    CheckinDTO toDto(Checkin checkin);

    @Mapping(source = "userId", target = "user")
    Checkin toEntity(CheckinDTO checkinDTO);

    default Checkin fromId(Long id) {
        if (id == null) {
            return null;
        }
        Checkin checkin = new Checkin();
        checkin.setId(id);
        return checkin;
    }
}
