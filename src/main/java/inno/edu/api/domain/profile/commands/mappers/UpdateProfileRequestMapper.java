package inno.edu.api.domain.profile.commands.mappers;

import inno.edu.api.domain.profile.commands.dtos.UpdateProfileRequest;
import inno.edu.api.domain.profile.models.Profile;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper
public interface UpdateProfileRequestMapper {
    void setProfile(UpdateProfileRequest updateProfileRequest, @MappingTarget Profile Profile);
}
