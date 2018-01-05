package inno.edu.api.domain.profile.association.assertions;

import inno.edu.api.domain.profile.association.exceptions.PendingAssociationExistsException;
import inno.edu.api.domain.profile.association.repositories.ProfileAssociationRepository;
import inno.edu.api.infrastructure.annotations.Assertion;

import java.util.UUID;

import static inno.edu.api.domain.profile.association.models.RequestStatus.PENDING;

@Assertion
public class CheckPendingAssociationsAssertion {
    private final ProfileAssociationRepository profileAssociationRepository;

    public CheckPendingAssociationsAssertion(ProfileAssociationRepository profileAssociationRepository) {
        this.profileAssociationRepository = profileAssociationRepository;
    }

    public void run(UUID id) {
        if (profileAssociationRepository.existsOneByProfileIdAndStatus(id, PENDING)) {
            throw new PendingAssociationExistsException(id);
        }
    }
}
