package inno.edu.api.domain.user.commands.mappers;

import inno.edu.api.domain.user.commands.dtos.CreateUserRequest;
import inno.edu.api.domain.user.models.User;
import org.mapstruct.Mapper;

@Mapper
public interface CreateUserRequestMapper {
    User toUser(CreateUserRequest createUserRequest);
}
