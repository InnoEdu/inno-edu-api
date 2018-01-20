package inno.edu.api.domain.profile.root.models.dtos.mappers;

import inno.edu.api.domain.profile.root.models.dtos.CreateProfileRequest;
import inno.edu.api.domain.profile.root.models.Profile;
import org.mapstruct.Mapper;

@Mapper
public interface CreateProfileRequestMapper {
    Profile toProfile(CreateProfileRequest createProfileRequest);
}
