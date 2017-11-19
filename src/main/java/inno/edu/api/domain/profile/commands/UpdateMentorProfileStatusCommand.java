package inno.edu.api.domain.profile.commands;

import inno.edu.api.domain.profile.models.MentorProfile;
import inno.edu.api.domain.profile.models.ProfileStatus;
import inno.edu.api.domain.profile.queries.GetMentorProfileByIdQuery;
import inno.edu.api.domain.profile.repositories.MentorProfileRepository;
import inno.edu.api.infrastructure.annotations.Command;

import java.util.UUID;

@Command
public class UpdateMentorProfileStatusCommand {
    private final GetMentorProfileByIdQuery getMentorProfileByIdQuery;
    private final MentorProfileRepository mentorProfileRepository;

    public UpdateMentorProfileStatusCommand(GetMentorProfileByIdQuery getMentorProfileByIdQuery, MentorProfileRepository mentorProfileRepository) {
        this.getMentorProfileByIdQuery = getMentorProfileByIdQuery;
        this.mentorProfileRepository = mentorProfileRepository;
    }

    public void run(UUID profileId, ProfileStatus status) {
        MentorProfile profile = getMentorProfileByIdQuery.run(profileId);

        profile.setStatus(status);
        mentorProfileRepository.save(profile);
    }
}
