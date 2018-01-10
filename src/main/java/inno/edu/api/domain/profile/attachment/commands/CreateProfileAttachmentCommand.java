package inno.edu.api.domain.profile.attachment.commands;

import inno.edu.api.domain.profile.attachment.commands.dtos.CreateProfileAttachmentRequest;
import inno.edu.api.domain.profile.attachment.commands.mappers.CreateProfileAttachmentRequestMapper;
import inno.edu.api.domain.profile.attachment.models.ProfileAttachment;
import inno.edu.api.domain.profile.attachment.repositories.ProfileAttachmentRepository;
import inno.edu.api.domain.profile.root.assertions.ProfileExistsAssertion;
import inno.edu.api.infrastructure.annotations.Command;
import inno.edu.api.infrastructure.services.UUIDGeneratorService;
import inno.edu.api.infrastructure.storage.StorageService;

@Command
public class CreateProfileAttachmentCommand {
    private final ProfileExistsAssertion profileExistsAssertion;

    private final UUIDGeneratorService uuidGeneratorService;
    private final CreateProfileAttachmentRequestMapper createProfileAttachmentRequestMapper;
    private final StorageService storageService;
    private final ProfileAttachmentRepository profileAttachmentRepository;

    public CreateProfileAttachmentCommand(ProfileExistsAssertion profileExistsAssertion, UUIDGeneratorService uuidGeneratorService, CreateProfileAttachmentRequestMapper createProfileAttachmentRequestMapper, StorageService storageService, ProfileAttachmentRepository profileAttachmentRepository) {
        this.profileExistsAssertion = profileExistsAssertion;
        this.uuidGeneratorService = uuidGeneratorService;
        this.createProfileAttachmentRequestMapper = createProfileAttachmentRequestMapper;
        this.storageService = storageService;
        this.profileAttachmentRepository = profileAttachmentRepository;
    }

    public ProfileAttachment run(CreateProfileAttachmentRequest request) {
        profileExistsAssertion.run(request.getProfileId());

        String fileName = storageService.save(request.getProfileId(), request.getFile());

        ProfileAttachment profileAttachment = createProfileAttachmentRequestMapper.toProfileAttachment(request, fileName);
        profileAttachment.setId(uuidGeneratorService.generate());
        return profileAttachmentRepository.save(profileAttachment);
    }
}
