package com.gcs.technicaltestbci.mappers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import com.gcs.technicaltestbci.dtos.requests.UserCreatedRequestDto;
import com.gcs.technicaltestbci.dtos.response.UserCreatedResponseDto;
import com.gcs.technicaltestbci.dtos.response.UserResponse;
import com.gcs.technicaltestbci.entities.UserEntity;

@Mapper(componentModel = "spring")
public interface IUserMapper {
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "name", source =  "userCreatedRequestDto.name")
    @Mapping(target = "email", source =  "userCreatedRequestDto.email")
    @Mapping(target = "password", source =  "userCreatedRequestDto.password")
    @Mapping(target = "createdOn", ignore = true)
    @Mapping(target = "active", constant = "true")
    @Mapping(target = "listPhones", ignore = true)
    UserEntity userCreatedRequestDtoToUserEntity(UserCreatedRequestDto userCreatedRequestDto);

    @Mapping(target = "id", expression = "java(userEntity.getId().toString())")
    @Mapping(target = "name", source = "userEntity.name")
    @Mapping(target = "email", source = "userEntity.email")
    @Mapping(target = "createdOn",source = "createdOn", qualifiedByName = "localDateTimeToString" )
    @Mapping(target = "active", source = "userEntity.active")
    UserCreatedResponseDto entityToUserCreatedResponseDto(UserEntity userEntity);

    @Mapping(target = "id", expression = "java(userEntity.getId().toString())")
    @Mapping(target = "name", source =  "userEntity.name")
    @Mapping(target = "email", source =  "userEntity.email")
    @Mapping(target = "createdOn",source = "createdOn", qualifiedByName = "localDateTimeToString" )
    @Mapping(target = "listPhones", expression = "java(userEntity.getListPhones().stream().map(phone -> phone.getNumberPhone()).collect(java.util.stream.Collectors.toList()))")
    @Mapping(target = "lastLogin", ignore = true)
    @Mapping(target = "active", source = "userEntity.active")
    UserResponse entityToUserResponse(UserEntity userEntity);

    @Named("localDateTimeToString")
    default String localDateTimeToString(LocalDateTime dateTime) {
        return dateTime != null ? dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) : null;
    }

}