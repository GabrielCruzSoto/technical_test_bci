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
public class LoginAuditDto {
    private String id;
    private String ipUser;
    private String clientDevice;
    private String createdOn;
}
