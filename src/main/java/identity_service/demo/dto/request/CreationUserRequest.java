package identity_service.demo.dto.request;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreationUserRequest {

    @Size(min = 5, message = "Username must be at least 3 characters")
    private String userName;

    @Size(min = 8, message = "Password must be at least 3 characters")
    private String password;

    private String firstName;
    private String lastName;
    private String email;
}
