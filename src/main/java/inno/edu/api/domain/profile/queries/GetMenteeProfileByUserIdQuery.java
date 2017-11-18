package inno.edu.api.domain.profile.queries;

import inno.edu.api.domain.profile.exceptions.UserProfileNotFoundException;
import inno.edu.api.domain.profile.models.MenteeProfile;
import inno.edu.api.domain.profile.repositories.MenteeProfileRepository;
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
        return ofNullable(menteeProfileRepository.findOneByMenteeId(userId))
                .orElseThrow(() -> new UserProfileNotFoundException(userId));
    }
}
