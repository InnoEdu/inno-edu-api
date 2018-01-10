package inno.edu.api.domain.profile.attachment.commands;

import inno.edu.api.domain.profile.attachment.commands.dtos.UploadProfileAttachmentRequest;
import inno.edu.api.domain.profile.root.assertions.ProfileExistsAssertion;
import inno.edu.api.infrastructure.annotations.Command;
import inno.edu.api.infrastructure.storage.StorageService;

@Command
public class UploadProfileAttachmentCommand {
    private final ProfileExistsAssertion profileExistsAssertion;
    private final StorageService storageService;

    public UploadProfileAttachmentCommand(ProfileExistsAssertion profileExistsAssertion, StorageService storageService) {
        this.profileExistsAssertion = profileExistsAssertion;
        this.storageService = storageService;
    }

    public void run(UploadProfileAttachmentRequest request) {
        profileExistsAssertion.run(request.getProfileId());
        storageService.save(request.getProfileId(), request.getFile());
    }
}
