package inno.edu.api.domain.profile.models;

import javax.persistence.Id;
import java.util.UUID;

public class AssociationRequest {
    @Id
    private UUID id;

    private UUID profileId;
    private UUID schoolId;
    private RequestStatus status;
    private String description;
}
