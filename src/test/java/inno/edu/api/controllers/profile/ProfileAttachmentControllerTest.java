package inno.edu.api.controllers.profile;

import inno.edu.api.domain.attachment.models.resources.AttachmentResource;
import inno.edu.api.infrastructure.web.ResourceBuilder;
import inno.edu.api.domain.attachment.models.dtos.CreateAttachmentRequest;
import inno.edu.api.domain.profile.attachment.commands.CreateProfileAttachmentCommand;
import inno.edu.api.domain.profile.attachment.commands.DeleteProfileAttachmentCommand;
import inno.edu.api.domain.profile.attachment.commands.UploadProfilePhotoCommand;
import inno.edu.api.domain.profile.attachment.queries.GetProfileAttachmentsByProfileIdQuery;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.ServletRequestAttributes;

import static inno.edu.api.support.AttachmentFactory.feiProfilePhoto;
import static inno.edu.api.support.AttachmentFactory.attachments;
import static inno.edu.api.support.AttachmentFactory.createAttachmentRequest;
import static inno.edu.api.support.ProfileFactory.feiProfile;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.web.context.request.RequestContextHolder.setRequestAttributes;

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

    @Mock
    private UploadProfilePhotoCommand uploadPhotoAttachmentCommand;

    @InjectMocks
    private ProfileAttachmentController profileAttachmentController;

    @Before
    public void setup() {
        setRequestAttributes(new ServletRequestAttributes(new MockHttpServletRequest()));
    }

    @Test
    public void shouldListAttachments() {
        when(getProfileAttachmentsByProfileIdQuery.run(feiProfile().getId())).thenReturn(attachments());

        profileAttachmentController.all(feiProfile().getId());

        verify(resourceBuilder).wrappedFrom(eq(attachments()), any(), eq(AttachmentResource.class));
    }

    @Test
    public void shouldCreateAttachment() {
        CreateAttachmentRequest request = createAttachmentRequest();

        when(createProfileAttachmentCommand.run(feiProfile().getId(), request)).thenReturn(feiProfilePhoto());

        AttachmentResource attachmentResource = profileAttachmentController.post(
                feiProfile().getId(),
                request.getDescription(),
                request.getFile()
        );

        assertThat(attachmentResource.getAttachment(), is(feiProfilePhoto()));
    }

    @Test
    public void shouldDeleteAttachment() {
        profileAttachmentController.delete(feiProfile().getId(), feiProfilePhoto().getId());

        verify(deleteProfileAttachmentCommand).run(feiProfile().getId(), feiProfilePhoto().getId());
    }

    @Test
    public void shouldUploadPhoto() {
        CreateAttachmentRequest request = createAttachmentRequest();

        when(uploadPhotoAttachmentCommand.run(feiProfile().getId(), request.getFile()))
                .thenReturn(feiProfilePhoto());

        AttachmentResource attachmentResource = profileAttachmentController.upload(
                feiProfile().getId(),
                request.getFile()
        );

        assertThat(attachmentResource.getAttachment(), is(feiProfilePhoto()));
    }
}