package inno.edu.api.controllers.profile;

import inno.edu.api.domain.profile.accomplishment.models.resources.AccomplishmentResource;
import inno.edu.api.presentation.resources.ResourceBuilder;
import inno.edu.api.domain.profile.accomplishment.commands.CreateAccomplishmentCommand;
import inno.edu.api.domain.profile.accomplishment.commands.DeleteAccomplishmentCommand;
import inno.edu.api.domain.profile.accomplishment.commands.UpdateAccomplishmentCommand;
import inno.edu.api.domain.profile.accomplishment.models.dtos.CreateAccomplishmentRequest;
import inno.edu.api.domain.profile.accomplishment.models.dtos.UpdateAccomplishmentRequest;
import inno.edu.api.domain.profile.accomplishment.models.Accomplishment;
import inno.edu.api.domain.profile.accomplishment.queries.GetAccomplishmentByIdQuery;
import inno.edu.api.domain.profile.accomplishment.queries.GetAccomplishmentsByProfileIdQuery;
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
public class AccomplishmentController {
    private final ResourceBuilder resourceBuilder;

    private final GetAccomplishmentByIdQuery getAccomplishmentByIdQuery;
    private final GetAccomplishmentsByProfileIdQuery getAccomplishmentsByProfileIdQuery;

    private final CreateAccomplishmentCommand createAccomplishmentCommand;
    private final UpdateAccomplishmentCommand updateAccomplishmentCommand;
    private final DeleteAccomplishmentCommand deleteAccomplishmentCommand;

    @Autowired
    public AccomplishmentController(ResourceBuilder resourceBuilder, GetAccomplishmentByIdQuery getAccomplishmentByIdQuery, GetAccomplishmentsByProfileIdQuery getAccomplishmentsByProfileIdQuery, CreateAccomplishmentCommand createAccomplishmentCommand, UpdateAccomplishmentCommand updateAccomplishmentCommand, DeleteAccomplishmentCommand deleteAccomplishmentCommand) {
        this.resourceBuilder = resourceBuilder;
        this.getAccomplishmentByIdQuery = getAccomplishmentByIdQuery;
        this.getAccomplishmentsByProfileIdQuery = getAccomplishmentsByProfileIdQuery;
        this.createAccomplishmentCommand = createAccomplishmentCommand;
        this.updateAccomplishmentCommand = updateAccomplishmentCommand;
        this.deleteAccomplishmentCommand = deleteAccomplishmentCommand;
    }

    @GetMapping("/{id}/accomplishments")
    public Resources<Object> all(@PathVariable UUID id) {
        Iterable<Accomplishment> accomplishments = getAccomplishmentsByProfileIdQuery.run(id);
        return resourceBuilder.wrappedFrom(accomplishments, AccomplishmentResource::new, AccomplishmentResource.class);
    }

    @GetMapping("/accomplishments/{id}")
    public AccomplishmentResource get(@PathVariable UUID id) {
        return new AccomplishmentResource(getAccomplishmentByIdQuery.run(id));
    }

    @PostMapping("/{id}/accomplishments")
    public ResponseEntity<Accomplishment> post(@PathVariable UUID id, @Valid @RequestBody CreateAccomplishmentRequest request) {
        AccomplishmentResource accomplishmentResource = new AccomplishmentResource(createAccomplishmentCommand.run(id, request));
        return accomplishmentResource.toCreated();
    }

    @PutMapping("/accomplishments/{id}")
    public ResponseEntity<Accomplishment> put(@PathVariable UUID id, @Valid @RequestBody UpdateAccomplishmentRequest request) {
        AccomplishmentResource accomplishmentResource = new AccomplishmentResource(updateAccomplishmentCommand.run(id, request));
        return accomplishmentResource.toUpdated();
    }

    @DeleteMapping("/accomplishments/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        deleteAccomplishmentCommand.run(id);
        return noContent().build();
    }
}
