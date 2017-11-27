package inno.edu.api.controllers;

import inno.edu.api.controllers.resources.AvailabilityResource;
import inno.edu.api.controllers.resources.ResourceBuilder;
import inno.edu.api.domain.availability.commands.CreateAvailabilityCommand;
import inno.edu.api.domain.availability.commands.DeleteAvailabilityCommand;
import inno.edu.api.domain.availability.commands.UpdateAvailabilityCommand;
import inno.edu.api.domain.availability.commands.dtos.CreateAvailabilityRequest;
import inno.edu.api.domain.availability.commands.dtos.UpdateAvailabilityRequest;
import inno.edu.api.domain.availability.models.Availability;
import inno.edu.api.domain.availability.queries.GetAvailabilityByIdQuery;
import inno.edu.api.domain.availability.repositories.AvailabilityRepository;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ResponseHeader;
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
@RequestMapping(value = "/api/availability", produces = "application/hal+json")
public class AvailabilityController {
    private final AvailabilityRepository availabilityRepository;

    private final ResourceBuilder resourceBuilder;

    private final CreateAvailabilityCommand createAvailabilityCommand;
    private final UpdateAvailabilityCommand updateAvailabilityCommand;
    private final DeleteAvailabilityCommand deleteAvailabilityCommand;

    private final GetAvailabilityByIdQuery getAvailabilityByIdQuery;

    public AvailabilityController(AvailabilityRepository availabilityRepository, ResourceBuilder resourceBuilder, CreateAvailabilityCommand createAvailabilityCommand, UpdateAvailabilityCommand updateAvailabilityCommand, DeleteAvailabilityCommand deleteAvailabilityCommand, GetAvailabilityByIdQuery getAvailabilityByIdQuery) {
        this.availabilityRepository = availabilityRepository;
        this.resourceBuilder = resourceBuilder;
        this.createAvailabilityCommand = createAvailabilityCommand;
        this.updateAvailabilityCommand = updateAvailabilityCommand;
        this.deleteAvailabilityCommand = deleteAvailabilityCommand;
        this.getAvailabilityByIdQuery = getAvailabilityByIdQuery;
    }

    @GetMapping
    @ApiOperation(value = "Find all availability", notes = "Return all availability.", response = Availability.class, responseContainer = "List")
    public Resources<AvailabilityResource> all() {
        Iterable<Availability> availability = availabilityRepository.findAll();
        return resourceBuilder.from(availability, AvailabilityResource::new);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get an availability", notes = "Get an availability by ID.", response = Availability.class)
    @ApiResponses({
            @ApiResponse(code = 404, message = "Availability not found."),
    })
    public AvailabilityResource get(@PathVariable UUID id) {
        return new AvailabilityResource(getAvailabilityByIdQuery.run(id));
    }

    @PostMapping
    @ApiOperation(value = "Create a new availability", notes = "Creates a new availability.")
    @ApiResponses({
            @ApiResponse(code = 201, message = "New availability successfully created.", responseHeaders = @ResponseHeader(name = "Location", description = "Link to the new resource created.", response = String.class)),
            @ApiResponse(code = 404, message = "Invalid mentor profile ID supplied."),
    })
    public ResponseEntity<Availability> post(@Valid @RequestBody CreateAvailabilityRequest createAvailabilityRequest) {
        AvailabilityResource availabilityResource = new AvailabilityResource(createAvailabilityCommand.run(createAvailabilityRequest));
        return availabilityResource.toCreated();
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update an availability", notes = "Update an availability.", response = Availability.class)
    @ApiResponses({
            @ApiResponse(code = 201, message = "New availability successfully updated.", responseHeaders = @ResponseHeader(name = "Location", description = "Link to the updated resource.", response = String.class)),
            @ApiResponse(code = 404, message = "Availability not found."),
    })
    public ResponseEntity<Availability> put(@PathVariable UUID id, @Valid @RequestBody UpdateAvailabilityRequest updateAvailabilityRequest) {
        AvailabilityResource availabilityResource = new AvailabilityResource(updateAvailabilityCommand.run(id, updateAvailabilityRequest));
        return availabilityResource.toUpdated();
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete an availability", notes = "Delete an availability, this operation cannot be undone.")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Availability successfully deleted."),
            @ApiResponse(code = 404, message = "Availability not found.")
    })
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        deleteAvailabilityCommand.run(id);
        return noContent().build();
    }
}
