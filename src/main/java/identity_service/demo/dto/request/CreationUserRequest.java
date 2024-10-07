package identity_service.demo.dto.request;

import identity_service.demo.entity.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreationUserRequest {

    @NotNull
    @Size(min = 5, max = 25, message = "INVALID_USERNAME")
    private String userName;

    @NotNull
    @Size(min = 8, max = 25, message = "INVALID_PASSWORD")
    private String password;

    private String firstName;
    private String lastName;

    @NotNull
    @Email
    private String email;

    @NotNull
    private Set<String> roles;
}
