package identity_service.demo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID userId;
    private String userName;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private Set<String> roles;

    /*@ManyToMany
    private Set<Role> roles;*/
}
