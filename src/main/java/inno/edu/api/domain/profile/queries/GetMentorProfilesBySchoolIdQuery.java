package inno.edu.api.domain.profile.queries;

import inno.edu.api.domain.profile.models.MentorProfile;
import inno.edu.api.domain.profile.repositories.MentorProfileRepository;
import inno.edu.api.domain.school.assertions.SchoolExistsAssertion;
import inno.edu.api.infrastructure.annotations.Query;

import java.util.List;
import java.util.UUID;

import static inno.edu.api.domain.profile.models.ProfileStatus.ACTIVE;

@Query
public class GetMentorProfilesBySchoolIdQuery {
    private final SchoolExistsAssertion schoolExistsAssertion;
    private final MentorProfileRepository mentorProfileRepository;

    public GetMentorProfilesBySchoolIdQuery(MentorProfileRepository mentorProfileRepository, SchoolExistsAssertion schoolExistsAssertion) {
        this.mentorProfileRepository = mentorProfileRepository;
        this.schoolExistsAssertion = schoolExistsAssertion;
    }

    public List<MentorProfile> run(UUID schoolId) {
        schoolExistsAssertion.run(schoolId);

        return mentorProfileRepository.findBySchoolIdAndStatus(schoolId, ACTIVE);
    }
}
