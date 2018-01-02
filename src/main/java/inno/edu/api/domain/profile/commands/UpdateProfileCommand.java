package inno.edu.api.domain.profile.commands;

import inno.edu.api.domain.profile.commands.dtos.UpdateProfileRequest;
import inno.edu.api.domain.profile.commands.mappers.UpdateProfileRequestMapper;
import inno.edu.api.domain.profile.models.Profile;
import inno.edu.api.domain.profile.queries.GetProfileByIdQuery;
import inno.edu.api.domain.profile.repositories.ProfileRepository;
import inno.edu.api.infrastructure.annotations.Command;

import java.util.UUID;

@Command
public class UpdateProfileCommand {
    private final UpdateProfileRequestMapper updateProfileRequestMapper;

    private final GetProfileByIdQuery getProfileByIdQuery;
    private final ProfileRepository profileRepository;

    public UpdateProfileCommand(UpdateProfileRequestMapper updateProfileRequestMapper, GetProfileByIdQuery getProfileByIdQuery, ProfileRepository profileRepository) {
        this.updateProfileRequestMapper = updateProfileRequestMapper;
        this.getProfileByIdQuery = getProfileByIdQuery;
        this.profileRepository = profileRepository;
    }

    public Profile run(UUID id, UpdateProfileRequest updateProfileRequest) {
        Profile currentProfile = getProfileByIdQuery.run(id);
        updateProfileRequestMapper.setProfile(updateProfileRequest, currentProfile);
        return profileRepository.save(currentProfile);
    }
}
