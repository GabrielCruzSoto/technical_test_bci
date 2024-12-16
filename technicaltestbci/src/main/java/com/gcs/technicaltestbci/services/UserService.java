package com.gcs.technicaltestbci.services;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.gcs.technicaltestbci.dtos.requests.UserCreatedRequestDto;
import com.gcs.technicaltestbci.dtos.response.LoginAuditDto;
import com.gcs.technicaltestbci.dtos.response.UserCreatedResponseDto;
import com.gcs.technicaltestbci.dtos.response.UserResponse;
import com.gcs.technicaltestbci.entities.LoginAuditEntity;
import com.gcs.technicaltestbci.entities.PhonesEntity;
import com.gcs.technicaltestbci.entities.UserEntity;
import com.gcs.technicaltestbci.exceptions.UserCreatedException;
import com.gcs.technicaltestbci.exceptions.UserNotFoundException;
import com.gcs.technicaltestbci.mappers.ILoginAuditMapper;
import com.gcs.technicaltestbci.mappers.IUserMapper;
import com.gcs.technicaltestbci.mappers.IPhonesMapper;
import com.gcs.technicaltestbci.repositories.ILoginAuditRepository;
import com.gcs.technicaltestbci.repositories.IUserRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserService implements UserDetailsService {
    private final IUserMapper userMapper;
    private final ILoginAuditMapper historyLoginMapper;
    private final IPhonesMapper phonesMapper;
    private final IUserRepository userRepository;
    private final ILoginAuditRepository loginAuditRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(IUserMapper userMapper, ILoginAuditMapper historyLoginMapper, IPhonesMapper phonesMapper,
            IUserRepository userRepository, ILoginAuditRepository loginAuditRepository,
            PasswordEncoder passwordEncoder) {
        this.userMapper = userMapper;
        this.historyLoginMapper = historyLoginMapper;
        this.phonesMapper = phonesMapper;
        this.userRepository = userRepository;
        this.loginAuditRepository = loginAuditRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserCreatedResponseDto register(UserCreatedRequestDto userCreatedRequestDto) throws UserCreatedException {
        log.info("register| userCreatedRequestDto={}", userCreatedRequestDto);
        if (userRepository.existsByEmail(userCreatedRequestDto.getEmail())) {
            throw new UserCreatedException();
        }

        
        UserEntity preSaveEntity = this.userMapper.userCreatedRequestDtoToUserEntity(userCreatedRequestDto);
        Set<PhonesEntity> listPhones = userCreatedRequestDto.getPhones().stream()
            .map(phonesMapper::requestDtoToEntity)
            .collect(Collectors.toSet());
        
        preSaveEntity.setListPhones(listPhones);
        preSaveEntity.setPassword(passwordEncoder.encode(userCreatedRequestDto.getPassword()));

        UserEntity savedEntity = userRepository.save(preSaveEntity);
        
        UserCreatedResponseDto userCreatedResponseDto = userMapper.entityToUserCreatedResponseDto(savedEntity);
        log.info("register| userCreatedResponseDto={}", userCreatedResponseDto);
        return userCreatedResponseDto;
    }

    public UserResponse getUserByEmail(String email) throws UserNotFoundException {
        Optional<UserEntity> opUserEntity = this.userRepository.findByEmail(email);
        if(opUserEntity.isEmpty()){
            throw new UserNotFoundException();
        }
        UserEntity userEntity = opUserEntity.get();
        userEntity.getListPhones();
        UserResponse userResponse = userMapper.entityToUserResponse(userEntity);
        return userResponse;
    }

    public Page<UserResponse> getAllUsers(int page, int size) {
        log.info("getAllUsers| page={}, size={}", page, size);
        Pageable pageable = PageRequest.of(page, size);
        Page<UserEntity> users = userRepository.findAll(pageable);
        Page<UserResponse> userResponses = users.map(userMapper::entityToUserResponse);
        log.info("getAllUsers| userResponses={}", userResponses);
        return userResponses;
    }

    public Page<LoginAuditDto> getUserLoginAuditById(String id, int page, int size) {
        log.info("getUserLoginAuditById| id={}, page={}, size={}", id, page, size);
        Pageable pageable = PageRequest.of(page, size);
        Page<LoginAuditEntity> loginAudits = loginAuditRepository.findByUserId(id, pageable);
        Page<LoginAuditDto> loginAuditDtos = loginAudits.map(historyLoginMapper::entityToDto);
        log.info("getUserLoginAuditById| loginAuditDtos={}", loginAuditDtos);
        return loginAuditDtos;
    }

    public Page<LoginAuditDto> getUserLoginAuditByEmail(String email, int page, int size) {
        log.info("getUserLoginAuditByEmail| email={}, page={}, size={}", email, page, size);
        Pageable pageable = PageRequest.of(page, size);
        Page<LoginAuditEntity> loginAudits = loginAuditRepository.findByUserEmail(email, pageable);
        Page<LoginAuditDto> loginAuditDtos = loginAudits.map(historyLoginMapper::entityToDto);
        log.info("getUserLoginAuditByEmail| loginAuditDtos={}", loginAuditDtos);
        return loginAuditDtos;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> opUserEntity = this.userRepository.findByEmail(username);
        if (opUserEntity.isEmpty()) {
            throw new UsernameNotFoundException("El usuario no existe");
        }
        UserEntity userEntity = opUserEntity.get();
        UserDetails userDetails = User.builder()
                .username(userEntity.getEmail())
                .password(userEntity.getPassword())
                .disabled(!userEntity.getActive())
                .build();
        return userDetails;
    }

}
