package inno.edu.api.factories;

import inno.edu.api.controllers.resources.UniversityResource;
import inno.edu.api.domain.university.models.University;
import org.springframework.hateoas.Resources;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
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

    private static UniversityResource stanfordResource() {
        return new UniversityResource(stanford());
    }

    private static UniversityResource berkeleyResource() {
        return new UniversityResource(stanford());
    }

    public static List<University> universities() {
        return newArrayList(stanford(), berkeley());
    }

    public static Resources<UniversityResource> universitiesResource() {
        return new Resources<>(newArrayList(stanfordResource(), berkeleyResource()));
    }

    public static String universityPayload() {
        return "{\"name\": \"%s\" }";
    }
}
