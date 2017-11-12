package inno.edu.api.controllers;

import inno.edu.api.controllers.resources.MenteeProfileResource;
import inno.edu.api.controllers.resources.ResourceBuilder;
import inno.edu.api.domain.user.exceptions.ProfileNotFoundException;
import inno.edu.api.domain.user.models.MenteeProfile;
import inno.edu.api.domain.user.repositories.MenteeProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.UUID;

import static java.util.Optional.ofNullable;

@RestController
@RequestMapping(value = "/api/mentee-profiles", produces = "application/hal+json")
public class MenteeProfileController {

    private final ResourceBuilder resourceBuilder;
    private final MenteeProfileRepository menteeProfileRepository;

    @Autowired
    public MenteeProfileController(MenteeProfileRepository menteeProfileRepository, ResourceBuilder resourceBuilder) {
        this.menteeProfileRepository = menteeProfileRepository;
        this.resourceBuilder = resourceBuilder;
    }

    @GetMapping
    public Resources<MenteeProfileResource> all() {
        Iterable<MenteeProfile> profiles = menteeProfileRepository.findAll();
        return resourceBuilder.from(profiles, MenteeProfileResource::new);
    }

    @GetMapping("/{id}")
    public MenteeProfileResource get(@PathVariable UUID id) {
        Optional<MenteeProfile> profile = ofNullable(menteeProfileRepository.findOne(id));
        return new MenteeProfileResource(profile.orElseThrow(() -> new ProfileNotFoundException(id)));
    }

}
