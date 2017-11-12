package inno.edu.api.domain.user.queries;

import inno.edu.api.domain.user.exceptions.UserProfileNotFoundException;
import inno.edu.api.domain.user.models.MenteeProfile;
import inno.edu.api.domain.user.repositories.MenteeProfileRepository;
import inno.edu.api.infrastructure.annotations.Query;

import java.util.UUID;

import static java.util.Optional.ofNullable;

@Query
public class GetMenteeProfileByUserIdQuery {
    private final MenteeProfileRepository menteeProfileRepository;

    public GetMenteeProfileByUserIdQuery(MenteeProfileRepository menteeProfileRepository) {
        this.menteeProfileRepository = menteeProfileRepository;
    }

    public MenteeProfile run(UUID userId) {
        return ofNullable(menteeProfileRepository.findOneMenteeProfileByMenteeId(userId))
                .orElseThrow(() -> new UserProfileNotFoundException(userId));
    }
}
