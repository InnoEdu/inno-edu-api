package inno.edu.api.domain.attachment.commands;

import inno.edu.api.domain.attachment.commands.mappers.UpdateAttachmentRequestMapper;
import inno.edu.api.domain.attachment.models.Attachment;
import inno.edu.api.domain.attachment.queries.GetAttachmentByIdQuery;
import inno.edu.api.domain.attachment.repositories.AttachmentRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static inno.edu.api.support.AttachmentFactory.attachment;
import static inno.edu.api.support.AttachmentFactory.updateAttachmentRequest;
import static inno.edu.api.support.AttachmentFactory.updatedAttachment;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UpdateAttachmentCommandTest {
    @Mock
    private UpdateAttachmentRequestMapper updateAttachmentRequestMapper;

    @Mock
    private AttachmentRepository attachmentRepository;

    @Mock
    private GetAttachmentByIdQuery getAttachmentByIdQuery;

    @InjectMocks
    private UpdateAttachmentCommand updateAttachmentCommand;

    @Test
    public void shouldReturnUpdatedAttachment() {
        when(getAttachmentByIdQuery.run(attachment().getId())).thenReturn(attachment());
        when(attachmentRepository.save(attachment())).thenReturn(updatedAttachment());

        Attachment attachment = updateAttachmentCommand.run(attachment().getId(), updateAttachmentRequest());

        verify(updateAttachmentRequestMapper).setAttachment(updateAttachmentRequest(), attachment());

        assertThat(attachment, is(updatedAttachment()));
    }
}