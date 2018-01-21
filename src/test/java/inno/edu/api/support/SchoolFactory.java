package inno.edu.api.support;

import inno.edu.api.domain.school.attachment.models.SchoolAttachment;
import inno.edu.api.domain.school.root.models.dtos.CreateSchoolRequest;
import inno.edu.api.domain.school.root.models.dtos.UpdateSchoolRequest;
import inno.edu.api.domain.school.root.models.School;

import java.util.List;
import java.util.UUID;

import static com.google.common.collect.Lists.newArrayList;
import static inno.edu.api.support.AttachmentFactory.alanProfilePhoto;
import static java.util.Collections.singletonList;
import static java.util.UUID.fromString;

public class SchoolFactory {
    public static School stanford() {
        return School.builder()
                .id(fromString("0a58153c-c15f-4e5b-802c-bbf5d6c1c55c"))
                .name("Stanford")
                .description("Stanford is an amazing university.")
                .build();
    }

    public static School schoolToDelete() {
        return stanford().toBuilder()
                .id(fromString("46ddda8e-e01e-430f-99ed-e8aee71db79a"))
                .build();
    }

    public static School newStanford(UUID id) {
        return stanford().toBuilder()
                .id(id)
                .build();
    }

    public static School berkeley() {
        return School.builder()
                .id(fromString("a10afaca-2016-44b8-940b-5b88323901b9"))
                .name("Berkeley")
                .description("Berkeley is an outstanding university.")
                .build();
    }

    public static CreateSchoolRequest createStanfordRequest() {
        return CreateSchoolRequest.builder()
                .name("Stanford")
                .description("Stanford is an amazing university.")
                .build();
    }

    public static UpdateSchoolRequest updateStanfordRequest() {
        return UpdateSchoolRequest.builder()
                .name("UpdatedStanford")
                .description("Stanford is still an outstanding university.")
                .build();
    }

    public static School updatedStanford() {
        return stanford().toBuilder()
                .name("UpdatedStanford")
                .description("Stanford is still an outstanding university.")
                .build();
    }

    public static List<School> schools() {
        return newArrayList(stanford(), berkeley());
    }

    public static SchoolAttachment stanfordAttachment() {
        return SchoolAttachment.builder()
                .attachmentId(alanProfilePhoto().getId())
                .schoolId(stanford().getId())
                .attachment(alanProfilePhoto())
                .build();
    }

    public static List<SchoolAttachment> stanfordAttachments() {
        return singletonList(stanfordAttachment());
    }
}
