package inno.edu.api.controllers.profile;

import inno.edu.api.controllers.profile.resources.ProfileAttachmentResource;
import inno.edu.api.domain.profile.attachment.commands.CreateProfileAttachmentCommand;
import inno.edu.api.domain.profile.attachment.commands.DeleteProfileAttachmentCommand;
import inno.edu.api.domain.profile.attachment.commands.dtos.CreateProfileAttachmentRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static inno.edu.api.support.ProfileFactory.feiCreateAttachmentRequest;
import static inno.edu.api.support.ProfileFactory.feiProfileAttachment;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProfileAttachmentControllerTest {
    @Mock
    private CreateProfileAttachmentCommand uploadProfileContentCommand;

    @Mock
    private DeleteProfileAttachmentCommand deleteProfileAttachmentCommand;

    @InjectMocks
    private ProfileAttachmentController profileAttachmentController;

    @Test
    public void shouldCreateAttachmentForProfile() {
        CreateProfileAttachmentRequest request = feiCreateAttachmentRequest();

        when(uploadProfileContentCommand.run(request)).thenReturn(feiProfileAttachment());

        ProfileAttachmentResource profileAttachmentResource = profileAttachmentController.post(
                request.getProfileId(),
                request.getDescription(),
                request.getFile()
        );

        assertThat(profileAttachmentResource.getProfileAttachment(), is(feiProfileAttachment()));
    }

    @Test
    public void shouldDeleteAttachmentForProfile() {
        profileAttachmentController.delete(feiProfileAttachment().getId());

        verify(deleteProfileAttachmentCommand).run(feiProfileAttachment().getId());
    }
}