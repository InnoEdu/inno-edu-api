package inno.edu.api.domain.profile.attachment.commands;

import inno.edu.api.domain.attachment.commands.CreateAttachmentCommand;
import inno.edu.api.domain.attachment.models.dtos.CreateAttachmentRequest;
import inno.edu.api.domain.attachment.models.Attachment;
import inno.edu.api.domain.profile.attachment.models.ProfileAttachment;
import inno.edu.api.domain.profile.attachment.repositories.ProfileAttachmentRepository;
import inno.edu.api.domain.profile.root.assertions.ProfileExistsAssertion;
import inno.edu.api.infrastructure.annotations.Command;

import java.util.UUID;

@Command
public class CreateProfileAttachmentCommand {
    private final ProfileExistsAssertion profileExistsAssertion;
    private final CreateAttachmentCommand createAttachmentCommand;
    private final ProfileAttachmentRepository profileAttachmentRepository;

    public CreateProfileAttachmentCommand(ProfileExistsAssertion profileExistsAssertion, CreateAttachmentCommand createAttachmentCommand, ProfileAttachmentRepository profileAttachmentRepository) {
        this.profileExistsAssertion = profileExistsAssertion;
        this.createAttachmentCommand = createAttachmentCommand;
        this.profileAttachmentRepository = profileAttachmentRepository;
    }

    public Attachment run(UUID profileId, CreateAttachmentRequest request) {
        profileExistsAssertion.run(profileId);

        Attachment attachment = createAttachmentCommand.run(request);

        ProfileAttachment profileAttachment = ProfileAttachment.builder()
                .profileId(profileId)
                .attachmentId(attachment.getId())
                .build();

        profileAttachmentRepository.save(profileAttachment);

        return attachment;
    }
}
