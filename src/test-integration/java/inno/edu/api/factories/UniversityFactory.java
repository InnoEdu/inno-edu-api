package inno.edu.api.factories;

import inno.edu.api.domain.university.models.University;

import static java.util.UUID.fromString;

public class UniversityFactory {
    public static University university() {
        return University.builder().id(fromString("0a58153c-c15f-4e5b-802c-bbf5d6c1c55c"))
                .name("Name").build();
    }

    public static University otherUniversity() {
        return University.builder().id(fromString("a10afaca-2016-44b8-940b-5b88323901b9"))
                .name("OtherName").build();
    }

    public static University updatedUniversity() {
        return University.builder().id(fromString("0a58153c-c15f-4e5b-802c-bbf5d6c1c55c"))
                .name("NameUpdated").build();
    }

    public static String universityPayload() {
        return "{\"name\": \"%s\" }";
    }

}
