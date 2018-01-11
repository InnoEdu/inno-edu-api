package inno.edu.api.controllers;

import inno.edu.api.controllers.resources.AttachmentResource;
import inno.edu.api.domain.attachment.commands.CreateAttachmentCommand;
import inno.edu.api.domain.attachment.commands.DeleteAttachmentCommand;
import inno.edu.api.domain.attachment.commands.dtos.CreateAttachmentRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static inno.edu.api.support.AttachmentFactory.attachment;
import static inno.edu.api.support.AttachmentFactory.createAttachmentRequest;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AttachmentControllerTest {
    @Mock
    private CreateAttachmentCommand createAttachmentCommand;

    @Mock
    private DeleteAttachmentCommand deleteAttachmentCommand;

    @InjectMocks
    private AttachmentController attachmentController;

    @Test
    public void shouldCreateAttachment() {
        CreateAttachmentRequest request = createAttachmentRequest();

        when(createAttachmentCommand.run(request)).thenReturn(attachment());

        AttachmentResource attachmentResource = attachmentController.post(
                request.getDescription(),
                request.getFile()
        );

        assertThat(attachmentResource.getAttachment(), is(attachment()));
    }

    @Test
    public void shouldDeleteAttachment() {
        attachmentController.delete(attachment().getId());

        verify(deleteAttachmentCommand).run(attachment().getId());
    }
}