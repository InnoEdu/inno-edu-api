package inno.edu.api.domain.profile.attachment.commands;

import inno.edu.api.domain.profile.attachment.commands.dtos.UploadProfileAttachmentRequest;
import inno.edu.api.domain.profile.root.assertions.ProfileExistsAssertion;
import inno.edu.api.infrastructure.storage.StorageService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static inno.edu.api.support.ProfileFactory.feiUploadAttachmentRequest;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class UploadProfileAttachmentCommandTest {
    @Mock
    private StorageService storageService;

    @Mock
    private ProfileExistsAssertion profileExistsAssertion;

    @InjectMocks
    private UploadProfileAttachmentCommand uploadProfileAttachmentCommand;

    @Test
    public void shouldCallStorageToSaveContent() {
        UploadProfileAttachmentRequest request = feiUploadAttachmentRequest();

        uploadProfileAttachmentCommand.run(request);

        verify(storageService).save(request.getProfileId(), request.getFile());
    }

    @Test
    public void shouldRunAllAssertions() {
        uploadProfileAttachmentCommand.run(feiUploadAttachmentRequest());

        verify(profileExistsAssertion).run(feiUploadAttachmentRequest().getProfileId());
    }
}