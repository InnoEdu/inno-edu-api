package inno.edu.api.controllers.profile;

import inno.edu.api.domain.profile.attachment.commands.UploadProfileAttachmentCommand;
import inno.edu.api.domain.profile.attachment.commands.dtos.UploadProfileAttachmentRequest;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static inno.edu.api.support.ProfileFactory.feiUploadAttachmentRequest;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentCaptor.forClass;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class ProfileAttachmentControllerTest {
    @Mock
    private UploadProfileAttachmentCommand uploadProfileContentCommand;

    @Mock
    private MultipartFile multipartFile;

    @InjectMocks
    private ProfileAttachmentController profileAttachmentController;

    @Test
    public void shouldUploadContentForProfile() throws IOException {
        ArgumentCaptor<UploadProfileAttachmentRequest> argumentCaptor = forClass(UploadProfileAttachmentRequest.class);

        profileAttachmentController.upload(
                feiUploadAttachmentRequest().getProfileId(),
                feiUploadAttachmentRequest().getDescription(),
                feiUploadAttachmentRequest().getFile()
        );

        verify(uploadProfileContentCommand).run(argumentCaptor.capture());

        assertThat(argumentCaptor.getValue().getProfileId(), is(feiUploadAttachmentRequest().getProfileId()));
        assertThat(argumentCaptor.getValue().getDescription(), is(feiUploadAttachmentRequest().getDescription()));
        assertThat(IOUtils.toString(argumentCaptor.getValue().getFile().getInputStream()), is(IOUtils.toString(feiUploadAttachmentRequest().getFile().getInputStream())));
    }
}