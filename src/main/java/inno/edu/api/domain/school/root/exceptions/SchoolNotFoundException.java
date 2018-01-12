package inno.edu.api.domain.school.root.exceptions;

import java.util.UUID;

public class SchoolNotFoundException extends RuntimeException {
    public SchoolNotFoundException(UUID school) {
        super("Could not find school '" + school.toString() + "'.");
    }
}