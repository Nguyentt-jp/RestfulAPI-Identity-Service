package identity_service.demo.configuration;

import identity_service.demo.entity.Permission;
import identity_service.demo.entity.Role;
import identity_service.demo.entity.User;
import identity_service.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class AplicationInitConfig {

    private final PasswordEncoder passwordEncoder;

    @Bean
    ApplicationRunner init(UserRepository userRepository) {
        return args -> {
            if (userRepository.findUserByUserName("admin") == null) {
                Role adminRole = new Role();

                adminRole.setRoleName("ADMIN");
                adminRole.setDescription("Admin");


                Set<Role> roles = new HashSet<>();
                roles.add(adminRole);

                roles.forEach(role -> {log.warn(role.getRoleName());});

                User user = User.builder()
                    .userName("admin")
                    .password(passwordEncoder.encode("admin"))
                    .firstName("admin")
                    .lastName("admin")
                    .email("admin@example.com")
                    .roles(roles)
                    .build();

                userRepository.save(user);
                log.warn("admin user has been created with default password: admin, please change it");
            }
        };
    }
}
