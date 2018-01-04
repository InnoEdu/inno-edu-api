package inno.edu.api.controllers;

import inno.edu.api.controllers.resources.ProfileResource;
import inno.edu.api.controllers.resources.ResourceBuilder;
import inno.edu.api.domain.profile.commands.AssociateProfileCommand;
import inno.edu.api.domain.profile.commands.CreateProfileCommand;
import inno.edu.api.domain.profile.commands.DeleteProfileCommand;
import inno.edu.api.domain.profile.commands.UpdateProfileCommand;
import inno.edu.api.domain.profile.commands.dtos.CreateProfileRequest;
import inno.edu.api.domain.profile.commands.dtos.ProfileAssociationRequest;
import inno.edu.api.domain.profile.commands.dtos.UpdateProfileRequest;
import inno.edu.api.domain.profile.models.Profile;
import inno.edu.api.domain.profile.queries.GetProfileByIdQuery;
import inno.edu.api.domain.profile.repositories.ProfileRepository;
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
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.UUID;

import static org.springframework.http.ResponseEntity.noContent;

@RestController
@RequestMapping(value = "/api/profiles", produces = "application/hal+json")
public class ProfileController {

    private final ResourceBuilder resourceBuilder;
    private final ProfileRepository profileRepository;

    private final GetProfileByIdQuery getProfileByIdQuery;

    private final UpdateProfileCommand updateProfileCommand;
    private final CreateProfileCommand createProfileCommand;
    private final DeleteProfileCommand deleteProfileCommand;
    private final AssociateProfileCommand associateProfileCommand;

    @Autowired
    public ProfileController(ProfileRepository profileRepository, ResourceBuilder resourceBuilder, GetProfileByIdQuery getProfileByIdQuery, UpdateProfileCommand updateProfileCommand, CreateProfileCommand createProfileCommand, DeleteProfileCommand deleteProfileCommand, AssociateProfileCommand associateProfileCommand) {
        this.profileRepository = profileRepository;
        this.resourceBuilder = resourceBuilder;
        this.getProfileByIdQuery = getProfileByIdQuery;
        this.updateProfileCommand = updateProfileCommand;
        this.createProfileCommand = createProfileCommand;
        this.deleteProfileCommand = deleteProfileCommand;
        this.associateProfileCommand = associateProfileCommand;
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

    @PutMapping("/{id}/associate")
    public ResponseEntity<?> associate(@PathVariable UUID id, @Valid @RequestBody ProfileAssociationRequest request) {
        associateProfileCommand.run(id, request);
        return noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        deleteProfileCommand.run(id);
        return noContent().build();
    }
}
