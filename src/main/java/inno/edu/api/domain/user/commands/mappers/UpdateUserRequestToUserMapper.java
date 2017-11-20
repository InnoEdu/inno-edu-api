package inno.edu.api.domain.user.commands.mappers;

import inno.edu.api.domain.user.commands.dtos.UpdateUserRequest;
import inno.edu.api.domain.user.models.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper
public interface UpdateUserRequestToUserMapper {
    void updateUserRequestToUser(UpdateUserRequest updateUserRequest, @MappingTarget User user);
}
