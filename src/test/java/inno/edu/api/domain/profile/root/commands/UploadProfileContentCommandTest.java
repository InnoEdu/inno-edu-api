package inno.edu.api.domain.profile.root.commands;

import inno.edu.api.domain.profile.attachment.commands.UploadProfileContentCommand;
import inno.edu.api.domain.profile.root.assertions.ProfileExistsAssertion;
import inno.edu.api.infrastructure.storage.StorageService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.web.multipart.MultipartFile;

import static inno.edu.api.support.ProfileFactory.feiProfile;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class UploadProfileContentCommandTest {
    @Mock
    private StorageService storageService;

    @Mock
    private ProfileExistsAssertion profileExistsAssertion;

    @Mock
    private MultipartFile multipartFile;

    @InjectMocks
    private UploadProfileContentCommand uploadProfileContentCommand;

    @Test
    public void shouldCallStorageToSaveContent() {
        uploadProfileContentCommand.run(feiProfile().getId(), multipartFile);

        verify(storageService).save(feiProfile().getId(), multipartFile);
    }

    @Test
    public void shouldRunAllAssertions() {
        uploadProfileContentCommand.run(feiProfile().getId(), multipartFile);

        verify(profileExistsAssertion).run(feiProfile().getId());
    }
}