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
public class ApplicationInitConfig {

    private final PasswordEncoder passwordEncoder;

    @Bean
    ApplicationRunner init(UserRepository userRepository, RoleRepository roleRepository, PermissionRepository permissionRepository) {
        return args -> {
            if (userRepository.findUserByUserName("admin") == null) {

                Set<Permission> permissionAdmin = new HashSet<>();

                permissionAdmin.add(permissionRepository.saveAndFlush(new Permission("CREATE_DATA","create data permission")));
                permissionAdmin.add(permissionRepository.saveAndFlush(new Permission("UPDATE_DATA","update data permission")));
                permissionAdmin.add(permissionRepository.saveAndFlush(new Permission("DELETE_DATA","delete data permission")));
                permissionAdmin.add(permissionRepository.saveAndFlush(new Permission("CREATE_ROLE","create role")));
                permissionAdmin.add(permissionRepository.saveAndFlush(new Permission("UPDATE_ROLE","update role")));
                permissionAdmin.add(permissionRepository.saveAndFlush(new Permission("DELETE_ROLE","delete role")));
                permissionAdmin.add(permissionRepository.saveAndFlush(new Permission("CREATE_PERMISSION","create permission")));
                permissionAdmin.add(permissionRepository.saveAndFlush(new Permission("DELETE_PERMISSION","delete permission")));

                Set<Permission> permissionUser = new HashSet<>();

                permissionUser.add(permissionRepository.saveAndFlush(new Permission("CREATE_POST","create post permission")));
                permissionUser.add(permissionRepository.saveAndFlush(new Permission("UPDATE_POST","update post permission")));
                permissionUser.add(permissionRepository.saveAndFlush(new Permission("DELETE_POST","delete post permission")));

                Role adminRole = Role.builder()
                    .roleName(identity_service.demo.entity.enums.Role.ADMIN.name())
                    .description("admin role")
                    .permissions(permissionAdmin)
                    .build();

                Role userRole = Role.builder()
                    .roleName(identity_service.demo.entity.enums.Role.USER.name())
                    .description("user role")
                    .permissions(permissionUser)
                    .build();

                roleRepository.saveAndFlush(userRole);
                roleRepository.saveAndFlush(adminRole);

                User user = User.builder()
                    .userName("admin")
                    .password(passwordEncoder.encode("admin"))
                    .firstName("admin")
                    .lastName("admin")
                    .email("admin@example.com")
                    .roles(Collections.singleton(adminRole))
                    .build();

                userRepository.saveAndFlush(user);
                log.warn("admin user has been created with default password: admin, please change it");
            }
        };
    }
}
