package inno.edu.api.domain.profile.commands;

import inno.edu.api.domain.profile.models.Profile;
import inno.edu.api.domain.profile.models.ProfileStatus;
import inno.edu.api.domain.profile.queries.GetProfileByIdQuery;
import inno.edu.api.domain.profile.repositories.ProfileRepository;
import inno.edu.api.infrastructure.annotations.Command;

import java.util.UUID;

@Command
public class UpdateProfileStatusCommand {
    private final GetProfileByIdQuery getProfileByIdQuery;
    private final ProfileRepository profileRepository;

    public UpdateProfileStatusCommand(GetProfileByIdQuery getProfileByIdQuery, ProfileRepository profileRepository) {
        this.getProfileByIdQuery = getProfileByIdQuery;
        this.profileRepository = profileRepository;
    }

    public void run(UUID profileId, ProfileStatus status) {
        Profile profile = getProfileByIdQuery.run(profileId);

        profile.setStatus(status);
        profileRepository.save(profile);
    }
}
