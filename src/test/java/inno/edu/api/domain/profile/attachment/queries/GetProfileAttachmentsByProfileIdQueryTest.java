package inno.edu.api.domain.profile.attachment.queries;

import inno.edu.api.domain.profile.attachment.models.ProfileAttachment;
import inno.edu.api.domain.profile.attachment.repositories.ProfileAttachmentRepository;
import inno.edu.api.domain.profile.root.assertions.ProfileExistsAssertion;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static inno.edu.api.support.ProfileFactory.feiProfile;
import static inno.edu.api.support.ProfileFactory.feiProfileAttachments;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GetProfileAttachmentsByProfileIdQueryTest {
    @Mock
    private ProfileAttachmentRepository attachmentRepository;

    @Mock
    private ProfileExistsAssertion profileExistsAssertion;

    @InjectMocks
    private GetProfileAttachmentsByProfileIdQuery getProfileAttachmentsByProfileIdQuery;

    @Test
    public void shouldGetProfileAttachmentsByProfile() {
        when(attachmentRepository.findByProfileId(feiProfile().getId())).thenReturn(feiProfileAttachments());

        List<ProfileAttachment> attachments = getProfileAttachmentsByProfileIdQuery.run(feiProfile().getId());

        assertThat(attachments, is(feiProfileAttachments()));
    }

    @Test
    public void shouldRunAllAssertions() {
        getProfileAttachmentsByProfileIdQuery.run(feiProfile().getId());

        verify(profileExistsAssertion).run(feiProfile().getId());
    }

}