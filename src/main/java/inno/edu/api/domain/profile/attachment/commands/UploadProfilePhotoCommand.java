package inno.edu.api.domain.profile.attachment.commands;

import inno.edu.api.domain.attachment.commands.CreateAttachmentCommand;
import inno.edu.api.domain.attachment.commands.DeleteAttachmentCommand;
import inno.edu.api.domain.attachment.models.dtos.CreateAttachmentRequest;
import inno.edu.api.domain.attachment.models.Attachment;
import inno.edu.api.domain.profile.root.models.Profile;
import inno.edu.api.domain.profile.root.queries.GetProfileByIdQuery;
import inno.edu.api.domain.profile.root.repositories.ProfileRepository;
import inno.edu.api.infrastructure.annotations.Command;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Command
public class UploadProfilePhotoCommand {
    static final String DESCRIPTION = "Profile photo";

    private final CreateAttachmentCommand createAttachmentCommand;
    private final DeleteAttachmentCommand deleteAttachmentCommand;

    private final GetProfileByIdQuery getProfileByIdQuery;
    private final ProfileRepository profileRepository;

    public UploadProfilePhotoCommand(CreateAttachmentCommand createAttachmentCommand, DeleteAttachmentCommand deleteAttachmentCommand, GetProfileByIdQuery getProfileByIdQuery, ProfileRepository profileRepository) {
        this.createAttachmentCommand = createAttachmentCommand;
        this.deleteAttachmentCommand = deleteAttachmentCommand;
        this.getProfileByIdQuery = getProfileByIdQuery;
        this.profileRepository = profileRepository;
    }

    public Attachment run(UUID profileId, MultipartFile file) {
        Profile profile = getProfileByIdQuery.run(profileId);

        if (profile.getPhotoId() != null) {
            UUID attachmentId = profile.getPhotoId();

            profile.setPhotoId(null);
            profileRepository.save(profile);

            deleteAttachmentCommand.run(attachmentId);
        }

        Attachment attachment = createAttachment(file);
        profile.setPhotoId(attachment.getId());
        profileRepository.save(profile);

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
