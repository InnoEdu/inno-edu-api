package inno.edu.api.controllers;

import inno.edu.api.controllers.resources.ProfileResource;
import inno.edu.api.controllers.resources.ResourceBuilder;
import inno.edu.api.domain.profile.models.Profile;
import inno.edu.api.domain.profile.queries.GetProfileByIdQuery;
import inno.edu.api.domain.profile.repositories.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping(value = "/api/profiles", produces = "application/hal+json")
public class ProfileController {

    private final ResourceBuilder resourceBuilder;
    private final ProfileRepository profileRepository;

    private final GetProfileByIdQuery getProfileByIdQuery;

    @Autowired
    public ProfileController(ProfileRepository profileRepository, ResourceBuilder resourceBuilder, GetProfileByIdQuery getProfileByIdQuery) {
        this.profileRepository = profileRepository;
        this.resourceBuilder = resourceBuilder;
        this.getProfileByIdQuery = getProfileByIdQuery;
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
}
