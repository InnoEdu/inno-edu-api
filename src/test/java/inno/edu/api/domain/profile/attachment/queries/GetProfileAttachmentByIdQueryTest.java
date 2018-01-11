package inno.edu.api.domain.profile.attachment.queries;

import inno.edu.api.domain.profile.attachment.exceptions.ProfileAttachmentNotFoundException;
import inno.edu.api.domain.profile.attachment.models.ProfileAttachment;
import inno.edu.api.domain.profile.attachment.repositories.ProfileAttachmentRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static inno.edu.api.support.ProfileFactory.feiProfileAttachment;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GetProfileAttachmentByIdQueryTest {
    @Mock
    private ProfileAttachmentRepository profileAttachmentRepository;

    @InjectMocks
    private GetProfileAttachmentByIdQuery getProfileAttachmentByIdQuery;

    @Test(expected = ProfileAttachmentNotFoundException.class)
    public void shouldThrowExceptionIfProfileAttachmentDoesNotExist() {
        when(profileAttachmentRepository.findOne(feiProfileAttachment().getId())).thenReturn(null);

        getProfileAttachmentByIdQuery.run(feiProfileAttachment().getId());
    }

    @Test
    public void shouldReturnProfileAttachment() {
        when(profileAttachmentRepository.findOne(feiProfileAttachment().getId())).thenReturn(feiProfileAttachment());

        ProfileAttachment attachment = getProfileAttachmentByIdQuery.run(feiProfileAttachment().getId());

        assertThat(attachment, is(feiProfileAttachment()));
    }
}