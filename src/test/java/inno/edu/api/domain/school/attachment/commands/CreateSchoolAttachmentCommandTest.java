package inno.edu.api.domain.school.attachment.commands;

import inno.edu.api.domain.attachment.commands.CreateAttachmentCommand;
import inno.edu.api.domain.attachment.models.dtos.CreateAttachmentRequest;
import inno.edu.api.domain.school.attachment.models.SchoolAttachment;
import inno.edu.api.domain.school.attachment.repositories.SchoolAttachmentRepository;
import inno.edu.api.domain.school.root.assertions.SchoolExistsAssertion;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static inno.edu.api.support.AttachmentFactory.attachment;
import static inno.edu.api.support.AttachmentFactory.createAttachmentRequest;
import static inno.edu.api.support.SchoolFactory.stanford;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CreateSchoolAttachmentCommandTest {
    @Mock
    private SchoolExistsAssertion schoolExistsAssertion;

    @Mock
    private CreateAttachmentCommand createAttachmentCommand;

    @Mock
    private SchoolAttachmentRepository schoolAttachmentRepository;

    @InjectMocks
    private CreateSchoolAttachmentCommand createSchoolAttachmentCommand;

    @Before
    public void setUp() {
        when(createAttachmentCommand.run(any())).thenReturn(attachment());
    }

    @Test
    public void shouldCreateAttachment() {
        CreateAttachmentRequest attachmentRequest = createAttachmentRequest();

        createSchoolAttachmentCommand.run(stanford().getId(), attachmentRequest);

        verify(createAttachmentCommand).run(attachmentRequest);
    }

    @Test
    public void shouldCreateSchoolAttachment() {
        CreateAttachmentRequest attachmentRequest = createAttachmentRequest();

        createSchoolAttachmentCommand.run(stanford().getId(), attachmentRequest);

        ArgumentCaptor<SchoolAttachment> captor = ArgumentCaptor.forClass(SchoolAttachment.class);

        verify(schoolAttachmentRepository).save(captor.capture());

        assertThat(captor.getValue().getSchoolId(), is(stanford().getId()));
        assertThat(captor.getValue().getAttachmentId(), is(attachment().getId()));
    }

    @Test
    public void shouldRunAllAssertions() {
        createSchoolAttachmentCommand.run(stanford().getId(), createAttachmentRequest());

        verify(schoolExistsAssertion).run(stanford().getId());
    }
}