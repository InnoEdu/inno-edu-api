package inno.edu.api.domain.profile.experience.queries;

import inno.edu.api.domain.profile.assertions.ProfileExistsAssertion;
import inno.edu.api.domain.profile.experience.models.Experience;
import inno.edu.api.domain.profile.experience.repositories.ExperienceRepository;
import inno.edu.api.infrastructure.annotations.Query;

import java.util.List;
import java.util.UUID;

@Query
public class GetExperiencesByProfileIdQuery {
    private final ProfileExistsAssertion profileExistsAssertion;
    private final ExperienceRepository experienceRepository;

    public GetExperiencesByProfileIdQuery(ProfileExistsAssertion profileExistsAssertion, ExperienceRepository experienceRepository) {
        this.profileExistsAssertion = profileExistsAssertion;
        this.experienceRepository = experienceRepository;
    }

    public List<Experience> run(UUID profileId) {
        profileExistsAssertion.run(profileId);
        return experienceRepository.findByProfileId(profileId);
    }
}
