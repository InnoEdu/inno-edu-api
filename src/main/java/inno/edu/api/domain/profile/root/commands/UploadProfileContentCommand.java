package inno.edu.api.domain.profile.root.commands;

import inno.edu.api.domain.profile.root.assertions.ProfileExistsAssertion;
import inno.edu.api.infrastructure.annotations.Command;
import inno.edu.api.infrastructure.storage.StorageService;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Command
public class UploadProfileContentCommand {
    private final ProfileExistsAssertion profileExistsAssertion;
    private final StorageService storageService;

    public UploadProfileContentCommand(ProfileExistsAssertion profileExistsAssertion, StorageService storageService) {
        this.profileExistsAssertion = profileExistsAssertion;
        this.storageService = storageService;
    }

    public void run(UUID id, MultipartFile file) {
        profileExistsAssertion.run(id);

        storageService.save(id, file);
    }
}
