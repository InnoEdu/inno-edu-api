package inno.edu.api.support;

import inno.edu.api.domain.attachment.models.Attachment;
import inno.edu.api.domain.attachment.models.dtos.CreateAttachmentRequest;
import inno.edu.api.domain.attachment.models.dtos.UpdateAttachmentRequest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static java.util.Collections.singletonList;
import static java.util.UUID.fromString;

public class AttachmentFactory {
    public static Attachment feiProfilePhoto() {
        return Attachment.builder()
                .id(fromString("0a2de2db-3997-4082-82b6-56aad9746af9"))
                .description("Fei profile photo")
                .url("Fei profile photo url")
                .build();
    }

    public static Attachment feiAttachment() {
        return Attachment.builder()
                .id(fromString("542aba26-c9c6-4693-a426-3fcf11d2fbdf"))
                .description("Fei attachment")
                .url("Fei attachment url")
                .build();
    }

    public static Attachment stanfordAttachment() {
        return Attachment.builder()
                .id(fromString("02122a25-1591-4ced-8c87-9b5c684e36c0"))
                .description("Stanford attachment")
                .url("Stanford attachment url")
                .build();
    }

    public static Attachment alanProfilePhoto() {
        return Attachment.builder()
                .id(fromString("a58c72bf-8444-491e-a887-f2a8c6345520"))
                .description("Alan profile photo")
                .url("Alan profile photo url")
                .build();
    }

    public static Attachment attachmentToDelete() {
        return feiProfilePhoto().toBuilder()
                .id(fromString("b192eba5-264b-4c32-9bfe-e72d1f7a244b"))
                .build();
    }

    public static Attachment updatedAttachment() {
        return feiAttachment().toBuilder()
                .description("Updated description")
                .build();
    }

    public static CreateAttachmentRequest createAttachmentRequest() {
        return CreateAttachmentRequest.builder()
                .description(feiAttachment().getDescription())
                .file(new MockMultipartFile("file", feiAttachment().getUrl(), null, "bar".getBytes()))
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
        return singletonList(feiAttachment());
    }

    public static List<Attachment> otherAttachments() {
        return singletonList(alanProfilePhoto());
    }

}
