package inno.edu.api.controllers.profile;

import inno.edu.api.controllers.resources.AttachmentResource;
import inno.edu.api.controllers.resources.ResourceBuilder;
import inno.edu.api.domain.attachment.commands.dtos.CreateAttachmentRequest;
import inno.edu.api.domain.profile.attachment.commands.CreateProfileAttachmentCommand;
import inno.edu.api.domain.profile.attachment.commands.DeleteProfileAttachmentCommand;
import inno.edu.api.domain.profile.attachment.queries.GetProfileAttachmentsByProfileIdQuery;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static inno.edu.api.support.AttachmentFactory.attachment;
import static inno.edu.api.support.AttachmentFactory.attachments;
import static inno.edu.api.support.AttachmentFactory.createAttachmentRequest;
import static inno.edu.api.support.ProfileFactory.feiProfile;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProfileAttachmentControllerTest {
    @Mock
    private ResourceBuilder resourceBuilder;

    @Mock
    private GetProfileAttachmentsByProfileIdQuery getProfileAttachmentsByProfileIdQuery;

    @Mock
    private CreateProfileAttachmentCommand createProfileAttachmentCommand;

    @Mock
    private DeleteProfileAttachmentCommand deleteProfileAttachmentCommand;

    @InjectMocks
    private ProfileAttachmentController profileAttachmentController;

    @Test
    public void shouldListAttachments() {
        when(getProfileAttachmentsByProfileIdQuery.run(feiProfile().getId())).thenReturn(attachments());

        profileAttachmentController.all(feiProfile().getId());

        verify(resourceBuilder).wrappedFrom(eq(attachments()), any(), eq(AttachmentResource.class));
    }

    @Test
    public void shouldCreateAttachment() {
        CreateAttachmentRequest request = createAttachmentRequest();

        when(createProfileAttachmentCommand.run(feiProfile().getId(), request)).thenReturn(attachment());

        AttachmentResource attachmentResource = profileAttachmentController.post(
                feiProfile().getId(),
                request.getDescription(),
                request.getFile()
        );

        assertThat(attachmentResource.getAttachment(), is(attachment()));
    }

    @Test
    public void shouldDeleteAttachment() {
        profileAttachmentController.delete(feiProfile().getId(), attachment().getId());

        verify(deleteProfileAttachmentCommand).run(feiProfile().getId(), attachment().getId());
    }
}