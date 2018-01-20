package inno.edu.api.support;

import inno.edu.api.domain.attachment.models.dtos.CreateAttachmentRequest;
import inno.edu.api.domain.attachment.models.dtos.UpdateAttachmentRequest;
import inno.edu.api.domain.attachment.models.Attachment;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static inno.edu.api.infrastructure.configuration.StaticConstants.NO_FILE;
import static java.util.Collections.singletonList;
import static java.util.UUID.fromString;

public class AttachmentFactory {
    public static Attachment attachment() {
        return Attachment.builder()
                .id(fromString("0a2de2db-3997-4082-82b6-56aad9746af9"))
                .description("My file")
                .url(NO_FILE)
                .build();
    }

    public static Attachment otherAttachment() {
        return Attachment.builder()
                .id(fromString("a58c72bf-8444-491e-a887-f2a8c6345520"))
                .description("My second file")
                .url(NO_FILE)
                .build();
    }

    public static Attachment attachmentToDelete() {
        return attachment().toBuilder()
                .id(fromString("b192eba5-264b-4c32-9bfe-e72d1f7a244b"))
                .build();
    }

    public static Attachment updatedAttachment() {
        return attachment().toBuilder()
                .description("Updated description")
                .build();
    }

    public static CreateAttachmentRequest createAttachmentRequest() {
        return CreateAttachmentRequest.builder()
                .description(attachment().getDescription())
                .file(new MockMultipartFile("file", attachment().getUrl(), null, "bar".getBytes()))
                .build();
    }

    public static CreateAttachmentRequest createAttachmentRequestForFile(String description, MultipartFile multipartFile) {
        return CreateAttachmentRequest.builder()
                .description(description)
                .file(multipartFile)
                .build();
    }

    public static UpdateAttachmentRequest updateAttachmentRequest() {
        return UpdateAttachmentRequest.builder()
                .description(updatedAttachment().getDescription())
                .build();
    }

    public static List<Attachment> attachments() {
        return singletonList(attachment());
    }

    public static List<Attachment> otherAttachments() {
        return singletonList(otherAttachment());
    }

}
