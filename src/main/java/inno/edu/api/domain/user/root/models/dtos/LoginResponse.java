package inno.edu.api.domain.user.root.models.dtos;

import inno.edu.api.domain.user.root.models.ApplicationUser;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class LoginResponse {
    private ApplicationUser user;
    private String token;
}
