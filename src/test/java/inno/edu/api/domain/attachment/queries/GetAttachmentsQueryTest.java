package inno.edu.api.domain.attachment.queries;

import inno.edu.api.domain.attachment.models.Attachment;
import inno.edu.api.domain.attachment.repositories.AttachmentRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static inno.edu.api.support.AttachmentFactory.attachments;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GetAttachmentsQueryTest {
    @Mock
    private AttachmentRepository attachmentRepository;

    @InjectMocks
    private GetAttachmentsQuery getAttachmentsQuery;

    @Test
    public void shouldReturnAttachments() {
        when(attachmentRepository.findAll()).thenReturn(attachments());

        List<Attachment> attachments = getAttachmentsQuery.run();

        assertThat(attachments, is(attachments()));
    }

}