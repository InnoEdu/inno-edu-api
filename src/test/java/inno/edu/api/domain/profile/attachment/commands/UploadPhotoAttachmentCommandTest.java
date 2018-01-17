package inno.edu.api.domain.profile.attachment.commands;

import inno.edu.api.domain.attachment.commands.CreateAttachmentCommand;
import inno.edu.api.domain.attachment.commands.DeleteAttachmentCommand;
import inno.edu.api.domain.attachment.models.Attachment;
import inno.edu.api.domain.profile.root.models.Profile;
import inno.edu.api.domain.profile.root.queries.GetProfileByIdQuery;
import inno.edu.api.domain.profile.root.repositories.ProfileRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.mock.web.MockMultipartFile;

import static inno.edu.api.domain.profile.attachment.commands.UploadPhotoAttachmentCommand.DESCRIPTION;
import static inno.edu.api.support.AttachmentFactory.attachment;
import static inno.edu.api.support.AttachmentFactory.createAttachmentRequestForFile;
import static inno.edu.api.support.ProfileFactory.feiProfile;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentCaptor.forClass;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UploadPhotoAttachmentCommandTest {
    @Mock
    private CreateAttachmentCommand createAttachmentCommand;

    @Mock
    private GetProfileByIdQuery getProfileByIdQuery;

    @Mock
    private ProfileRepository profileRepository;

    @Mock
    private DeleteAttachmentCommand deleteAttachmentCommand;

    @Mock
    private MockMultipartFile multipartFile;

    @InjectMocks
    private UploadPhotoAttachmentCommand uploadPhotoAttachmentCommand;

    @Before
    public void setUp() {
        when(getProfileByIdQuery.run(feiProfile().getId())).thenReturn(feiProfile());

        when(createAttachmentCommand.run(createAttachmentRequestForFile(DESCRIPTION, multipartFile)))
                .thenReturn(attachment());
    }

    @Test
    public void shouldCreateAttachment() {
        Attachment attachment = uploadPhotoAttachmentCommand.run(feiProfile().getId(), multipartFile);

        verify(deleteAttachmentCommand, never()).run(any());

        assertThat(attachment, is(attachment()));
    }

    @Test
    public void shouldSaveProfileWithPhoto() {
        uploadPhotoAttachmentCommand.run(feiProfile().getId(), multipartFile);

        ArgumentCaptor<Profile> profileArgumentCaptor = forClass(Profile.class);

        verify(profileRepository).save(profileArgumentCaptor.capture());

        assertThat(profileArgumentCaptor.getValue().getPhotoId(), is(attachment().getId()));
    }

    @Test
    public void shouldDeleteOldAttachmentIfItExists() {
        Profile profileWithPhoto = feiProfile().toBuilder()
                .photoId(attachment().getId())
                .build();

        when(getProfileByIdQuery.run(feiProfile().getId())).thenReturn(profileWithPhoto);

        uploadPhotoAttachmentCommand.run(feiProfile().getId(), multipartFile);

        verify(deleteAttachmentCommand).run(attachment().getId());
    }
}