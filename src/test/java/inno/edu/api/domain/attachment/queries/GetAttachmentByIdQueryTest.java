package inno.edu.api.domain.attachment.queries;

import inno.edu.api.domain.attachment.exceptions.AttachmentNotFoundException;
import inno.edu.api.domain.attachment.models.Attachment;
import inno.edu.api.domain.attachment.repositories.AttachmentRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static inno.edu.api.support.AttachmentFactory.attachment;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GetAttachmentByIdQueryTest {
    @Mock
    private AttachmentRepository attachmentRepository;

    @InjectMocks
    private GetAttachmentByIdQuery getAttachmentByIdQuery;

    @Test(expected = AttachmentNotFoundException.class)
    public void shouldThrowExceptionIfAttachmentDoesNotExist() {
        when(attachmentRepository.findOne(attachment().getId())).thenReturn(null);

        getAttachmentByIdQuery.run(attachment().getId());
    }

    @Test
    public void shouldReturnAttachment() {
        when(attachmentRepository.findOne(attachment().getId())).thenReturn(attachment());

        Attachment attachment = getAttachmentByIdQuery.run(attachment().getId());

        assertThat(attachment, is(attachment()));
    }
}