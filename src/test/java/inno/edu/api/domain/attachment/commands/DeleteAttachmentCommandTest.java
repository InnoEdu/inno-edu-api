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

import static inno.edu.api.support.AttachmentFactory.attachment;
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
        when(attachmentRepository.findOne(attachment().getId()))
                .thenReturn(attachment());
    }

    @Test
    public void shouldCallRepositoryToDeleteAttachment() {
        deleteAttachmentCommand.run(attachment().getId());

        verify(attachmentRepository).delete(attachment().getId());
    }

    @Test
    public void shouldCallStorageServiceToDeleteFile() {
        deleteAttachmentCommand.run(attachment().getId());

        verify(storageService).delete(attachment().getUrl());
    }

    @Test
    public void shouldRunAllAssertions() {
        deleteAttachmentCommand.run(attachment().getId());

        verify(attachmentExistsAssertion).run(attachment().getId());
    }
}