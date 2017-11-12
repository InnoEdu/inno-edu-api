package inno.edu.api.domain.user.queries;

import inno.edu.api.domain.user.models.MentorProfile;
import inno.edu.api.domain.user.repositories.MentorProfileRepository;
import inno.edu.api.infrastructure.annotations.Query;

import java.util.List;
import java.util.UUID;

import static inno.edu.api.domain.user.models.ProfileStatus.ACTIVE;

@Query
public class GetMentorProfilesByUniversityIdQuery {
    private final MentorProfileRepository mentorProfileRepository;

    public GetMentorProfilesByUniversityIdQuery(MentorProfileRepository mentorProfileRepository) {
        this.mentorProfileRepository = mentorProfileRepository;
    }

    public List<MentorProfile> run(UUID universityId) {
        return mentorProfileRepository.findByUniversityIdAndStatus(universityId, ACTIVE);
    }
}
