package inno.edu.api.domain.user.root.models.dtos.mappers;

import inno.edu.api.domain.user.root.models.dtos.CreateUserRequest;
import inno.edu.api.domain.user.root.models.ApplicationUser;
import org.mapstruct.Mapper;

@Mapper
public interface CreateUserRequestMapper {
    ApplicationUser toUser(CreateUserRequest createUserRequest);
}
