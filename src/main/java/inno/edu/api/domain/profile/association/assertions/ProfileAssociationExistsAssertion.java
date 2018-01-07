package inno.edu.api.domain.profile.association.assertions;

import inno.edu.api.domain.common.assertions.EntityExistsAssertion;
import inno.edu.api.domain.profile.association.exceptions.ProfileAssociationNotFoundException;
import inno.edu.api.domain.profile.association.repositories.ProfileAssociationRepository;
import inno.edu.api.infrastructure.annotations.Assertion;

import java.util.UUID;
import java.util.function.Function;

@Assertion
public class ProfileAssociationExistsAssertion extends EntityExistsAssertion<ProfileAssociationRepository, ProfileAssociationNotFoundException, Function<UUID, ProfileAssociationNotFoundException>> {
    protected ProfileAssociationExistsAssertion(ProfileAssociationRepository repository) {
        super(repository, ProfileAssociationNotFoundException::new);
    }
}