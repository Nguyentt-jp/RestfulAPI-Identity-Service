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
    @Column(name = "role_name")
    private String roleName;

    private String description;

   /* @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "user_roles",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "role_name")
    )*/
    //private Set<User> users;

    @OneToMany(
        mappedBy = "role",
        fetch = FetchType.LAZY,
        cascade = CascadeType.ALL
    )
    private Set<Permission> permissions;

    public Role(String roleName) {
        this.roleName = roleName;
    }
}
