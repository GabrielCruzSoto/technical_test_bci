package com.gcs.technicaltestbci.mappers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import com.gcs.technicaltestbci.dtos.response.LoginAuditDto;
import com.gcs.technicaltestbci.entities.LoginAuditEntity;



@Mapper(componentModel = "spring")
public interface ILoginAuditMapper {

    @Mapping(target = "id", expression = "java(loginAuditEntity.getId().toString())")
    @Mapping(target = "ipUser", source = "loginAuditEntity.ipUser")
    @Mapping(target = "clientDevice", source = "loginAuditEntity.clientDevice")
    @Mapping(target = "createdOn",source = "loginAuditEntity.createdOn", qualifiedByName = "localDateTimeToString" )
    LoginAuditDto entityToDto(LoginAuditEntity loginAuditEntity);

    @Named("localDateTimeToString")
    default String localDateTimeToString(LocalDateTime dateTime) {
        return dateTime != null ? dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) : null;
    }
}