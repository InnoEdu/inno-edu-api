package inno.edu.api.domain.attachment.commands;

import inno.edu.api.domain.attachment.assertions.AttachmentExistsAssertion;
import inno.edu.api.domain.attachment.models.Attachment;
import inno.edu.api.domain.attachment.repositories.AttachmentRepository;
import inno.edu.api.infrastructure.annotations.Command;
import inno.edu.api.infrastructure.storage.services.StorageService;

import java.util.UUID;

@Command
public class DeleteAttachmentCommand {
    private final AttachmentExistsAssertion profileAttachmentExistsAssertion;
    private final StorageService storageService;
    private final AttachmentRepository attachmentRepository;

    public DeleteAttachmentCommand(AttachmentExistsAssertion profileAttachmentExistsAssertion, StorageService storageService, AttachmentRepository attachmentRepository) {
        this.profileAttachmentExistsAssertion = profileAttachmentExistsAssertion;
        this.storageService = storageService;
        this.attachmentRepository = attachmentRepository;
    }

    public void run(UUID id) {
        profileAttachmentExistsAssertion.run(id);

        Attachment attachment = attachmentRepository.findOne(id);
        attachmentRepository.delete(id);

        storageService.delete(attachment.getUrl());
    }
}
