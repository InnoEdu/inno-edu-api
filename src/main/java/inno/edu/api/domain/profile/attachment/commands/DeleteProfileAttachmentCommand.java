package inno.edu.api.domain.profile.attachment.commands;

import inno.edu.api.domain.profile.attachment.assertions.ProfileAttachmentExistsAssertion;
import inno.edu.api.domain.profile.attachment.models.ProfileAttachment;
import inno.edu.api.domain.profile.attachment.repositories.ProfileAttachmentRepository;
import inno.edu.api.infrastructure.annotations.Command;
import inno.edu.api.infrastructure.storage.services.StorageService;

import java.util.UUID;

@Command
public class DeleteProfileAttachmentCommand {
    private final ProfileAttachmentExistsAssertion profileAttachmentExistsAssertion;
    private final StorageService storageService;
    private final ProfileAttachmentRepository profileAttachmentRepository;

    public DeleteProfileAttachmentCommand(ProfileAttachmentExistsAssertion profileAttachmentExistsAssertion, StorageService storageService, ProfileAttachmentRepository profileAttachmentRepository) {
        this.profileAttachmentExistsAssertion = profileAttachmentExistsAssertion;
        this.storageService = storageService;
        this.profileAttachmentRepository = profileAttachmentRepository;
    }

    public void run(UUID id) {
        profileAttachmentExistsAssertion.run(id);

        ProfileAttachment profileAttachment = profileAttachmentRepository.findOne(id);
        profileAttachmentRepository.delete(id);

        storageService.delete(profileAttachment.getUrl());
    }
}
