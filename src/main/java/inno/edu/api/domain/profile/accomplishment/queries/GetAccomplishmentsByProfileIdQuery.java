package inno.edu.api.domain.profile.accomplishment.queries;

import inno.edu.api.domain.profile.accomplishment.models.Accomplishment;
import inno.edu.api.domain.profile.accomplishment.repositories.AccomplishmentRepository;
import inno.edu.api.domain.profile.root.assertions.ProfileExistsAssertion;
import inno.edu.api.infrastructure.annotations.Query;

import java.util.List;
import java.util.UUID;

@Query
public class GetAccomplishmentsByProfileIdQuery {
    private final ProfileExistsAssertion profileExistsAssertion;
    private final AccomplishmentRepository accomplishmentRepository;

    public GetAccomplishmentsByProfileIdQuery(ProfileExistsAssertion profileExistsAssertion, AccomplishmentRepository accomplishmentRepository) {
        this.profileExistsAssertion = profileExistsAssertion;
        this.accomplishmentRepository = accomplishmentRepository;
    }

    public List<Accomplishment> run(UUID profileId) {
        profileExistsAssertion.run(profileId);
        return accomplishmentRepository.findByProfileId(profileId);
    }
}
