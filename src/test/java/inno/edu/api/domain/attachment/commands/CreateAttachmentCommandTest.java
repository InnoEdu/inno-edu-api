package inno.edu.api.domain.attachment.commands;

import inno.edu.api.domain.attachment.models.dtos.CreateAttachmentRequest;
import inno.edu.api.domain.attachment.models.dtos.mappers.CreateAttachmentRequestMapper;
import inno.edu.api.domain.attachment.models.Attachment;
import inno.edu.api.domain.attachment.repositories.AttachmentRepository;
import inno.edu.api.infrastructure.services.UUIDGeneratorService;
import inno.edu.api.infrastructure.storage.services.StorageService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static inno.edu.api.support.AttachmentFactory.attachment;
import static inno.edu.api.support.AttachmentFactory.createAttachmentRequest;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CreateAttachmentCommandTest {
    @Mock
    private UUIDGeneratorService uuidGeneratorService;

    @Mock
    private CreateAttachmentRequestMapper createAttachmentRequestMapper;

    @Mock
    private StorageService storageService;

    @Mock
    private AttachmentRepository attachmentRepository;

    @InjectMocks
    private CreateAttachmentCommand createAttachmentCommand;

    @Before
    public void setUp() {
        when(createAttachmentRequestMapper.toAttachment(any(), any()))
                .thenReturn(attachment());

        when(attachmentRepository.save(attachment()))
                .then((answer) -> answer.getArguments()[0]);

        when(uuidGeneratorService.generate()).thenReturn(attachment().getId());
    }

    @Test
    public void shouldCallStorageToSaveFile() {
        CreateAttachmentRequest request = createAttachmentRequest();

        createAttachmentCommand.run(request);

        verify(storageService).save(attachment().getId(), request.getFile());
    }

    @Test
    public void shouldCallRepositoryToSaveAttachment() {
        CreateAttachmentRequest request = createAttachmentRequest();

        Attachment attachment = createAttachmentCommand.run(request);

        assertThat(attachment, is(attachment()));
    }
}