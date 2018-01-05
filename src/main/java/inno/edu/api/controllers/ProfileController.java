package inno.edu.api.controllers;

import inno.edu.api.controllers.resources.ExperienceResource;
import inno.edu.api.controllers.resources.ProfileAssociationResource;
import inno.edu.api.controllers.resources.ProfileResource;
import inno.edu.api.controllers.resources.ResourceBuilder;
import inno.edu.api.domain.profile.association.commands.AssociateProfileCommand;
import inno.edu.api.domain.profile.experience.commands.CreateExperienceCommand;
import inno.edu.api.domain.profile.experience.commands.DeleteExperienceCommand;
import inno.edu.api.domain.profile.experience.commands.UpdateExperienceCommand;
import inno.edu.api.domain.profile.experience.commands.dtos.CreateExperienceRequest;
import inno.edu.api.domain.profile.experience.commands.dtos.UpdateExperienceRequest;
import inno.edu.api.domain.profile.experience.models.Experience;
import inno.edu.api.domain.profile.experience.queries.GetExperienceByIdQuery;
import inno.edu.api.domain.profile.experience.queries.GetExperiencesByProfileIdQuery;
import inno.edu.api.domain.profile.root.commands.CreateProfileCommand;
import inno.edu.api.domain.profile.root.commands.DeleteProfileCommand;
import inno.edu.api.domain.profile.association.commands.ApproveProfileAssociationCommand;
import inno.edu.api.domain.profile.association.commands.RejectProfileAssociationCommand;
import inno.edu.api.domain.profile.root.commands.UpdateProfileCommand;
import inno.edu.api.domain.profile.root.commands.dtos.ApproveProfileAssociationRequest;
import inno.edu.api.domain.profile.root.commands.dtos.CreateProfileRequest;
import inno.edu.api.domain.profile.root.commands.dtos.ProfileAssociationRequest;
import inno.edu.api.domain.profile.root.commands.dtos.RejectProfileAssociationRequest;
import inno.edu.api.domain.profile.root.commands.dtos.UpdateProfileRequest;
import inno.edu.api.domain.profile.root.models.Profile;
import inno.edu.api.domain.profile.association.models.ProfileAssociation;
import inno.edu.api.domain.profile.association.models.RequestStatus;
import inno.edu.api.domain.profile.association.queries.GetAssociationsByProfileIdQuery;
import inno.edu.api.domain.profile.root.queries.GetProfileByIdQuery;
import inno.edu.api.domain.profile.root.repositories.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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
public class ProfileController {

    private final ResourceBuilder resourceBuilder;
    private final ProfileRepository profileRepository;

    private final GetProfileByIdQuery getProfileByIdQuery;
    private final GetExperienceByIdQuery getExperienceByIdQuery;
    private final GetAssociationsByProfileIdQuery getAssociationsByProfileIdQuery;
    private final GetExperiencesByProfileIdQuery getExperiencesByProfileIdQuery;

    private final UpdateProfileCommand updateProfileCommand;
    private final CreateProfileCommand createProfileCommand;
    private final DeleteProfileCommand deleteProfileCommand;

    private final AssociateProfileCommand associateProfileCommand;
    private final ApproveProfileAssociationCommand approveProfileAssociationCommand;
    private final RejectProfileAssociationCommand rejectProfileAssociationCommand;

    private final CreateExperienceCommand createExperienceCommand;
    private final UpdateExperienceCommand updateExperienceCommand;
    private final DeleteExperienceCommand deleteExperienceCommand;

