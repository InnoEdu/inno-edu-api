package inno.edu.api.domain.profile.attachment.commands;

import inno.edu.api.domain.attachment.assertions.AttachmentExistsAssertion;
import inno.edu.api.domain.attachment.commands.DeleteAttachmentCommand;
import inno.edu.api.domain.profile.attachment.models.ProfileAttachmentPrimaryKey;
import inno.edu.api.domain.profile.attachment.repositories.ProfileAttachmentRepository;
import inno.edu.api.domain.profile.root.assertions.ProfileExistsAssertion;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static inno.edu.api.support.AttachmentFactory.attachment;
import static inno.edu.api.support.ProfileFactory.feiProfile;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentCaptor.forClass;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class DeleteProfileAttachmentCommandTest {
    @Mock
    private ProfileExistsAssertion profileExistsAssertion;

    @Mock
    private AttachmentExistsAssertion attachmentExistsAssertion;

    @Mock
    private ProfileAttachmentRepository profileAttachmentRepository;

    @Mock
    private DeleteAttachmentCommand deleteAttachmentCommand;

    @InjectMocks
    private DeleteProfileAttachmentCommand deleteProfileAttachmentCommand;

    @Test
    public void shouldDeleteAttachment() {
        deleteProfileAttachmentCommand.run(feiProfile().getId(), attachment().getId());

        verify(deleteAttachmentCommand).run(attachment().getId());
    }

    @Test
    public void shouldDeleteProfileAttachment() {
        deleteProfileAttachmentCommand.run(feiProfile().getId(), attachment().getId());

        ArgumentCaptor<ProfileAttachmentPrimaryKey> primaryKeyArgumentCaptor = forClass(ProfileAttachmentPrimaryKey.class);
        verify(profileAttachmentRepository).delete(primaryKeyArgumentCaptor.capture());

        assertThat(primaryKeyArgumentCaptor.getValue().getProfileId(), is(feiProfile().getId()));
        assertThat(primaryKeyArgumentCaptor.getValue().getAttachmentId(), is(attachment().getId()));
    }

    @Test
    public void shouldRunAllAssertions() {
        deleteProfileAttachmentCommand.run(feiProfile().getId(), attachment().getId());

        verify(profileExistsAssertion).run(feiProfile().getId());
        verify(attachmentExistsAssertion).run(attachment().getId());
    }
}