package inno.edu.api.domain.school.attachment.queries;

import inno.edu.api.domain.attachment.models.Attachment;
import inno.edu.api.domain.school.attachment.repositories.SchoolAttachmentRepository;
import inno.edu.api.domain.school.root.assertions.SchoolExistsAssertion;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static inno.edu.api.support.AttachmentFactory.otherAttachments;
import static inno.edu.api.support.SchoolFactory.stanford;
import static inno.edu.api.support.SchoolFactory.stanfordAttachments;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GetSchoolAttachmentsBySchoolIdQueryTest {
    @Mock
    private SchoolExistsAssertion schoolExistsAssertion;

    @Mock
    private SchoolAttachmentRepository schoolAttachmentRepository;

    @InjectMocks
    private GetSchoolAttachmentsBySchoolIdQuery getSchoolAttachmentsBySchoolIdQuery;

    @Before
    public void setUp() {
        when(schoolAttachmentRepository.findBySchoolId(stanford().getId()))
                .thenReturn(stanfordAttachments());
    }

    @Test
    public void shouldReturnSchoolAttachments() {
        List<Attachment> actualAttachments = getSchoolAttachmentsBySchoolIdQuery.run(stanford().getId());

        assertThat(actualAttachments, is(otherAttachments()));
    }

    @Test
    public void shouldRunAllAssertions() {
        getSchoolAttachmentsBySchoolIdQuery.run(stanford().getId());

        verify(schoolExistsAssertion).run(stanford().getId());
    }
}