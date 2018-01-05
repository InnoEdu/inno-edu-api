package inno.edu.api.domain.profile.root.queries;

import inno.edu.api.domain.profile.root.models.Profile;
import inno.edu.api.domain.profile.root.repositories.ProfileRepository;
import inno.edu.api.domain.school.assertions.SchoolExistsAssertion;
import inno.edu.api.infrastructure.annotations.Query;

import java.util.List;
import java.util.UUID;

@Query
public class GetProfilesBySchoolIdQuery {
    private final SchoolExistsAssertion schoolExistsAssertion;
    private final ProfileRepository profileRepository;

    public GetProfilesBySchoolIdQuery(ProfileRepository profileRepository, SchoolExistsAssertion schoolExistsAssertion) {
        this.profileRepository = profileRepository;
        this.schoolExistsAssertion = schoolExistsAssertion;
    }

    public List<Profile> run(UUID schoolId) {
        schoolExistsAssertion.run(schoolId);
        return profileRepository.findBySchoolId(schoolId);
    }
}
