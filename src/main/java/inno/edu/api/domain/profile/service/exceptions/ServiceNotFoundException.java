package inno.edu.api.domain.profile.service.exceptions;

import java.util.UUID;

public class ServiceNotFoundException extends RuntimeException {
    public ServiceNotFoundException(UUID profileId) {
        super("Could not find service '" + profileId.toString() + "'.");
    }
}