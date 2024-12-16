package com.gcs.technicaltestbci.services;

import java.util.Optional;



import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.gcs.technicaltestbci.dtos.requests.LoginRequestDto;
import com.gcs.technicaltestbci.dtos.response.LoginResponseDto;

import com.gcs.technicaltestbci.entities.LoginAuditEntity;
import com.gcs.technicaltestbci.entities.UserEntity;
import com.gcs.technicaltestbci.exceptions.AuthenticationException;

import com.gcs.technicaltestbci.repositories.ILoginAuditRepository;
import com.gcs.technicaltestbci.repositories.IUserRepository;


import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AuthService{

    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final IUserRepository userRepository;
    private final ILoginAuditRepository loginAuditRepository;

    public AuthService(JwtService jwtService, AuthenticationManager authenticationManager,
            IUserRepository userRepository, ILoginAuditRepository loginAuditRepository) {
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.loginAuditRepository = loginAuditRepository;
    }

    public LoginResponseDto login(LoginRequestDto request, String ipClient, String infoDevice)
            throws AuthenticationException {
        log.info("login| loginRequestDto={}", request);
        Optional<UserEntity> opUserEntity = this.userRepository.findByEmail(request.getEmail());
        if (opUserEntity.isEmpty()) {
            throw new AuthenticationException("El Usuario o la contraseña son incorrectos");
        }
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtService.generateToken(authentication);
        
        LoginAuditEntity loginAuditEntity = LoginAuditEntity.builder()
                .user(opUserEntity.get())
                .clientDevice(infoDevice)
                .ipUser(ipClient)
                .build();
        this.loginAuditRepository.save(loginAuditEntity);
        log.warn("login| Login success");
        return new LoginResponseDto(jwt);
    }


}