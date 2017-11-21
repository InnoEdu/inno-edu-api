package inno.edu.api.support;

import inno.edu.api.domain.school.commands.dtos.CreateSchoolRequest;
import inno.edu.api.domain.school.commands.dtos.UpdateSchoolRequest;
import inno.edu.api.domain.school.models.School;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static java.util.UUID.fromString;

public class SchoolFactory {
    public static School stanford() {
        return School.builder()
                .id(fromString("0a58153c-c15f-4e5b-802c-bbf5d6c1c55c"))
                .name("Stanford")
                .description("Stanford is an amazing university.")
                .photoUrl("https://fm.cnbc.com/applications/cnbc.com/resources/img/editorial/2013/07/26/100917787-Stanford_Oval_May_2011_panorama_r.jpg?v=1374868882")
                .build();
    }

    public static School berkeley() {
        return School.builder()
                .id(fromString("a10afaca-2016-44b8-940b-5b88323901b9"))
                .name("Berkeley")
                .description("Berkeley is an outstanding university.")
                .photoUrl("https://upload.wikimedia.org/wikipedia/commons/thumb/7/7f/UCBerkeleyCampus.jpg/220px-UCBerkeleyCampus.jpg")
                .build();
    }

    public static CreateSchoolRequest createStanfordRequest() {
        return CreateSchoolRequest.builder()
                .name("Stanford")
                .description("Stanford is an amazing university.")
                .photoUrl("https://fm.cnbc.com/applications/cnbc.com/resources/img/editorial/2013/07/26/100917787-Stanford_Oval_May_2011_panorama_r.jpg?v=1374868882")
                .build();
    }

    public static UpdateSchoolRequest updateStanfordRequest() {
        return UpdateSchoolRequest.builder()
                .name("UpdatedStanford")
                .description("Stanford is still an outstanding university.")
                .photoUrl("UpdatedPhoto")
                .build();
    }

    public static School updatedStanford() {
        return School.builder()
                .id(fromString("0a58153c-c15f-4e5b-802c-bbf5d6c1c55c"))
                .name("UpdatedStanford")
                .description("Stanford is still an outstanding university.")
                .photoUrl("UpdatedPhoto")
                .build();
    }

    public static List<School> schools() {
        return newArrayList(stanford(), berkeley());
    }
}
