package inno.edu.api.domain.school.attachment.commands;

import inno.edu.api.domain.attachment.commands.CreateAttachmentCommand;
import inno.edu.api.domain.attachment.models.dtos.CreateAttachmentRequest;
import inno.edu.api.domain.attachment.models.Attachment;
import inno.edu.api.domain.school.attachment.models.SchoolAttachment;
import inno.edu.api.domain.school.attachment.repositories.SchoolAttachmentRepository;
import inno.edu.api.domain.school.root.assertions.SchoolExistsAssertion;
import inno.edu.api.infrastructure.annotations.Command;

import java.util.UUID;

@Command
public class CreateSchoolAttachmentCommand {
    private final SchoolExistsAssertion schoolExistsAssertion;
    private final CreateAttachmentCommand createAttachmentCommand;
    private final SchoolAttachmentRepository schoolAttachmentRepository;

    public CreateSchoolAttachmentCommand(SchoolExistsAssertion schoolExistsAssertion, CreateAttachmentCommand createAttachmentCommand, SchoolAttachmentRepository schoolAttachmentRepository) {
        this.schoolExistsAssertion = schoolExistsAssertion;
        this.createAttachmentCommand = createAttachmentCommand;
        this.schoolAttachmentRepository = schoolAttachmentRepository;
    }

    public Attachment run(UUID schoolId, CreateAttachmentRequest request) {
        schoolExistsAssertion.run(schoolId);

        Attachment attachment = createAttachmentCommand.run(request);

        SchoolAttachment schoolAttachment = SchoolAttachment.builder()
                .schoolId(schoolId)
                .attachmentId(attachment.getId())
                .build();

        schoolAttachmentRepository.save(schoolAttachment);

        return attachment;
    }
}
