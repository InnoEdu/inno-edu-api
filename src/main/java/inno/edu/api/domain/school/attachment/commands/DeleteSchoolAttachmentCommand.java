package inno.edu.api.domain.school.attachment.commands;

import inno.edu.api.domain.attachment.assertions.AttachmentExistsAssertion;
import inno.edu.api.domain.attachment.commands.DeleteAttachmentCommand;
import inno.edu.api.domain.school.attachment.models.SchoolAttachmentPrimaryKey;
import inno.edu.api.domain.school.attachment.repositories.SchoolAttachmentRepository;
import inno.edu.api.domain.school.root.assertions.SchoolExistsAssertion;
import inno.edu.api.infrastructure.annotations.Command;

import java.util.UUID;

@Command
public class DeleteSchoolAttachmentCommand {
    private final SchoolExistsAssertion schoolExistsAssertion;
    private final AttachmentExistsAssertion attachmentExistsAssertion;
    private final SchoolAttachmentRepository schoolAttachmentRepository;
    private final DeleteAttachmentCommand deleteAttachmentCommand;

    public DeleteSchoolAttachmentCommand(SchoolExistsAssertion schoolExistsAssertion, AttachmentExistsAssertion attachmentExistsAssertion, SchoolAttachmentRepository schoolAttachmentRepository, DeleteAttachmentCommand deleteAttachmentCommand) {
        this.schoolExistsAssertion = schoolExistsAssertion;
        this.attachmentExistsAssertion = attachmentExistsAssertion;
        this.schoolAttachmentRepository = schoolAttachmentRepository;
        this.deleteAttachmentCommand = deleteAttachmentCommand;
    }

    public void run(UUID schoolId, UUID id) {
        schoolExistsAssertion.run(schoolId);
        attachmentExistsAssertion.run(id);

        SchoolAttachmentPrimaryKey schoolAttachmentPrimaryKey =
                SchoolAttachmentPrimaryKey.builder()
                        .schoolId(schoolId)
                        .attachmentId(id)
                        .build();

        schoolAttachmentRepository.delete(schoolAttachmentPrimaryKey);

        deleteAttachmentCommand.run(id);
    }
}
