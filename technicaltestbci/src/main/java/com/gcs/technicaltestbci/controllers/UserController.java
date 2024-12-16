package com.gcs.technicaltestbci.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gcs.technicaltestbci.dtos.requests.UserCreatedRequestDto;
import com.gcs.technicaltestbci.dtos.response.LoginAuditDto;
import com.gcs.technicaltestbci.dtos.response.UserCreatedResponseDto;
import com.gcs.technicaltestbci.dtos.response.UserResponse;
import com.gcs.technicaltestbci.exceptions.UserCreatedException;
import com.gcs.technicaltestbci.exceptions.UserNotFoundException;
import com.gcs.technicaltestbci.services.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Tag(name = "User Management", description = "Endpoints para la gestión de usuarios")
public class UserController {
    
    private final UserService userService;

    @PostMapping("/register")
    @Operation(summary = "Registrar usuario", description = "Registra un nuevo usuario en el sistema")
    public ResponseEntity<UserCreatedResponseDto> registerUser(
            @Valid @RequestBody UserCreatedRequestDto userRequest) throws UserCreatedException {
        UserCreatedResponseDto response = userService.register(userRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{email}")
    @Operation(summary = "Obtener usuario por email", description = "Obtiene la información de un usuario por su email")
    public ResponseEntity<UserResponse> getUserById(@PathVariable String email) throws UserNotFoundException {
        UserResponse user = userService.getUserByEmail(email);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/all")
    @Operation(summary = "Listar todos los usuarios", description = "Obtiene una lista paginada de todos los usuarios")
    public ResponseEntity<Page<UserResponse>> getAllUsers(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size)  {
        Page<UserResponse> users = userService.getAllUsers(page, size);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/by-id/{id}/audit")
    @Operation(summary = "Obtener auditoría de inicio de sesión por ID", description = "Obtiene el historial de auditoría de inicio de sesión de un usuario por su ID")
    public ResponseEntity<Page<LoginAuditDto>> getUserLoginAuditById(
            @PathVariable String id, 
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size)  {
        Page<LoginAuditDto> loginAudit = userService.getUserLoginAuditById(id, page, size);
        return ResponseEntity.ok(loginAudit);
    }
    @GetMapping("/by-email/{email}/audit")
    @Operation(summary = "Obtener auditoría de inicio de sesión por email", description = "Obtiene el historial de auditoría de inicio de sesión de un usuario por su email")
    public ResponseEntity<Page<LoginAuditDto>> getUserLoginAuditByEmail(
            @PathVariable String email, 
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size)  {
        Page<LoginAuditDto> loginAudit = userService.getUserLoginAuditByEmail(email, page, size);
        return ResponseEntity.ok(loginAudit);
    }

}
