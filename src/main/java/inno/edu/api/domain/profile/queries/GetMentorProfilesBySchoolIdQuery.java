package inno.edu.api.domain.profile.queries;

import inno.edu.api.domain.school.exceptions.SchoolNotFoundException;
import inno.edu.api.domain.school.repositories.SchoolRepository;
import inno.edu.api.domain.profile.models.MentorProfile;
import inno.edu.api.domain.profile.repositories.MentorProfileRepository;
import inno.edu.api.infrastructure.annotations.Query;

import java.util.List;
import java.util.UUID;

import static inno.edu.api.domain.profile.models.ProfileStatus.ACTIVE;

@Query
public class GetMentorProfilesBySchoolIdQuery {
    private final MentorProfileRepository mentorProfileRepository;
    private final SchoolRepository schoolRepository;

    public GetMentorProfilesBySchoolIdQuery(MentorProfileRepository mentorProfileRepository, SchoolRepository schoolRepository) {
        this.mentorProfileRepository = mentorProfileRepository;
        this.schoolRepository = schoolRepository;
    }

    public List<MentorProfile> run(UUID schoolId) {
        if (!schoolRepository.exists(schoolId)) {
            throw new SchoolNotFoundException(schoolId);
        }
        return mentorProfileRepository.findBySchoolIdAndStatus(schoolId, ACTIVE);
    }
}
