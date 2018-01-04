package inno.edu.api.domain.profile.queries;

import inno.edu.api.domain.profile.assertions.ProfileExistsAssertion;
import inno.edu.api.domain.profile.models.ProfileAssociation;
import inno.edu.api.domain.profile.models.RequestStatus;
import inno.edu.api.domain.profile.repositories.ProfileAssociationRepository;
import inno.edu.api.infrastructure.annotations.Query;

import java.util.List;
import java.util.UUID;

import static java.util.Objects.nonNull;

@Query
public class GetAssociationsByProfileIdQuery {
    private final ProfileExistsAssertion profileExistsAssertion;
    private final ProfileAssociationRepository profileAssociationRepository;

    public GetAssociationsByProfileIdQuery(ProfileExistsAssertion profileExistsAssertion, ProfileAssociationRepository profileAssociationRepository) {
        this.profileExistsAssertion = profileExistsAssertion;
        this.profileAssociationRepository = profileAssociationRepository;
    }

    public List<ProfileAssociation> run(UUID profileId, RequestStatus status) {
        profileExistsAssertion.run(profileId);

        if (nonNull(status)) {
            return profileAssociationRepository.findByProfileIdAndStatus(profileId, status);
        }
        return profileAssociationRepository.findByProfileId(profileId);
    }
}
