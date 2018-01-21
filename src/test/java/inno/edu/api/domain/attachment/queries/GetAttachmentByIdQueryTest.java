package inno.edu.api.domain.attachment.queries;

import inno.edu.api.domain.attachment.exceptions.AttachmentNotFoundException;
import inno.edu.api.domain.attachment.models.Attachment;
import inno.edu.api.domain.attachment.repositories.AttachmentRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static inno.edu.api.support.AttachmentFactory.feiProfilePhoto;
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
        when(attachmentRepository.findOne(feiProfilePhoto().getId())).thenReturn(null);

        getAttachmentByIdQuery.run(feiProfilePhoto().getId());
    }

    @Test
    public void shouldReturnAttachment() {
        when(attachmentRepository.findOne(feiProfilePhoto().getId())).thenReturn(feiProfilePhoto());

        Attachment attachment = getAttachmentByIdQuery.run(feiProfilePhoto().getId());

        assertThat(attachment, is(feiProfilePhoto()));
    }
}