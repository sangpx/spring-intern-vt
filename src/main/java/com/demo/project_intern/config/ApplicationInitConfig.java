package com.demo.project_intern.config;

import com.demo.project_intern.constant.PredefinedRole;
import com.demo.project_intern.entity.RoleEntity;
import com.demo.project_intern.entity.UserEntity;
import com.demo.project_intern.repository.RoleRepository;
import com.demo.project_intern.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class ApplicationInitConfig {

    private final PasswordEncoder passwordEncoder;

    @NonFinal
    static final String ADMIN_USER_NAME = "admin";

    @NonFinal
    static final String ADMIN_PASSWORD = "admin";

    @Bean
    @ConditionalOnProperty(
            prefix = "spring",
            value = "datasource.driverClassName",
            havingValue = "com.mysql.cj.jdbc.Driver")
    ApplicationRunner applicationRunner(UserRepository userRepository, RoleRepository roleRepository) {
        log.info("Initializing application.....");
        return args -> {
            if (userRepository.findByUserName(ADMIN_USER_NAME).isEmpty()) {
                roleRepository.save(RoleEntity.builder()
                        .name(PredefinedRole.USER_ROLE)
                        .build());
                RoleEntity adminRole = roleRepository.save(RoleEntity.builder()
                        .name(PredefinedRole.ADMIN_ROLE)
                        .build());
                var roles = new HashSet<RoleEntity>();
                roles.add(adminRole);

                UserEntity user = UserEntity.builder()
                        .userName(ADMIN_USER_NAME)
                        .password(passwordEncoder.encode(ADMIN_PASSWORD))
                        .roles(roles)
                        .build();

                userRepository.save(user);
                log.warn("admin user has been created with default password: admin, please change it");
            }
            log.info("Application initialization completed .....");
        };
    }
}
