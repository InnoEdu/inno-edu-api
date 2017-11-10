package inno.edu.api.domain.university.exceptions;

import java.util.UUID;

public class UniversityNotFoundException extends RuntimeException {
    public UniversityNotFoundException(UUID universityId) {
        super("Could not find university '" + universityId.toString() + "'.");
    }
}