package inno.edu.api.factories;

import inno.edu.api.controllers.resources.UniversityResource;
import inno.edu.api.domain.university.models.University;
import org.springframework.hateoas.Resources;

import java.util.List;

import static java.util.Collections.singletonList;
import static java.util.UUID.fromString;

public class UniversityFactory {
    public static University university() {
        return University.builder().id(fromString("841b43e1-08be-4401-968f-6ee45370a973")).build();
    }

    public static University updatedUniversity() {
        return University.builder().id(fromString("841b43e1-08be-4401-968f-6ee45370a973")).name("UpdatedUniversity").build();
    }

    private static UniversityResource universityResource() {
        return new UniversityResource(university());
    }

    public static List<University> universities() {
        return singletonList(university());
    }

    public static Resources<UniversityResource> universitiesResource() {
        return new Resources<>(singletonList(universityResource()));
    }
}
