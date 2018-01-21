package inno.edu.api.domain.attachment.commands;

import inno.edu.api.domain.attachment.assertions.AttachmentExistsAssertion;
import inno.edu.api.domain.attachment.repositories.AttachmentRepository;
import inno.edu.api.infrastructure.storage.services.StorageService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static inno.edu.api.support.AttachmentFactory.feiProfilePhoto;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DeleteAttachmentCommandTest {
    @Mock
    private AttachmentExistsAssertion attachmentExistsAssertion;

    @Mock
    private StorageService storageService;

    @Mock
    private AttachmentRepository attachmentRepository;

    @InjectMocks
    private DeleteAttachmentCommand deleteAttachmentCommand;

    @Before
    public void setUp() {
        when(attachmentRepository.findOne(feiProfilePhoto().getId()))
                .thenReturn(feiProfilePhoto());
    }

    @Test
    public void shouldCallRepositoryToDeleteAttachment() {
        deleteAttachmentCommand.run(feiProfilePhoto().getId());

        verify(attachmentRepository).delete(feiProfilePhoto().getId());
    }

    @Test
    public void shouldCallStorageServiceToDeleteFile() {
        deleteAttachmentCommand.run(feiProfilePhoto().getId());

        verify(storageService).delete(feiProfilePhoto().getUrl());
    }

    @Test
    public void shouldRunAllAssertions() {
        deleteAttachmentCommand.run(feiProfilePhoto().getId());

        verify(attachmentExistsAssertion).run(feiProfilePhoto().getId());
    }
}