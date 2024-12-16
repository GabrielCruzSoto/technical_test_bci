package com.gcs.technicaltestbci.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.gcs.technicaltestbci.security.JwtAuthenticationFilterSecurity;
import com.gcs.technicaltestbci.services.JwtService;
import com.gcs.technicaltestbci.services.UserService;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class BeansConfigs {
    @Bean
    public PasswordEncoder getInstancePasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Ejemplo")
                        .version("1.0")
                        .description("Documentación de la API de ejemplo con SpringDoc OpenAPI")
                );
    }
    @Bean
    public JwtAuthenticationFilterSecurity jwtAuthenticationFilter( JwtService jwtService, UserService userService) {
        return new JwtAuthenticationFilterSecurity(jwtService, userService);
    }
}
