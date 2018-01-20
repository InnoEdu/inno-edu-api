package inno.edu.api.domain.profile.root.models.dtos.mappers;

import inno.edu.api.domain.profile.root.models.dtos.UpdateProfileRequest;
import inno.edu.api.domain.profile.root.models.Profile;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper
public interface UpdateProfileRequestMapper {
    void setProfile(UpdateProfileRequest updateProfileRequest, @MappingTarget Profile Profile);
}
