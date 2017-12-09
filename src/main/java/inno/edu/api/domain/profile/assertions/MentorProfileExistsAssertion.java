package inno.edu.api.domain.profile.assertions;

import inno.edu.api.domain.profile.exceptions.ProfileNotFoundException;
import inno.edu.api.domain.profile.repositories.MentorProfileRepository;
import inno.edu.api.infrastructure.annotations.Assertion;

import java.util.UUID;

@Assertion
public class MentorProfileExistsAssertion {
    private final MentorProfileRepository profileRepository;

    public MentorProfileExistsAssertion(MentorProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    public void run(UUID id) {
        if (!profileRepository.exists(id)) {
            throw new ProfileNotFoundException(id);
        }
    }
}
