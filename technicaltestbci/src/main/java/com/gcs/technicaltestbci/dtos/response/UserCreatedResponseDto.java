package com.gcs.technicaltestbci.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserCreatedResponseDto {
    private String id;
    private String name;
    private String email;
    private String createdOn;
    private boolean isActive;
}
