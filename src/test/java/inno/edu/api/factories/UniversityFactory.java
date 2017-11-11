package inno.edu.api.factories;

import inno.edu.api.domain.university.models.University;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static java.lang.String.format;
import static java.util.UUID.fromString;

public class UniversityFactory {
    public static University stanford() {
        return University.builder()
                .id(fromString("0a58153c-c15f-4e5b-802c-bbf5d6c1c55c"))
                .name("Stanford")
                .build();
    }

    public static University berkeley() {
        return University.builder()
                .id(fromString("a10afaca-2016-44b8-940b-5b88323901b9"))
                .name("Berkeley")
                .build();
    }

    public static University updatedStanford() {
        return University.builder()
                .id(fromString("0a58153c-c15f-4e5b-802c-bbf5d6c1c55c"))
                .name("UpdatedBerkeley")
                .build();
    }

    public static List<University> universities() {
        return newArrayList(stanford(), berkeley());
    }

    public static String universityPayload(University university) {
        return format("{\"name\": \"%s\" }", university.getName());
    }
}
