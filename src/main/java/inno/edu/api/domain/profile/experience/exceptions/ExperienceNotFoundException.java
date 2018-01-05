package inno.edu.api.domain.profile.experience.exceptions;

import java.util.UUID;

public class ExperienceNotFoundException extends RuntimeException {
    public ExperienceNotFoundException(UUID profileId) {
        super("Could not find experience '" + profileId.toString() + "'.");
    }
}