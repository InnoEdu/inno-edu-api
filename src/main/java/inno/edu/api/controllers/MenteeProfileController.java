package inno.edu.api.controllers;

import inno.edu.api.controllers.resources.MenteeProfileResource;
import inno.edu.api.controllers.resources.ResourceBuilder;
import inno.edu.api.domain.profile.commands.CreateMenteeProfileCommand;
import inno.edu.api.domain.profile.commands.DeleteMenteeProfileCommand;
import inno.edu.api.domain.profile.commands.UpdateMenteeProfileCommand;
import inno.edu.api.domain.profile.models.MenteeProfile;
import inno.edu.api.domain.profile.queries.GetMenteeProfileByIdQuery;
import inno.edu.api.domain.profile.repositories.MenteeProfileRepository;
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

import java.util.UUID;

import static org.springframework.http.ResponseEntity.noContent;

@RestController
@RequestMapping(value = "/api/mentee-profiles", produces = "application/hal+json")
public class MenteeProfileController {

    private final ResourceBuilder resourceBuilder;
    private final MenteeProfileRepository menteeProfileRepository;

    private final GetMenteeProfileByIdQuery getMenteeProfileByIdQuery;

    private final UpdateMenteeProfileCommand updateMenteeProfileCommand;
    private final CreateMenteeProfileCommand createMenteeProfileCommand;
    private final DeleteMenteeProfileCommand deleteMenteeProfileCommand;

    @Autowired
    public MenteeProfileController(MenteeProfileRepository menteeProfileRepository, ResourceBuilder resourceBuilder, GetMenteeProfileByIdQuery getMenteeProfileByIdQuery, UpdateMenteeProfileCommand updateMenteeProfileCommand, CreateMenteeProfileCommand createMenteeProfileCommand, DeleteMenteeProfileCommand deleteMenteeProfileCommand) {
        this.menteeProfileRepository = menteeProfileRepository;
        this.resourceBuilder = resourceBuilder;
        this.getMenteeProfileByIdQuery = getMenteeProfileByIdQuery;
        this.updateMenteeProfileCommand = updateMenteeProfileCommand;
        this.createMenteeProfileCommand = createMenteeProfileCommand;
        this.deleteMenteeProfileCommand = deleteMenteeProfileCommand;
    }

    @GetMapping
    public Resources<MenteeProfileResource> all() {
        Iterable<MenteeProfile> profiles = menteeProfileRepository.findAll();
        return resourceBuilder.from(profiles, MenteeProfileResource::new);
    }

    @GetMapping("/{id}")
    public MenteeProfileResource get(@PathVariable UUID id) {
        return new MenteeProfileResource(getMenteeProfileByIdQuery.run(id));
    }

    @PostMapping
    public ResponseEntity<?> post(@RequestBody MenteeProfile profile) {
        MenteeProfileResource userResource = new MenteeProfileResource(createMenteeProfileCommand.run(profile));
        return userResource.toCreated();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> put(@PathVariable UUID id, @RequestBody MenteeProfile profile) {
        MenteeProfileResource profileResource = new MenteeProfileResource(updateMenteeProfileCommand.run(id, profile));
        return profileResource.toUpdated();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        deleteMenteeProfileCommand.run(id);

        return noContent().build();
    }
}
