package com.gcs.technicaltestbci.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.gcs.technicaltestbci.dtos.requests.PhonesCreatedRequestDto;
import com.gcs.technicaltestbci.entities.PhonesEntity;

@Mapper(componentModel = "spring")
public interface IPhonesMapper {
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userEntity", ignore = true)
    @Mapping(target = "numberPhone", source = "phonesCreatedRequestDto.numberPhone")
    @Mapping(target = "cityCode", source = "phonesCreatedRequestDto.cityCode")
    @Mapping(target = "countryCode", source = "phonesCreatedRequestDto.countryCode")
    PhonesEntity requestDtoToEntity(PhonesCreatedRequestDto phonesCreatedRequestDto);
}