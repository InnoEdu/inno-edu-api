package inno.edu.api.domain.school.attachment.commands;

import inno.edu.api.domain.attachment.commands.CreateAttachmentCommand;
import inno.edu.api.domain.attachment.commands.DeleteAttachmentCommand;
import inno.edu.api.domain.attachment.models.Attachment;
import inno.edu.api.domain.school.root.models.School;
import inno.edu.api.domain.school.root.queries.GetSchoolByIdQuery;
import inno.edu.api.domain.school.root.repositories.SchoolRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.mock.web.MockMultipartFile;

import static inno.edu.api.domain.school.attachment.commands.UploadSchoolPhotoCommand.DESCRIPTION;
import static inno.edu.api.support.AttachmentFactory.attachment;
import static inno.edu.api.support.AttachmentFactory.createAttachmentRequestForFile;
import static inno.edu.api.support.SchoolFactory.stanford;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentCaptor.forClass;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UploadSchoolPhotoCommandTest {
    @Mock
    private CreateAttachmentCommand createAttachmentCommand;

    @Mock
    private GetSchoolByIdQuery getSchoolByIdQuery;

    @Mock
    private SchoolRepository schoolRepository;

    @Mock
    private DeleteAttachmentCommand deleteAttachmentCommand;

    @Mock
    private MockMultipartFile multipartFile;

    @InjectMocks
    private UploadSchoolPhotoCommand uploadPhotoAttachmentCommand;

    @Before
    public void setUp() {
        when(getSchoolByIdQuery.run(stanford().getId())).thenReturn(stanford());

        when(createAttachmentCommand.run(createAttachmentRequestForFile(DESCRIPTION, multipartFile)))
                .thenReturn(attachment());
    }

    @Test
    public void shouldCreateAttachment() {
        Attachment attachment = uploadPhotoAttachmentCommand.run(stanford().getId(), multipartFile);

        verify(deleteAttachmentCommand, never()).run(any());

        assertThat(attachment, is(attachment()));
    }

    @Test
    public void shouldSaveSchoolWithPhoto() {
        uploadPhotoAttachmentCommand.run(stanford().getId(), multipartFile);

        ArgumentCaptor<School> schoolArgumentCaptor = forClass(School.class);

        verify(schoolRepository).save(schoolArgumentCaptor.capture());

        assertThat(schoolArgumentCaptor.getValue().getPhotoId(), is(attachment().getId()));
    }

    @Test
    public void shouldDeleteOldAttachmentIfItExists() {
        School schoolWithPhoto = stanford().toBuilder()
                .photoId(attachment().getId())
                .build();

        when(getSchoolByIdQuery.run(stanford().getId())).thenReturn(schoolWithPhoto);

        uploadPhotoAttachmentCommand.run(stanford().getId(), multipartFile);

        verify(deleteAttachmentCommand).run(attachment().getId());
    }
}