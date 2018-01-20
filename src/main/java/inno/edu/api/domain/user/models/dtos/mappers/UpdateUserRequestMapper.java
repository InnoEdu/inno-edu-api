package inno.edu.api.domain.user.models.dtos.mappers;

import inno.edu.api.domain.user.models.dtos.UpdateUserRequest;
import inno.edu.api.domain.user.models.ApplicationUser;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper
public interface UpdateUserRequestMapper {
    void setUser(UpdateUserRequest updateUserRequest, @MappingTarget ApplicationUser applicationUser);
}
