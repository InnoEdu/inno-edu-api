package inno.edu.api.domain.profile.commands;

import inno.edu.api.domain.profile.assertions.ProfileExistsAssertion;
import inno.edu.api.domain.profile.repositories.ProfileRepository;
import inno.edu.api.infrastructure.annotations.Command;

import java.util.UUID;

@Command
public class DeleteProfileCommand {
    private final ProfileExistsAssertion profileExistsAssertion;
    private final ProfileRepository profileRepository;

    public DeleteProfileCommand(ProfileExistsAssertion profileExistsAssertion, ProfileRepository profileRepository) {
        this.profileExistsAssertion = profileExistsAssertion;
        this.profileRepository = profileRepository;
    }

    public void run(UUID id) {
        profileExistsAssertion.run(id);
        profileRepository.delete(id);
    }
}
