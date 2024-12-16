package com.gcs.technicaltestbci.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gcs.technicaltestbci.dtos.requests.LoginRequestDto;
import com.gcs.technicaltestbci.dtos.response.LoginResponseDto;
import com.gcs.technicaltestbci.exceptions.AuthenticationException;
import com.gcs.technicaltestbci.services.AuthService;

@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication", description = "Endpoints para autenticación de usuarios")
public class AuthController {
    
    @Autowired
    private AuthService authService;
    
    @PostMapping("/login")
    @Operation(summary = "Iniciar sesión", description = "Permite a un usuario iniciar sesión en el sistema")
    public ResponseEntity<LoginResponseDto> login(
        @Valid @RequestBody LoginRequestDto loginRequest,
        HttpServletRequest request
    ) throws AuthenticationException {
        String clientIp = request.getHeader("X-Forwarded-For");
        if (clientIp == null || clientIp.isEmpty()) {
            clientIp = request.getRemoteAddr();
        }
        String userAgent = request.getHeader("User-Agent");
        
        LoginResponseDto authResponse = authService.login(loginRequest,clientIp, userAgent);
        return ResponseEntity.ok(authResponse);
    }
}
