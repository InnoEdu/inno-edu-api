package inno.edu.api.controllers.school;

import inno.edu.api.domain.attachment.models.dtos.CreateAttachmentRequest;
import inno.edu.api.domain.attachment.models.resources.AttachmentResource;
import inno.edu.api.domain.school.attachment.commands.CreateSchoolAttachmentCommand;
import inno.edu.api.domain.school.attachment.commands.DeleteSchoolAttachmentCommand;
import inno.edu.api.domain.school.attachment.commands.UploadSchoolPhotoCommand;
import inno.edu.api.domain.school.attachment.queries.GetSchoolAttachmentsBySchoolIdQuery;
import inno.edu.api.infrastructure.web.ResourceBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static inno.edu.api.support.AttachmentFactory.attachment;
import static inno.edu.api.support.AttachmentFactory.attachments;
import static inno.edu.api.support.AttachmentFactory.createAttachmentRequest;
import static inno.edu.api.support.SchoolFactory.stanford;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SchoolAttachmentControllerTest {
    @Mock
    private ResourceBuilder resourceBuilder;

    @Mock
    private GetSchoolAttachmentsBySchoolIdQuery getSchoolAttachmentsBySchoolIdQuery;

    @Mock
    private CreateSchoolAttachmentCommand createSchoolAttachmentCommand;

    @Mock
    private DeleteSchoolAttachmentCommand deleteSchoolAttachmentCommand;

    @Mock
    private UploadSchoolPhotoCommand uploadSchoolPhotoCommand;

    @InjectMocks
    private SchoolAttachmentController schoolAttachmentController;

    @Test
    public void shouldListAttachments() {
        when(getSchoolAttachmentsBySchoolIdQuery.run(stanford().getId())).thenReturn(attachments());

        schoolAttachmentController.all(stanford().getId());

        verify(resourceBuilder).wrappedFrom(eq(attachments()), any(), eq(AttachmentResource.class));
    }

    @Test
    public void shouldCreateAttachment() {
        CreateAttachmentRequest request = createAttachmentRequest();

        when(createSchoolAttachmentCommand.run(stanford().getId(), request)).thenReturn(attachment());

        AttachmentResource attachmentResource = schoolAttachmentController.post(
                stanford().getId(),
                request.getDescription(),
                request.getFile()
        );

        assertThat(attachmentResource.getAttachment(), is(attachment()));
    }

    @Test
    public void shouldDeleteAttachment() {
        schoolAttachmentController.delete(stanford().getId(), attachment().getId());

        verify(deleteSchoolAttachmentCommand).run(stanford().getId(), attachment().getId());
    }

    @Test
    public void shouldUploadPhoto() {
        CreateAttachmentRequest request = createAttachmentRequest();

        when(uploadSchoolPhotoCommand.run(stanford().getId(), request.getFile()))
                .thenReturn(attachment());

        AttachmentResource attachmentResource = schoolAttachmentController.upload(
                stanford().getId(),
                request.getFile()
        );

        assertThat(attachmentResource.getAttachment(), is(attachment()));
    }
}