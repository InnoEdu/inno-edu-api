package inno.edu.api.controllers.profile;

import inno.edu.api.presentation.resources.profile.ProfileAssociationResource;
import inno.edu.api.presentation.resources.ResourceBuilder;
import inno.edu.api.domain.profile.association.commands.ApproveProfileAssociationCommand;
import inno.edu.api.domain.profile.association.commands.AssociateProfileCommand;
import inno.edu.api.domain.profile.association.commands.RejectProfileAssociationCommand;
import inno.edu.api.domain.profile.association.models.ProfileAssociation;
import inno.edu.api.domain.profile.association.models.RequestStatus;
import inno.edu.api.domain.profile.association.queries.GetAssociationsByProfileIdQuery;
import inno.edu.api.domain.profile.root.commands.dtos.ApproveProfileAssociationRequest;
import inno.edu.api.domain.profile.root.commands.dtos.ProfileAssociationRequest;
import inno.edu.api.domain.profile.root.commands.dtos.RejectProfileAssociationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

import static org.springframework.http.ResponseEntity.noContent;

@RestController
@RequestMapping(value = "/api/profiles", produces = "application/hal+json")
public class ProfileAssociationController {

    private final ResourceBuilder resourceBuilder;

    private final GetAssociationsByProfileIdQuery getAssociationsByProfileIdQuery;
    private final AssociateProfileCommand associateProfileCommand;
    private final ApproveProfileAssociationCommand approveProfileAssociationCommand;
    private final RejectProfileAssociationCommand rejectProfileAssociationCommand;

    @Autowired
    public ProfileAssociationController(ResourceBuilder resourceBuilder, GetAssociationsByProfileIdQuery getAssociationsByProfileIdQuery, AssociateProfileCommand associateProfileCommand, ApproveProfileAssociationCommand approveProfileAssociationCommand, RejectProfileAssociationCommand rejectProfileAssociationCommand) {
        this.resourceBuilder = resourceBuilder;
        this.getAssociationsByProfileIdQuery = getAssociationsByProfileIdQuery;
        this.associateProfileCommand = associateProfileCommand;
        this.approveProfileAssociationCommand = approveProfileAssociationCommand;
        this.rejectProfileAssociationCommand = rejectProfileAssociationCommand;
    }

    @PostMapping("/{id}/associate")
    public ResponseEntity<?> associate(@PathVariable UUID id, @Valid @RequestBody ProfileAssociationRequest request) {
        associateProfileCommand.run(id, request);
        return noContent().build();
    }

    @GetMapping("/{id}/associations")
    public Resources<Object> all(@PathVariable UUID id, @RequestParam(required = false) RequestStatus status) {
        List<ProfileAssociation> profileAssociations = getAssociationsByProfileIdQuery.run(id, status);
        return resourceBuilder.wrappedFrom(profileAssociations, ProfileAssociationResource::new, ProfileAssociationResource.class);
    }

    @PutMapping("/associations/{id}/approve")
    public ResponseEntity<?> approve(@PathVariable UUID id, @Valid @RequestBody ApproveProfileAssociationRequest request) {
        approveProfileAssociationCommand.run(id, request);
        return noContent().build();
    }

    @PutMapping("/associations/{id}/reject")
    public ResponseEntity<?> reject(@PathVariable UUID id, @Valid @RequestBody RejectProfileAssociationRequest request) {
        rejectProfileAssociationCommand.run(id, request);
        return noContent().build();
    }
}
