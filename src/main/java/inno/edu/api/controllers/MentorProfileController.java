package inno.edu.api.controllers;

import inno.edu.api.controllers.resources.MentorProfileResource;
import inno.edu.api.controllers.resources.ResourceBuilder;
import inno.edu.api.domain.profile.commands.CreateMentorProfileCommand;
import inno.edu.api.domain.profile.commands.DeleteMentorProfileCommand;
import inno.edu.api.domain.profile.commands.UpdateMentorProfileCommand;
import inno.edu.api.domain.profile.commands.UpdateMentorProfileStatusCommand;
import inno.edu.api.domain.profile.commands.dtos.CreateMentorProfileRequest;
import inno.edu.api.domain.profile.commands.dtos.UpdateMentorProfileRequest;
import inno.edu.api.domain.profile.models.MentorProfile;
import inno.edu.api.domain.profile.models.ProfileStatus;
import inno.edu.api.domain.profile.queries.GetMentorProfileByIdQuery;
import inno.edu.api.domain.profile.repositories.MentorProfileRepository;
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
@RequestMapping(value = "/api/mentor-profiles", produces = "application/hal+json")
public class MentorProfileController {

    private final ResourceBuilder resourceBuilder;
    private final MentorProfileRepository mentorProfileRepository;

    private final GetMentorProfileByIdQuery getMentorProfileByIdQuery;

    private final UpdateMentorProfileCommand updateMentorProfileCommand;
    private final CreateMentorProfileCommand createMentorProfileCommand;
    private final DeleteMentorProfileCommand deleteMentorProfileCommand;

    private final UpdateMentorProfileStatusCommand updateMentorProfileStatusCommand;

    @Autowired
    public MentorProfileController(MentorProfileRepository mentorProfileRepository, ResourceBuilder resourceBuilder, GetMentorProfileByIdQuery getMentorProfileByIdQuery, UpdateMentorProfileCommand updateMentorProfileCommand, CreateMentorProfileCommand createMentorProfileCommand, DeleteMentorProfileCommand deleteMentorProfileCommand, UpdateMentorProfileStatusCommand updateMentorProfileStatusCommand) {
        this.mentorProfileRepository = mentorProfileRepository;
        this.resourceBuilder = resourceBuilder;
        this.getMentorProfileByIdQuery = getMentorProfileByIdQuery;
        this.updateMentorProfileCommand = updateMentorProfileCommand;
        this.createMentorProfileCommand = createMentorProfileCommand;
        this.deleteMentorProfileCommand = deleteMentorProfileCommand;
        this.updateMentorProfileStatusCommand = updateMentorProfileStatusCommand;
    }

    @GetMapping
    public Resources<Object> all() {
        Iterable<MentorProfile> profiles = mentorProfileRepository.findAll();
        return resourceBuilder.wrappedFrom(profiles, MentorProfileResource::new, MentorProfileResource.class);
    }

    @GetMapping("/{id}")
    public MentorProfileResource get(@PathVariable UUID id) {
        return new MentorProfileResource(getMentorProfileByIdQuery.run(id));
    }

    @PostMapping
    public ResponseEntity<MentorProfile> post(@Valid @RequestBody CreateMentorProfileRequest request) {
        MentorProfileResource mentorProfileResource = new MentorProfileResource(createMentorProfileCommand.run(request));
        return mentorProfileResource.toCreated();
    }

    @PutMapping("/{id}")
    public ResponseEntity<MentorProfile> put(@PathVariable UUID id, @Valid @RequestBody UpdateMentorProfileRequest request) {
        MentorProfileResource profileResource = new MentorProfileResource(updateMentorProfileCommand.run(id, request));
        return profileResource.toUpdated();
    }

    @PutMapping("/{id}/approve")
    public ResponseEntity<?> approve(@PathVariable UUID id) {
        updateMentorProfileStatusCommand.run(id, ProfileStatus.ACTIVE);
        return noContent().build();
    }

    @PutMapping("/{id}/reject")
    public ResponseEntity<?> reject(@PathVariable UUID id) {
        updateMentorProfileStatusCommand.run(id, ProfileStatus.REJECTED);
        return noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        deleteMentorProfileCommand.run(id);
        return noContent().build();
    }
}
