package inno.edu.api.domain.user.commands.mappers;

import inno.edu.api.domain.user.commands.dtos.UpdateUserRequest;
import inno.edu.api.domain.user.models.ApplicationUser;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper
public interface UpdateUserRequestMapper {
    void setUser(UpdateUserRequest updateUserRequest, @MappingTarget ApplicationUser applicationUser);
}
