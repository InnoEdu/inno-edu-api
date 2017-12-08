package inno.edu.api.domain.profile.queries;

import inno.edu.api.domain.profile.queries.dtos.MentorProfileUser;
import inno.edu.api.domain.profile.queries.mappers.MentorProfileUserMapper;
import inno.edu.api.domain.profile.repositories.MentorProfileRepository;
import inno.edu.api.domain.school.assertions.SchoolExistsAssertion;
import inno.edu.api.infrastructure.annotations.Query;

import java.util.List;
import java.util.UUID;

import static inno.edu.api.domain.profile.models.ProfileStatus.ACTIVE;
import static java.util.stream.Collectors.toList;

@Query
public class GetMentorProfilesBySchoolIdQuery {
    private final SchoolExistsAssertion schoolExistsAssertion;
    private final MentorProfileRepository mentorProfileRepository;
    private final MentorProfileUserMapper mentorProfileUserMapper;

    public GetMentorProfilesBySchoolIdQuery(MentorProfileRepository mentorProfileRepository, SchoolExistsAssertion schoolExistsAssertion, MentorProfileUserMapper mentorProfileUserMapper) {
        this.mentorProfileRepository = mentorProfileRepository;
        this.schoolExistsAssertion = schoolExistsAssertion;
        this.mentorProfileUserMapper = mentorProfileUserMapper;
    }

    public List<MentorProfileUser> run(UUID schoolId) {
        schoolExistsAssertion.run(schoolId);

        return mentorProfileRepository.findBySchoolIdAndStatus(schoolId, ACTIVE)
                .stream()
                .map(mentorProfileUserMapper::toResponse)
                .collect(toList());
    }
}
