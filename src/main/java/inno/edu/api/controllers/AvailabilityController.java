package inno.edu.api.controllers;

import inno.edu.api.controllers.resources.AvailabilityResource;
import inno.edu.api.controllers.resources.ResourceBuilder;
import inno.edu.api.domain.availability.commands.CreateAvailabilityByMentorIdCommand;
import inno.edu.api.domain.availability.commands.CreateAvailabilityCommand;
import inno.edu.api.domain.availability.commands.DeleteAvailabilityCommand;
import inno.edu.api.domain.availability.commands.UpdateAvailabilityCommand;
import inno.edu.api.domain.availability.commands.dtos.CreateAvailabilityByMentorIdRequest;
import inno.edu.api.domain.availability.commands.dtos.CreateAvailabilityRequest;
import inno.edu.api.domain.availability.commands.dtos.UpdateAvailabilityRequest;
import inno.edu.api.domain.availability.models.Availability;
import inno.edu.api.domain.availability.queries.GetAvailabilityByIdQuery;
import inno.edu.api.domain.availability.queries.GetAvailabilityByMentorId;
import inno.edu.api.domain.availability.repositories.AvailabilityRepository;
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
    private final AvailabilityRepository availabilityRepository;

    private final ResourceBuilder resourceBuilder;

    private final CreateAvailabilityCommand createAvailabilityCommand;
    private final CreateAvailabilityByMentorIdCommand createAvailabilityByMentorIdCommand;
    private final UpdateAvailabilityCommand updateAvailabilityCommand;
    private final DeleteAvailabilityCommand deleteAvailabilityCommand;

    private final GetAvailabilityByIdQuery getAvailabilityByIdQuery;
    private final GetAvailabilityByMentorId getAvailabilityByMentorIdQuery;

    public AvailabilityController(AvailabilityRepository availabilityRepository, ResourceBuilder resourceBuilder, CreateAvailabilityCommand createAvailabilityCommand, CreateAvailabilityByMentorIdCommand createAvailabilityByMentorIdCommand, UpdateAvailabilityCommand updateAvailabilityCommand, DeleteAvailabilityCommand deleteAvailabilityCommand, GetAvailabilityByIdQuery getAvailabilityByIdQuery, GetAvailabilityByMentorId getAvailabilityByMentorIdQuery) {
        this.availabilityRepository = availabilityRepository;
        this.resourceBuilder = resourceBuilder;
        this.createAvailabilityCommand = createAvailabilityCommand;
        this.createAvailabilityByMentorIdCommand = createAvailabilityByMentorIdCommand;
        this.updateAvailabilityCommand = updateAvailabilityCommand;
        this.deleteAvailabilityCommand = deleteAvailabilityCommand;
        this.getAvailabilityByIdQuery = getAvailabilityByIdQuery;
        this.getAvailabilityByMentorIdQuery = getAvailabilityByMentorIdQuery;
    }

    @GetMapping
    public Resources<Object> all() {
        Iterable<Availability> availability = availabilityRepository.findAll();
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

    @PostMapping("/mentor/{mentorId}")
    public ResponseEntity<Availability> postByMentor(@PathVariable UUID mentorId, @Valid @RequestBody CreateAvailabilityByMentorIdRequest request) {
        AvailabilityResource availabilityResource = new AvailabilityResource(createAvailabilityByMentorIdCommand.run(mentorId, request));
        return availabilityResource.toCreated();
    }

    @GetMapping("/mentor/{mentorId}")
    public Resources<Object> allByMentor(@PathVariable UUID mentorId) {
        List<Availability> availability = getAvailabilityByMentorIdQuery.run(mentorId);
        return resourceBuilder.wrappedFrom(availability, AvailabilityResource::new, AvailabilityResource.class);
    }
}
