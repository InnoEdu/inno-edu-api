package inno.edu.api.controllers;

import inno.edu.api.controllers.resources.MentorProfileResource;
import inno.edu.api.controllers.resources.ResourceBuilder;
import inno.edu.api.domain.user.commands.CreateMentorProfileCommand;
import inno.edu.api.domain.user.commands.UpdateMentorProfileCommand;
import inno.edu.api.domain.user.exceptions.ProfileNotFoundException;
import inno.edu.api.domain.user.models.MentorProfile;
import inno.edu.api.domain.user.repositories.MentorProfileRepository;
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

import java.util.Optional;
import java.util.UUID;

import static java.util.Optional.ofNullable;
import static org.springframework.http.ResponseEntity.noContent;

@RestController
@RequestMapping(value = "/api/mentor-profiles", produces = "application/hal+json")
public class MentorProfileController {

    private final ResourceBuilder resourceBuilder;
    private final MentorProfileRepository mentorProfileRepository;

    private UpdateMentorProfileCommand updateMentorProfileCommand;
    private CreateMentorProfileCommand createMentorProfileCommand;

    @Autowired
    public MentorProfileController(MentorProfileRepository mentorProfileRepository, ResourceBuilder resourceBuilder, UpdateMentorProfileCommand updateMentorProfileCommand, CreateMentorProfileCommand createMentorProfileCommand) {
        this.mentorProfileRepository = mentorProfileRepository;
        this.resourceBuilder = resourceBuilder;
        this.updateMentorProfileCommand = updateMentorProfileCommand;
        this.createMentorProfileCommand = createMentorProfileCommand;
    }

    @GetMapping
    public Resources<MentorProfileResource> all() {
        Iterable<MentorProfile> profiles = mentorProfileRepository.findAll();
        return resourceBuilder.from(profiles, MentorProfileResource::new);
    }

    @GetMapping("/{id}")
    public MentorProfileResource get(@PathVariable UUID id) {
        Optional<MentorProfile> profile = ofNullable(mentorProfileRepository.findOne(id));
        return new MentorProfileResource(profile.orElseThrow(() -> new ProfileNotFoundException(id)));
    }

    @PostMapping
    public ResponseEntity<?> post(@RequestBody MentorProfile profile) {
        MentorProfileResource userResource = new MentorProfileResource(createMentorProfileCommand.run(profile));
        return userResource.toCreated();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> put(@PathVariable UUID id, @RequestBody MentorProfile profile) {
        MentorProfileResource profileResource = new MentorProfileResource(updateMentorProfileCommand.run(id, profile));
        return profileResource.toUpdated();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        if (!mentorProfileRepository.exists(id)) {
            throw new ProfileNotFoundException(id);
        }
        mentorProfileRepository.delete(id);

        return noContent().build();
    }
}
