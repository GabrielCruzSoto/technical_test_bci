package com.gcs.technicaltestbci.controller;


import com.gcs.technicaltestbci.controllers.AuthController;
import com.gcs.technicaltestbci.dtos.requests.LoginRequestDto;
import com.gcs.technicaltestbci.dtos.response.LoginResponseDto;
import com.gcs.technicaltestbci.exceptions.AuthenticationException;
import com.gcs.technicaltestbci.services.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import jakarta.servlet.http.HttpServletRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class AuthControllerTest {

    @InjectMocks
    private AuthController authController;

    @Mock
    private AuthService authService;

    @Mock
    private HttpServletRequest request;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testLogin_Success() throws AuthenticationException {
        LoginRequestDto loginRequest = new LoginRequestDto("test@example.com", "password");
        LoginResponseDto loginResponse = new LoginResponseDto("token");

        when(authService.login(loginRequest, "127.0.0.1", "user-agent")).thenReturn(loginResponse);

        ResponseEntity<LoginResponseDto> response = authController.login(loginRequest, request);

        
        assertEquals(loginResponse, response.getBody());
    }

    @Test
    public void testLogin_Failure() throws AuthenticationException {
        LoginRequestDto loginRequest = new LoginRequestDto("test@example.com", "wrong-password");

        when(authService.login(loginRequest, "127.0.0.1", "user-agent")).thenThrow(new AuthenticationException("Invalid credentials"));

        try {
            authController.login(loginRequest, request);
        } catch (AuthenticationException e) {
            assertEquals("Invalid credentials", e.getMessage());
        }
    }
}