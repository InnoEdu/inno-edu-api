package inno.edu.api.domain.attachment.commands;

import inno.edu.api.domain.attachment.models.dtos.UpdateAttachmentRequest;
import inno.edu.api.domain.attachment.models.dtos.mappers.UpdateAttachmentRequestMapper;
import inno.edu.api.domain.attachment.models.Attachment;
import inno.edu.api.domain.attachment.queries.GetAttachmentByIdQuery;
import inno.edu.api.domain.attachment.repositories.AttachmentRepository;
import inno.edu.api.infrastructure.annotations.Command;

import java.util.UUID;

@Command
public class UpdateAttachmentCommand {
    private final UpdateAttachmentRequestMapper updateAttachmentRequestMapper;

    private final AttachmentRepository attachmentRepository;
    private final GetAttachmentByIdQuery getAttachmentByIdQuery;

    public UpdateAttachmentCommand(UpdateAttachmentRequestMapper updateAttachmentRequestMapper, AttachmentRepository attachmentRepository, GetAttachmentByIdQuery getAttachmentByIdQuery) {
        this.updateAttachmentRequestMapper = updateAttachmentRequestMapper;
        this.attachmentRepository = attachmentRepository;
        this.getAttachmentByIdQuery = getAttachmentByIdQuery;
    }

    public Attachment run(UUID id, UpdateAttachmentRequest updateAttachmentRequest) {
        Attachment currentAttachment = getAttachmentByIdQuery.run(id);
        updateAttachmentRequestMapper.setAttachment(updateAttachmentRequest, currentAttachment);

        return attachmentRepository.save(currentAttachment);
    }
}
