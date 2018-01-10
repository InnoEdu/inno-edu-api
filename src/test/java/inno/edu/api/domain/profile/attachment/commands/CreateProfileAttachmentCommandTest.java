package inno.edu.api.domain.profile.attachment.commands;

import inno.edu.api.domain.profile.attachment.commands.dtos.CreateProfileAttachmentRequest;
import inno.edu.api.domain.profile.attachment.commands.mappers.CreateProfileAttachmentRequestMapper;
import inno.edu.api.domain.profile.attachment.models.ProfileAttachment;
import inno.edu.api.domain.profile.attachment.repositories.ProfileAttachmentRepository;
import inno.edu.api.domain.profile.root.assertions.ProfileExistsAssertion;
import inno.edu.api.infrastructure.services.UUIDGeneratorService;
import inno.edu.api.infrastructure.storage.StorageService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static inno.edu.api.support.ProfileFactory.feiProfileAttachment;
import static inno.edu.api.support.ProfileFactory.feiCreateAttachmentRequest;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CreateProfileAttachmentCommandTest {
    @Mock
    private ProfileExistsAssertion profileExistsAssertion;

    @Mock
    private UUIDGeneratorService uuidGeneratorService;

    @Mock
    private CreateProfileAttachmentRequestMapper createProfileAttachmentRequestMapper;

    @Mock
    private StorageService storageService;

    @Mock
    private ProfileAttachmentRepository profileAttachmentRepository;

    @InjectMocks
    private CreateProfileAttachmentCommand createProfileAttachmentCommand;

    @Before
    public void setUp() {
        when(createProfileAttachmentRequestMapper.toProfileAttachment(any(), any()))
                .thenReturn(feiProfileAttachment());

        when(profileAttachmentRepository.save(feiProfileAttachment()))
                .then((answer) -> answer.getArguments()[0]);

        when(uuidGeneratorService.generate()).thenReturn(feiProfileAttachment().getId());
    }

    @Test
    public void shouldCallStorageToSaveContent() {
        CreateProfileAttachmentRequest request = feiCreateAttachmentRequest();

        createProfileAttachmentCommand.run(request);

        verify(storageService).save(request.getProfileId(), request.getFile());
    }

    @Test
    public void shouldCallRepositoryToSaveAttachment() {
        CreateProfileAttachmentRequest request = feiCreateAttachmentRequest();

        ProfileAttachment profileAttachment = createProfileAttachmentCommand.run(request);

        assertThat(profileAttachment, is(feiProfileAttachment()));
    }

    @Test
    public void shouldRunAllAssertions() {
        createProfileAttachmentCommand.run(feiCreateAttachmentRequest());

        verify(profileExistsAssertion).run(feiCreateAttachmentRequest().getProfileId());
    }
}