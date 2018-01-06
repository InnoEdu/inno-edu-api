package inno.edu.api.domain.profile.root.assertions;

import inno.edu.api.domain.profile.root.exceptions.ProfileNotFoundException;
import inno.edu.api.domain.profile.root.repositories.ProfileRepository;
import inno.edu.api.infrastructure.annotations.Assertion;

import java.util.UUID;

@Assertion
public class ProfileExistsAssertion {
    private final ProfileRepository profileRepository;

    public ProfileExistsAssertion(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    public void run(UUID id) {
        if (!profileRepository.exists(id)) {
            throw new ProfileNotFoundException(id);
        }
    }
}