    @Autowired
    public ProfileController(ProfileRepository profileRepository, ResourceBuilder resourceBuilder, GetProfileByIdQuery getProfileByIdQuery, GetExperienceByIdQuery getExperienceByIdQuery, GetAssociationsByProfileIdQuery getAssociationsByProfileIdQuery, GetExperiencesByProfileIdQuery getExperiencesByProfileIdQuery, UpdateProfileCommand updateProfileCommand, CreateProfileCommand createProfileCommand, DeleteProfileCommand deleteProfileCommand, AssociateProfileCommand associateProfileCommand, ApproveProfileAssociationCommand approveProfileAssociationCommand, RejectProfileAssociationCommand rejectProfileAssociationCommand, CreateExperienceCommand createExperienceCommand, UpdateExperienceCommand updateExperienceCommand, DeleteExperienceCommand deleteExperienceCommand) {
        this.profileRepository = profileRepository;
        this.resourceBuilder = resourceBuilder;
        this.getProfileByIdQuery = getProfileByIdQuery;
        this.getExperienceByIdQuery = getExperienceByIdQuery;
        this.getAssociationsByProfileIdQuery = getAssociationsByProfileIdQuery;
        this.getExperiencesByProfileIdQuery = getExperiencesByProfileIdQuery;
        this.updateProfileCommand = updateProfileCommand;
        this.createProfileCommand = createProfileCommand;
        this.deleteProfileCommand = deleteProfileCommand;
        this.associateProfileCommand = associateProfileCommand;
        this.approveProfileAssociationCommand = approveProfileAssociationCommand;
        this.rejectProfileAssociationCommand = rejectProfileAssociationCommand;
        this.createExperienceCommand = createExperienceCommand;
        this.updateExperienceCommand = updateExperienceCommand;
        this.deleteExperienceCommand = deleteExperienceCommand;
    }

    @GetMapping
    public Resources<Object> all() {
        Iterable<Profile> profiles = profileRepository.findAll();
        return resourceBuilder.wrappedFrom(profiles, ProfileResource::new, ProfileResource.class);
    }

    @GetMapping("/{id}")
    public ProfileResource get(@PathVariable UUID id) {
        return new ProfileResource(getProfileByIdQuery.run(id));
    }

    @PostMapping
    public ResponseEntity<Profile> post(@Valid @RequestBody CreateProfileRequest request) {
        ProfileResource profileResource = new ProfileResource(createProfileCommand.run(request));
        return profileResource.toCreated();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Profile> put(@PathVariable UUID id, @Valid @RequestBody UpdateProfileRequest request) {
        ProfileResource profileResource = new ProfileResource(updateProfileCommand.run(id, request));
        return profileResource.toUpdated();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        deleteProfileCommand.run(id);
        return noContent().build();
    }

    @PostMapping("/{id}/associate")
    public ResponseEntity<?> associate(@PathVariable UUID id, @Valid @RequestBody ProfileAssociationRequest request) {
        associateProfileCommand.run(id, request);
        return noContent().build();
    }

    @GetMapping("/{id}/associations")
    public Resources<Object> associations(@PathVariable UUID id, @RequestParam(required = false) RequestStatus status) {
        List<ProfileAssociation> profileAssociations = getAssociationsByProfileIdQuery.run(id, status);
        return resourceBuilder.wrappedFrom(profileAssociations, ProfileAssociationResource::new, ProfileAssociationResource.class);
    }

    @PutMapping("/associations/{id}/approve")
    public ResponseEntity<?> approveAssociation(@PathVariable UUID id, @Valid @RequestBody ApproveProfileAssociationRequest request) {
        approveProfileAssociationCommand.run(id, request);
        return noContent().build();
    }

    @PutMapping("/associations/{id}/reject")
    public ResponseEntity<?> rejectAssociation(@PathVariable UUID id, @Valid @RequestBody RejectProfileAssociationRequest request) {
        rejectProfileAssociationCommand.run(id, request);
        return noContent().build();
    }

    @GetMapping("/{id}/experiences")
    public Resources<Object> experiences(@PathVariable UUID id) {
        Iterable<Experience> experiences = getExperiencesByProfileIdQuery.run(id);
        return resourceBuilder.wrappedFrom(experiences, ExperienceResource::new, ExperienceResource.class);
    }

    @GetMapping("/experiences/{id}")
    public ExperienceResource getExperience(@PathVariable UUID id) {
        return new ExperienceResource(getExperienceByIdQuery.run(id));
    }

    @PostMapping("/{id}/experiences")
    public ResponseEntity<Experience> postExperience(@PathVariable UUID id, @Valid @RequestBody CreateExperienceRequest request) {
        ExperienceResource experienceResource = new ExperienceResource(createExperienceCommand.run(id, request));
        return experienceResource.toCreated();
    }

    @PutMapping("/experiences/{id}")
    public ResponseEntity<Experience> putExperience(@PathVariable UUID id, @Valid @RequestBody UpdateExperienceRequest request) {
        ExperienceResource experienceResource = new ExperienceResource(updateExperienceCommand.run(id, request));
        return experienceResource.toUpdated();
    }

    @DeleteMapping("/experiences/{id}")
    public ResponseEntity<?> deleteExperience(@PathVariable UUID id) {
        deleteExperienceCommand.run(id);
        return noContent().build();
    }
}
