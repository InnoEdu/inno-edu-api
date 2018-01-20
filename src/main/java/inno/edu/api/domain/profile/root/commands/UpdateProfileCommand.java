package inno.edu.api.domain.profile.root.commands;

import inno.edu.api.domain.profile.root.models.dtos.UpdateProfileRequest;
import inno.edu.api.domain.profile.root.models.dtos.mappers.UpdateProfileRequestMapper;
import inno.edu.api.domain.profile.root.models.Profile;
import inno.edu.api.domain.profile.root.queries.GetProfileByIdQuery;
import inno.edu.api.domain.profile.root.repositories.ProfileRepository;
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
