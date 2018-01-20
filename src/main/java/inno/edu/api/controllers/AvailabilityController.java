package inno.edu.api.controllers;

import inno.edu.api.domain.availability.models.resources.AvailabilityResource;
import inno.edu.api.presentation.resources.ResourceBuilder;
import inno.edu.api.domain.availability.commands.CreateAvailabilityCommand;
import inno.edu.api.domain.availability.commands.DeleteAvailabilityCommand;
import inno.edu.api.domain.availability.commands.UpdateAvailabilityCommand;
import inno.edu.api.domain.availability.models.dtos.CreateAvailabilityRequest;
import inno.edu.api.domain.availability.models.dtos.UpdateAvailabilityRequest;
import inno.edu.api.domain.availability.models.Availability;
import inno.edu.api.domain.availability.queries.GetAvailabilityByIdQuery;
import inno.edu.api.domain.availability.queries.GetAvailabilityByProfileId;
import inno.edu.api.domain.availability.queries.GetAvailabilityQuery;
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
import java.util.List;
import java.util.UUID;

import static org.springframework.http.ResponseEntity.noContent;

@RestController
@RequestMapping(value = "/api/availability", produces = "application/hal+json")
public class AvailabilityController {
    private final ResourceBuilder resourceBuilder;

    private final CreateAvailabilityCommand createAvailabilityCommand;
    private final UpdateAvailabilityCommand updateAvailabilityCommand;
    private final DeleteAvailabilityCommand deleteAvailabilityCommand;

    private final GetAvailabilityQuery getAvailabilityQuery;
    private final GetAvailabilityByIdQuery getAvailabilityByIdQuery;
    private final GetAvailabilityByProfileId getAvailabilityByProfileIdQuery;

    public AvailabilityController(GetAvailabilityQuery getAvailabilityQuery, ResourceBuilder resourceBuilder, CreateAvailabilityCommand createAvailabilityCommand, UpdateAvailabilityCommand updateAvailabilityCommand, DeleteAvailabilityCommand deleteAvailabilityCommand, GetAvailabilityQuery getAvailabilityQuery1, GetAvailabilityByIdQuery getAvailabilityByIdQuery, GetAvailabilityByProfileId getAvailabilityByProfileIdQuery) {
        this.resourceBuilder = resourceBuilder;
        this.createAvailabilityCommand = createAvailabilityCommand;
        this.updateAvailabilityCommand = updateAvailabilityCommand;
        this.deleteAvailabilityCommand = deleteAvailabilityCommand;
        this.getAvailabilityQuery = getAvailabilityQuery1;
        this.getAvailabilityByIdQuery = getAvailabilityByIdQuery;
        this.getAvailabilityByProfileIdQuery = getAvailabilityByProfileIdQuery;
    }

    @GetMapping
    public Resources<Object> all() {
        List<Availability> availability = getAvailabilityQuery.run();
        return resourceBuilder.wrappedFrom(availability, AvailabilityResource::new, AvailabilityResource.class);
    }

    @GetMapping("/{id}")
    public AvailabilityResource get(@PathVariable UUID id) {
        return new AvailabilityResource(getAvailabilityByIdQuery.run(id));
    }

    @PostMapping
    public ResponseEntity<Availability> post(@Valid @RequestBody CreateAvailabilityRequest request) {
        AvailabilityResource availabilityResource = new AvailabilityResource(createAvailabilityCommand.run(request));
        return availabilityResource.toCreated();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Availability> put(@PathVariable UUID id, @Valid @RequestBody UpdateAvailabilityRequest request) {
        AvailabilityResource availabilityResource = new AvailabilityResource(updateAvailabilityCommand.run(id, request));
        return availabilityResource.toUpdated();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        deleteAvailabilityCommand.run(id);
        return noContent().build();
    }

    @GetMapping("/profile/{profileId}")
    public Resources<Object> allByProfile(@PathVariable UUID profileId) {
        List<Availability> availability = getAvailabilityByProfileIdQuery.run(profileId);
        return resourceBuilder.wrappedFrom(availability, AvailabilityResource::new, AvailabilityResource.class);
    }
}
