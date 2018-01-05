package inno.edu.api.domain.profile.association.exceptions;

import java.util.UUID;

public class ProfileAssociationNotFoundException extends RuntimeException {
    public ProfileAssociationNotFoundException(UUID associationId) {
        super("Could not find association '" + associationId.toString() + "'.");
    }
}