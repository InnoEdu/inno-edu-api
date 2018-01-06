package inno.edu.api.domain.profile.interest.queries;

import inno.edu.api.domain.profile.interest.models.Interest;
import inno.edu.api.domain.profile.interest.repositories.InterestRepository;
import inno.edu.api.domain.profile.root.assertions.ProfileExistsAssertion;
import inno.edu.api.infrastructure.annotations.Query;

import java.util.List;
import java.util.UUID;

@Query
public class GetInterestsByProfileIdQuery {
    private final ProfileExistsAssertion profileExistsAssertion;
    private final InterestRepository interestRepository;

    public GetInterestsByProfileIdQuery(ProfileExistsAssertion profileExistsAssertion, InterestRepository interestRepository) {
        this.profileExistsAssertion = profileExistsAssertion;
        this.interestRepository = interestRepository;
    }

    public List<Interest> run(UUID profileId) {
        profileExistsAssertion.run(profileId);
        return interestRepository.findByProfileId(profileId);
    }
}
