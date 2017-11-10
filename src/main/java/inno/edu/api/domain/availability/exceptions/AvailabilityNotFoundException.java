package inno.edu.api.domain.availability.exceptions;

import java.util.UUID;

public class AvailabilityNotFoundException extends RuntimeException {
    public AvailabilityNotFoundException(UUID userId) {
        super("Could not find availability '" + userId.toString() + "'.");
    }
}