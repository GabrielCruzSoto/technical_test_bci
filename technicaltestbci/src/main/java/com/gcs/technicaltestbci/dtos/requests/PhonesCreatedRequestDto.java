package com.gcs.technicaltestbci.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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
public class PhonesCreatedRequestDto {
    @NotBlank(message = "El número de teléfono es obligatorio")
    @Pattern(regexp = "^[0-9]+$", message = "El número de teléfono solo debe contener dígitos")
    @Size(min = 7, max = 15, message = "El número de teléfono debe tener entre 7 y 15 dígitos")
    private String numberPhone;
    
    @NotBlank(message = "El código de ciudad es obligatorio")
    @Pattern(regexp = "^[0-9]+$", message = "El código de ciudad solo debe contener dígitos")
    @Size(min = 1, max = 5, message = "El código de ciudad debe tener entre 1 y 5 dígitos")
    private String cityCode;
    
    @NotBlank(message = "El código de país es obligatorio")
    @Pattern(regexp = "^\\+?[0-9]+$", message = "El código de país debe comenzar con + seguido de dígitos")
    @Size(min = 1, max = 4, message = "El código de país debe tener entre 1 y 4 caracteres")
    private String countryCode;
}
