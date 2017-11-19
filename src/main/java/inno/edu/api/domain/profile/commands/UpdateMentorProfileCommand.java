package inno.edu.api.domain.profile.commands;

import inno.edu.api.domain.profile.models.MentorProfile;
import inno.edu.api.domain.profile.queries.GetMentorProfileByIdQuery;
import inno.edu.api.domain.profile.repositories.MentorProfileRepository;
import inno.edu.api.infrastructure.annotations.Command;

import java.util.UUID;

@Command
public class UpdateMentorProfileCommand {
    private final GetMentorProfileByIdQuery getMentorProfileByIdQuery;
    private final MentorProfileRepository profileRepository;

    public UpdateMentorProfileCommand(GetMentorProfileByIdQuery getMentorProfileByIdQuery, MentorProfileRepository profileRepository) {
        this.getMentorProfileByIdQuery = getMentorProfileByIdQuery;
        this.profileRepository = profileRepository;
    }

    public MentorProfile run(UUID id, MentorProfile profile) {
        MentorProfile currentMentorProfile = getMentorProfileByIdQuery.run(id);

        currentMentorProfile.setEmail(profile.getEmail());
        currentMentorProfile.setDescription(profile.getDescription());
        currentMentorProfile.setStatus(profile.getStatus());

        return profileRepository.save(currentMentorProfile);
    }
}
