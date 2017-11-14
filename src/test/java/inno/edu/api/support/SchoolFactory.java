package inno.edu.api.support;

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
                .build();
    }

    public static School berkeley() {
        return School.builder()
                .id(fromString("a10afaca-2016-44b8-940b-5b88323901b9"))
                .name("Berkeley")
                .description("Berkeley is an outstanding university.")
                .build();
    }

    public static School updatedStanford() {
        return School.builder()
                .id(fromString("0a58153c-c15f-4e5b-802c-bbf5d6c1c55c"))
                .name("UpdatedBerkeley")
                .name("Berkley is still an outstanding university.")
                .build();
    }

    public static List<School> schools() {
        return newArrayList(stanford(), berkeley());
    }
}
