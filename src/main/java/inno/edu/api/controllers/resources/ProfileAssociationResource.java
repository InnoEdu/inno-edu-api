package inno.edu.api.controllers.resources;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import inno.edu.api.domain.profile.association.models.ProfileAssociation;
import lombok.Getter;
import org.springframework.hateoas.ResourceSupport;

@Getter
public class ProfileAssociationResource extends ResourceSupport {
    @JsonUnwrapped
    private final ProfileAssociation profileAssociation;

    public ProfileAssociationResource(ProfileAssociation profileAssociation) {
        this.profileAssociation = profileAssociation;
    }
}
