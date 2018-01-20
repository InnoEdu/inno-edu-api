package inno.edu.api.domain.attachment.commands;

import inno.edu.api.domain.attachment.models.dtos.CreateAttachmentRequest;
import inno.edu.api.domain.attachment.models.dtos.mappers.CreateAttachmentRequestMapper;
import inno.edu.api.domain.attachment.models.Attachment;
import inno.edu.api.domain.attachment.repositories.AttachmentRepository;
import inno.edu.api.infrastructure.annotations.Command;
import inno.edu.api.infrastructure.services.UUIDGeneratorService;
import inno.edu.api.infrastructure.storage.services.StorageService;

import java.util.UUID;

@Command
public class CreateAttachmentCommand {
    private final UUIDGeneratorService uuidGeneratorService;
    private final CreateAttachmentRequestMapper createAttachmentRequestMapper;
    private final StorageService storageService;
    private final AttachmentRepository attachmentRepository;

    public CreateAttachmentCommand(UUIDGeneratorService uuidGeneratorService, CreateAttachmentRequestMapper createAttachmentRequestMapper, StorageService storageService, AttachmentRepository attachmentRepository) {
        this.uuidGeneratorService = uuidGeneratorService;
        this.createAttachmentRequestMapper = createAttachmentRequestMapper;
        this.storageService = storageService;
        this.attachmentRepository = attachmentRepository;
    }

    public Attachment run(CreateAttachmentRequest request) {
        UUID attachmentId = uuidGeneratorService.generate();

        String url = storageService.save(attachmentId, request.getFile());

        Attachment attachment = createAttachmentRequestMapper.toAttachment(request, url);
        attachment.setId(attachmentId);

        return attachmentRepository.save(attachment);
    }
}
