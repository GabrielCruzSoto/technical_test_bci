package com.gcs.technicaltestbci.dtos.requests;

import java.util.List;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
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
public class UserCreatedRequestDto {
    @NotEmpty(message = "El nombre no puede estar vacío")
    private String name;
    
    @NotEmpty(message = "El email no puede estar vacío")
    @Email(message = "El formato del email no es válido")
    private String email;

    @ToString.Exclude
    @NotEmpty(message = "La contraseña no puede estar vacía")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[0-9].*[0-9])(?=.*[a-z]).{8,}$", 
             message = "La contraseña debe tener al menos 8 caracteres, una mayúscula, letras minúsculas y dos números")
    private String password;
    
    @NotEmpty(message = "La lista de teléfonos no puede estar vacía")
    private List<PhonesCreatedRequestDto> phones;
}
