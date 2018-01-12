package inno.edu.api.domain.school.attachment.commands;

import inno.edu.api.domain.attachment.assertions.AttachmentExistsAssertion;
import inno.edu.api.domain.attachment.commands.DeleteAttachmentCommand;
import inno.edu.api.domain.school.attachment.models.SchoolAttachmentPrimaryKey;
import inno.edu.api.domain.school.attachment.repositories.SchoolAttachmentRepository;
import inno.edu.api.domain.school.root.assertions.SchoolExistsAssertion;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static inno.edu.api.support.AttachmentFactory.attachment;
import static inno.edu.api.support.SchoolFactory.stanford;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentCaptor.forClass;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class DeleteSchoolAttachmentCommandTest {
    @Mock
    private SchoolExistsAssertion schoolExistsAssertion;

    @Mock
    private AttachmentExistsAssertion attachmentExistsAssertion;

    @Mock
    private SchoolAttachmentRepository schoolAttachmentRepository;

    @Mock
    private DeleteAttachmentCommand deleteAttachmentCommand;

    @InjectMocks
    private DeleteSchoolAttachmentCommand deleteSchoolAttachmentCommand;

    @Test
    public void shouldDeleteAttachment() {
        deleteSchoolAttachmentCommand.run(stanford().getId(), attachment().getId());

        verify(deleteAttachmentCommand).run(attachment().getId());
    }

    @Test
    public void shouldDeleteSchoolAttachment() {
        deleteSchoolAttachmentCommand.run(stanford().getId(), attachment().getId());

        ArgumentCaptor<SchoolAttachmentPrimaryKey> primaryKeyArgumentCaptor = forClass(SchoolAttachmentPrimaryKey.class);
        verify(schoolAttachmentRepository).delete(primaryKeyArgumentCaptor.capture());

        assertThat(primaryKeyArgumentCaptor.getValue().getSchoolId(), is(stanford().getId()));
        assertThat(primaryKeyArgumentCaptor.getValue().getAttachmentId(), is(attachment().getId()));
    }

    @Test
    public void shouldRunAllAssertions() {
        deleteSchoolAttachmentCommand.run(stanford().getId(), attachment().getId());

        verify(schoolExistsAssertion).run(stanford().getId());
        verify(attachmentExistsAssertion).run(attachment().getId());
    }
}