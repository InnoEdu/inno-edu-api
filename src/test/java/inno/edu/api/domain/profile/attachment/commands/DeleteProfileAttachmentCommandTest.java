package inno.edu.api.domain.profile.attachment.commands;

import inno.edu.api.domain.profile.attachment.assertions.ProfileAttachmentExistsAssertion;
import inno.edu.api.domain.profile.attachment.repositories.ProfileAttachmentRepository;
import inno.edu.api.infrastructure.storage.services.StorageService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static inno.edu.api.support.ProfileFactory.feiProfileAttachment;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DeleteProfileAttachmentCommandTest {
    @Mock
    private ProfileAttachmentExistsAssertion profileAttachmentExistsAssertion;

    @Mock
    private StorageService storageService;

    @Mock
    private ProfileAttachmentRepository profileAttachmentRepository;

    @InjectMocks
    private DeleteProfileAttachmentCommand deleteProfileAttachmentCommand;

    @Before
    public void setUp() {
        when(profileAttachmentRepository.findOne(feiProfileAttachment().getId()))
                .thenReturn(feiProfileAttachment());
    }

    @Test
    public void shouldCallRepositoryToDeleteAttachment() {
        deleteProfileAttachmentCommand.run(feiProfileAttachment().getId());

        verify(profileAttachmentRepository).delete(feiProfileAttachment().getId());
    }

    @Test
    public void shouldCallStorageServiceToDeleteFile() {
        deleteProfileAttachmentCommand.run(feiProfileAttachment().getId());

        verify(storageService).delete(feiProfileAttachment().getUrl());
    }

    @Test
    public void shouldRunAllAssertions() {
        deleteProfileAttachmentCommand.run(feiProfileAttachment().getId());

        verify(profileAttachmentExistsAssertion).run(feiProfileAttachment().getId());
    }
}