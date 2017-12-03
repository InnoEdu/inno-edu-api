package inno.edu.api.domain.profile.queries;

import inno.edu.api.domain.profile.exceptions.UserProfileNotFoundException;
import inno.edu.api.domain.profile.models.MentorProfile;
import inno.edu.api.domain.profile.repositories.MentorProfileRepository;
import inno.edu.api.domain.user.assertions.UserIsMentorAssertion;
import inno.edu.api.infrastructure.annotations.Query;

import java.util.UUID;

import static inno.edu.api.domain.profile.models.ProfileStatus.ACTIVE;
import static java.util.Optional.ofNullable;

@Query
public class GetMentorActiveProfileByUserIdQuery {
    private final UserIsMentorAssertion userIsMentorAssertion;
    private final MentorProfileRepository mentorProfileRepository;

    public GetMentorActiveProfileByUserIdQuery(UserIsMentorAssertion userIsMentorAssertion, MentorProfileRepository mentorProfileRepository) {
        this.userIsMentorAssertion = userIsMentorAssertion;
        this.mentorProfileRepository = mentorProfileRepository;
    }

    public MentorProfile run(UUID userId) {
        userIsMentorAssertion.run(userId);

        return ofNullable(mentorProfileRepository.findOneByMentorIdAndStatus(userId, ACTIVE))
                .orElseThrow(() -> new UserProfileNotFoundException(userId));
    }
}
