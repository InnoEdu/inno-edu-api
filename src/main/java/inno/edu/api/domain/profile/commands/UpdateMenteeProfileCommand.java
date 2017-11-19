package inno.edu.api.domain.profile.commands;

import inno.edu.api.domain.profile.models.MenteeProfile;
import inno.edu.api.domain.profile.queries.GetMenteeProfileByIdQuery;
import inno.edu.api.domain.profile.repositories.MenteeProfileRepository;
import inno.edu.api.infrastructure.annotations.Command;

import java.util.UUID;

@Command
public class UpdateMenteeProfileCommand {
    private final GetMenteeProfileByIdQuery getMenteeProfileByIdQuery;
    private final MenteeProfileRepository profileRepository;

    public UpdateMenteeProfileCommand(GetMenteeProfileByIdQuery getMenteeProfileByIdQuery, MenteeProfileRepository profileRepository) {
        this.getMenteeProfileByIdQuery = getMenteeProfileByIdQuery;
        this.profileRepository = profileRepository;
    }

    public MenteeProfile run(UUID id, MenteeProfile profile) {
        MenteeProfile currentMenteeProfile = getMenteeProfileByIdQuery.run(id);

        currentMenteeProfile.setEmail(profile.getEmail());
        currentMenteeProfile.setDescription(profile.getDescription());

        return profileRepository.save(currentMenteeProfile);
    }
}
