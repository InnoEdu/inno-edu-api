package inno.edu.api.domain.profile.queries;

import inno.edu.api.domain.profile.exceptions.UserProfileNotFoundException;
import inno.edu.api.domain.profile.models.MentorProfile;
import inno.edu.api.domain.profile.repositories.MentorProfileRepository;
import inno.edu.api.infrastructure.annotations.Query;

import java.util.UUID;

@Query
public class GetMentorProfileByUserIdQuery {
    private final MentorProfileRepository mentorProfileRepository;

    public GetMentorProfileByUserIdQuery(MentorProfileRepository mentorProfileRepository) {
        this.mentorProfileRepository = mentorProfileRepository;
    }

    public MentorProfile run(UUID userId) {
        return mentorProfileRepository.findByMentorId(userId).stream().findFirst()
                .orElseThrow(() -> new UserProfileNotFoundException(userId));
    }
}
