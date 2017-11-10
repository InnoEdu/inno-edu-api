package inno.edu.api.controllers;

import inno.edu.api.controllers.resources.AvailabilityResource;
import inno.edu.api.controllers.resources.ResourceBuilder;
import inno.edu.api.domain.availability.exceptions.AvailabilityNotFoundException;
import inno.edu.api.domain.availability.models.Availability;
import inno.edu.api.domain.availability.repositories.AvailabilityRepository;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.google.common.collect.Streams.stream;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping(value = "/api/availability", produces = "application/hal+json")
public class AvailabilityController {
    private final AvailabilityRepository availabilityRepository;

    private final ResourceBuilder resourceBuilder;

    public AvailabilityController(AvailabilityRepository availabilityRepository, ResourceBuilder resourceBuilder) {
        this.availabilityRepository = availabilityRepository;
        this.resourceBuilder = resourceBuilder;
    }

    @GetMapping
    public Resources<AvailabilityResource> all() {
        List<AvailabilityResource> availability = stream(availabilityRepository.findAll())
                .map(AvailabilityResource::new)
                .collect(toList());

        return resourceBuilder.fromResources(availability);
    }

    @GetMapping("/{id}")
    public AvailabilityResource get(@PathVariable UUID id) {
        Optional<Availability> availability = ofNullable(availabilityRepository.findOne(id));
        return new AvailabilityResource(availability.orElseThrow(() -> new AvailabilityNotFoundException(id)));
    }
}
