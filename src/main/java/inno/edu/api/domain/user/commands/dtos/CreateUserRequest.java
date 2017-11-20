package inno.edu.api.domain.user.commands.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserRequest {
    private String firstName;
    private String lastName;

    private String username;
    private String password;
    private String confirmPassword;

    private Boolean isMentor;

    private String photoUrl;
}
