package inno.edu.api.domain.profile.association.assertions;

import inno.edu.api.domain.profile.association.exceptions.ProfileAssociationNotFoundException;
import inno.edu.api.domain.profile.association.repositories.ProfileAssociationRepository;
import inno.edu.api.infrastructure.annotations.Assertion;

import java.util.UUID;

@Assertion
public class ProfileAssociationExistsAssertion {
    private final ProfileAssociationRepository profileAssociationRepository;

    public ProfileAssociationExistsAssertion(ProfileAssociationRepository profileAssociationRepository) {
        this.profileAssociationRepository = profileAssociationRepository;
    }

    public void run(UUID id) {
        if (!profileAssociationRepository.exists(id)) {
            throw new ProfileAssociationNotFoundException(id);
        }
    }
}
