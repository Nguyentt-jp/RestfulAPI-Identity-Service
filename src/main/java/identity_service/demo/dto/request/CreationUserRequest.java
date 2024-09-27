package identity_service.demo.dto.request;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreationUserRequest {

    @Size(min = 5, message = "INVALID_USERNAME")
    private String userName;

    @Size(min = 8, message = "INVALID_PASSWORD")
    private String password;

    private String firstName;
    private String lastName;
    private String email;
}
