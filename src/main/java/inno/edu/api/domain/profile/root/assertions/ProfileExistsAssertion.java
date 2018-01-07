package inno.edu.api.domain.profile.root.assertions;

import inno.edu.api.domain.common.assertions.EntityExistsAssertion;
import inno.edu.api.domain.profile.root.exceptions.ProfileNotFoundException;
import inno.edu.api.domain.profile.root.repositories.ProfileRepository;
import inno.edu.api.infrastructure.annotations.Assertion;

import java.util.UUID;
import java.util.function.Function;

@Assertion
public class ProfileExistsAssertion extends EntityExistsAssertion<ProfileRepository, ProfileNotFoundException, Function<UUID, ProfileNotFoundException>> {
    protected ProfileExistsAssertion(ProfileRepository repository) {
        super(repository, ProfileNotFoundException::new);
    }
}