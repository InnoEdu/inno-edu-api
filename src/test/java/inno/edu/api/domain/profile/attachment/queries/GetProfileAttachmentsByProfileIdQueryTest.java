package inno.edu.api.domain.profile.attachment.queries;

import inno.edu.api.domain.attachment.models.Attachment;
import inno.edu.api.domain.profile.attachment.repositories.ProfileAttachmentRepository;
import inno.edu.api.domain.profile.root.assertions.ProfileExistsAssertion;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static inno.edu.api.support.AttachmentFactory.attachments;
import static inno.edu.api.support.ProfileFactory.feiProfile;
import static inno.edu.api.support.ProfileFactory.feiProfileAttachments;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GetProfileAttachmentsByProfileIdQueryTest {
    @Mock
    private ProfileExistsAssertion profileExistsAssertion;

    @Mock
    private ProfileAttachmentRepository profileAttachmentRepository;

    @InjectMocks
    private GetProfileAttachmentsByProfileIdQuery getProfileAttachmentsByProfileIdQuery;

    @Before
    public void setUp() {
        when(profileAttachmentRepository.findByProfileId(feiProfile().getId()))
                .thenReturn(feiProfileAttachments());
    }

    @Test
    public void shouldReturnProfileAttachments() {
        List<Attachment> actualAttachments = getProfileAttachmentsByProfileIdQuery.run(feiProfile().getId());

        assertThat(actualAttachments, is(attachments()));
    }

    @Test
    public void shouldRunAllAssertions() {
        getProfileAttachmentsByProfileIdQuery.run(feiProfile().getId());

        verify(profileExistsAssertion).run(feiProfile().getId());
    }
}