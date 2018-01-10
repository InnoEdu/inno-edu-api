package inno.edu.api.controllers.profile;

import inno.edu.api.controllers.profile.resources.ProfileAttachmentResource;
import inno.edu.api.domain.profile.attachment.commands.UploadProfileAttachmentCommand;
import inno.edu.api.domain.profile.attachment.commands.dtos.UploadProfileAttachmentRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;

import static inno.edu.api.support.ProfileFactory.feiProfileAttachment;
import static inno.edu.api.support.ProfileFactory.feiUploadAttachmentRequest;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProfileAttachmentControllerTest {
    @Mock
    private UploadProfileAttachmentCommand uploadProfileContentCommand;

    @InjectMocks
    private ProfileAttachmentController profileAttachmentController;

    @Test
    public void shouldUploadContentForProfile() throws IOException {
        UploadProfileAttachmentRequest request = feiUploadAttachmentRequest();

        when(uploadProfileContentCommand.run(request)).thenReturn(feiProfileAttachment());

        ProfileAttachmentResource profileAttachmentResource = profileAttachmentController.upload(
                request.getProfileId(),
                request.getDescription(),
                request.getFile()
        );

        assertThat(profileAttachmentResource.getProfileAttachment(), is(feiProfileAttachment()));
    }
}