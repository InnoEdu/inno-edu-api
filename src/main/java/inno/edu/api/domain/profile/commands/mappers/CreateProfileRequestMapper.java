package inno.edu.api.domain.profile.commands.mappers;

import inno.edu.api.domain.profile.commands.dtos.CreateProfileRequest;
import inno.edu.api.domain.profile.models.Profile;
import org.mapstruct.Mapper;

@Mapper
public interface CreateProfileRequestMapper {
    Profile toProfile(CreateProfileRequest createProfileRequest);
}
