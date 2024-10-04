package identity_service.demo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "roles")
public class Role {
    @Id
    private String roleName;
    private String description;

    /*@ManyToMany(fetch = FetchType.LAZY)
    private Set<User> users;*/

    @OneToMany(mappedBy = "permissionName", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Permission> permissions;
}
