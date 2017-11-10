package inno.edu.api.domain.availability.exceptions;

import java.util.UUID;

public class AvailabilityNotFoundException extends RuntimeException {
    public AvailabilityNotFoundException(UUID availabilityId) {
        super("Could not find availability '" + availabilityId.toString() + "'.");
    }
}