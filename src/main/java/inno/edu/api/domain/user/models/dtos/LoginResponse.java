package inno.edu.api.domain.user.models.dtos;

import inno.edu.api.domain.user.models.ApplicationUser;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class LoginResponse {
    private ApplicationUser user;
    private String token;
}
