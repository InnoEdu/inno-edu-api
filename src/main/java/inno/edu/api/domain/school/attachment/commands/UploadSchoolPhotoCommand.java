package inno.edu.api.domain.school.attachment.commands;

import inno.edu.api.domain.attachment.commands.CreateAttachmentCommand;
import inno.edu.api.domain.attachment.commands.DeleteAttachmentCommand;
import inno.edu.api.domain.attachment.models.Attachment;
import inno.edu.api.domain.attachment.models.dtos.CreateAttachmentRequest;
import inno.edu.api.domain.school.root.models.School;
import inno.edu.api.domain.school.root.queries.GetSchoolByIdQuery;
import inno.edu.api.domain.school.root.repositories.SchoolRepository;
import inno.edu.api.infrastructure.annotations.Command;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Command
public class UploadSchoolPhotoCommand {
    static final String DESCRIPTION = "School photo";

    private final CreateAttachmentCommand createAttachmentCommand;
    private final DeleteAttachmentCommand deleteAttachmentCommand;

    private final GetSchoolByIdQuery getSchoolByIdQuery;
    private final SchoolRepository schoolRepository;

    public UploadSchoolPhotoCommand(CreateAttachmentCommand createAttachmentCommand, DeleteAttachmentCommand deleteAttachmentCommand, GetSchoolByIdQuery getSchoolByIdQuery, SchoolRepository schoolRepository) {
        this.createAttachmentCommand = createAttachmentCommand;
        this.deleteAttachmentCommand = deleteAttachmentCommand;
        this.getSchoolByIdQuery = getSchoolByIdQuery;
        this.schoolRepository = schoolRepository;
    }

    public Attachment run(UUID schoolId, MultipartFile file) {
        School school = getSchoolByIdQuery.run(schoolId);

        if (school.getPhotoId() != null) {
            deleteAttachmentCommand.run(school.getPhotoId());
        }

        Attachment attachment = createAttachment(file);
        school.setPhotoId(attachment.getId());
        schoolRepository.save(school);

        return attachment;
    }

    private Attachment createAttachment(MultipartFile file) {
        CreateAttachmentRequest request = CreateAttachmentRequest.builder()
                .file(file)
                .description(DESCRIPTION)
                .build();

        return createAttachmentCommand.run(request);
    }
}
