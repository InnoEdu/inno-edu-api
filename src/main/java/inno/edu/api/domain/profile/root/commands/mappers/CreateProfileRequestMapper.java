package inno.edu.api.domain.profile.root.commands.mappers;

import inno.edu.api.domain.profile.root.commands.dtos.CreateProfileRequest;
import inno.edu.api.domain.profile.root.models.Profile;
import org.mapstruct.Mapper;

@Mapper
public interface CreateProfileRequestMapper {
    Profile toProfile(CreateProfileRequest createProfileRequest);
}
