package inno.edu.api.controllers.profile;

import inno.edu.api.domain.profile.attachment.commands.UploadProfileContentCommand;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.web.multipart.MultipartFile;

import static inno.edu.api.support.ProfileFactory.feiProfile;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class ProfileAttachmentControllerTest {
    @Mock
    private UploadProfileContentCommand uploadProfileContentCommand;

    @Mock
    private MultipartFile multipartFile;

    @InjectMocks
    private ProfileAttachmentController profileAttachmentController;

    @Test
    public void shouldUploadContentForProfile() {
        profileAttachmentController.upload(feiProfile().getId(), multipartFile);

        verify(uploadProfileContentCommand).run(feiProfile().getId(), multipartFile);
    }

}