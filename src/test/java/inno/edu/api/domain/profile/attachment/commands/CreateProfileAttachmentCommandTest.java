package inno.edu.api.domain.profile.attachment.commands;

import inno.edu.api.domain.attachment.commands.CreateAttachmentCommand;
import inno.edu.api.domain.attachment.models.dtos.CreateAttachmentRequest;
import inno.edu.api.domain.profile.attachment.models.ProfileAttachment;
import inno.edu.api.domain.profile.attachment.repositories.ProfileAttachmentRepository;
import inno.edu.api.domain.profile.root.assertions.ProfileExistsAssertion;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static inno.edu.api.support.AttachmentFactory.attachment;
import static inno.edu.api.support.AttachmentFactory.createAttachmentRequest;
import static inno.edu.api.support.ProfileFactory.feiProfile;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CreateProfileAttachmentCommandTest {
    @Mock
    private ProfileExistsAssertion profileExistsAssertion;

    @Mock
    private CreateAttachmentCommand createAttachmentCommand;

    @Mock
    private ProfileAttachmentRepository profileAttachmentRepository;

    @InjectMocks
    private CreateProfileAttachmentCommand createProfileAttachmentCommand;

    @Before
    public void setUp() {
        when(createAttachmentCommand.run(any())).thenReturn(attachment());
    }

    @Test
    public void shouldCreateAttachment() {
        CreateAttachmentRequest attachmentRequest = createAttachmentRequest();

        createProfileAttachmentCommand.run(feiProfile().getId(), attachmentRequest);

        verify(createAttachmentCommand).run(attachmentRequest);
    }

    @Test
    public void shouldCreateProfileAttachment() {
        CreateAttachmentRequest attachmentRequest = createAttachmentRequest();

        createProfileAttachmentCommand.run(feiProfile().getId(), attachmentRequest);

        ArgumentCaptor<ProfileAttachment> captor = ArgumentCaptor.forClass(ProfileAttachment.class);

        verify(profileAttachmentRepository).save(captor.capture());

        assertThat(captor.getValue().getProfileId(), is(feiProfile().getId()));
        assertThat(captor.getValue().getAttachmentId(), is(attachment().getId()));
    }

    @Test
    public void shouldRunAllAssertions() {
        createProfileAttachmentCommand.run(feiProfile().getId(), createAttachmentRequest());

        verify(profileExistsAssertion).run(feiProfile().getId());
    }
}