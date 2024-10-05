package identity_service.demo.configuration;

import identity_service.demo.entity.Permission;
import identity_service.demo.entity.Role;
import identity_service.demo.entity.User;
import identity_service.demo.repository.PermissionRepository;
import identity_service.demo.repository.RoleRepository;
import identity_service.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class AplicationInitConfig {

    private final PasswordEncoder passwordEncoder;

    @Bean
    ApplicationRunner init(UserRepository userRepository, PermissionRepository permissionRepository, RoleRepository roleRepository) {
        return args -> {
            if (userRepository.findUserByUserName("admin") == null) {
                Role adminRole = new Role();
                Set<Role> roles = new HashSet<>();
                Set<Permission> rolesPermission = new HashSet<>();

                Role userRole = new Role();
                Set<Role> userRoles = new HashSet<>();
                Set<Permission> userPermission = new HashSet<>();

                adminRole.setRoleName("ADMIN");
                adminRole.setDescription("Admin");

                userRole.setRoleName("USER");
                userRole.setDescription("user");

                rolesPermission.add(Permission.builder()
                    .permissionName("CREATE_DATA")
                    .description("create data")
                    .role(new Role("ADMIN"))
                    .build());
                rolesPermission.add(Permission.builder()
                    .permissionName("UPDATE_DATA")
                    .description("update data")
                    .role(new Role("ADMIN"))
                    .build());

                userPermission.add(Permission.builder()
                    .permissionName("CREATE_POST")
                    .description("create data")
                    .role(new Role("USER"))
                    .build());
                userPermission.add(Permission.builder()
                    .permissionName("EDIT_POST")
                    .description("update data")
                    .role(new Role("USER"))
                    .build());

                adminRole.setPermissions(rolesPermission);
                roles.add(adminRole);

                userRole.setPermissions(userPermission);
                userRoles.add(userRole);

                roles.forEach(role -> {
                    role.getPermissions().forEach(permission -> {
                        log.warn("Adding permission: {}", permission.getPermissionName());
                        log.warn("Adding permission: {}", permission.getDescription());
                    });
                });

                User user = User.builder()
                    .userName("admin")
                    .password(passwordEncoder.encode("admin"))
                    .firstName("admin")
                    .lastName("admin")
                    .email("admin@example.com")
                    //.roles(Collections.singleton("ADMIN"))
                    .build();

                roleRepository.saveAndFlush(adminRole);
                roleRepository.saveAndFlush(userRole);
                //permissionRepository.saveAndFlush(adminPermission);
                userRepository.saveAndFlush(user);
                log.warn("admin user has been created with default password: admin, please change it");
            }
        };
    }
}
