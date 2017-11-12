package inno.edu.api.domain.user.queries;

import inno.edu.api.domain.user.exceptions.UserProfileNotFoundException;
import inno.edu.api.domain.user.models.MentorProfile;
import inno.edu.api.domain.user.repositories.MentorProfileRepository;
import inno.edu.api.infrastructure.annotations.Query;

import java.util.UUID;

import static java.util.Optional.ofNullable;

@Query
public class GetMentorActiveProfileByUserIdQuery {
    private final MentorProfileRepository mentorProfileRepository;

    public GetMentorActiveProfileByUserIdQuery(MentorProfileRepository mentorProfileRepository) {
        this.mentorProfileRepository = mentorProfileRepository;
    }

    public MentorProfile run(UUID userId) {
        return ofNullable(mentorProfileRepository.findOneMentorProfileByMentorIdAndIsActiveIsTrue(userId))
                .orElseThrow(() -> new UserProfileNotFoundException(userId));
    }
}
