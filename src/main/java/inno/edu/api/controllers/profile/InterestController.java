package inno.edu.api.controllers.profile;

import inno.edu.api.controllers.profile.resources.InterestResource;
import inno.edu.api.controllers.resources.ResourceBuilder;
import inno.edu.api.domain.profile.interest.commands.CreateInterestCommand;
import inno.edu.api.domain.profile.interest.commands.DeleteInterestCommand;
import inno.edu.api.domain.profile.interest.commands.UpdateInterestCommand;
import inno.edu.api.domain.profile.interest.commands.dtos.CreateInterestRequest;
import inno.edu.api.domain.profile.interest.commands.dtos.UpdateInterestRequest;
import inno.edu.api.domain.profile.interest.models.Interest;
import inno.edu.api.domain.profile.interest.queries.GetInterestByIdQuery;
import inno.edu.api.domain.profile.interest.queries.GetInterestsByProfileIdQuery;
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
public class InterestController {
    private final ResourceBuilder resourceBuilder;

    private final GetInterestByIdQuery getInterestByIdQuery;
    private final GetInterestsByProfileIdQuery getInterestsByProfileIdQuery;

    private final CreateInterestCommand createInterestCommand;
    private final UpdateInterestCommand updateInterestCommand;
    private final DeleteInterestCommand deleteInterestCommand;

    @Autowired
    public InterestController(ResourceBuilder resourceBuilder, GetInterestByIdQuery getInterestByIdQuery, GetInterestsByProfileIdQuery getInterestsByProfileIdQuery, CreateInterestCommand createInterestCommand, UpdateInterestCommand updateInterestCommand, DeleteInterestCommand deleteInterestCommand) {
        this.resourceBuilder = resourceBuilder;
        this.getInterestByIdQuery = getInterestByIdQuery;
        this.getInterestsByProfileIdQuery = getInterestsByProfileIdQuery;
        this.createInterestCommand = createInterestCommand;
        this.updateInterestCommand = updateInterestCommand;
        this.deleteInterestCommand = deleteInterestCommand;
    }

    @GetMapping("/{id}/interests")
    public Resources<Object> all(@PathVariable UUID id) {
        Iterable<Interest> interests = getInterestsByProfileIdQuery.run(id);
        return resourceBuilder.wrappedFrom(interests, InterestResource::new, InterestResource.class);
    }

    @GetMapping("/interests/{id}")
    public InterestResource get(@PathVariable UUID id) {
        return new InterestResource(getInterestByIdQuery.run(id));
    }

    @PostMapping("/{id}/interests")
    public ResponseEntity<Interest> post(@PathVariable UUID id, @Valid @RequestBody CreateInterestRequest request) {
        InterestResource interestResource = new InterestResource(createInterestCommand.run(id, request));
        return interestResource.toCreated();
    }

    @PutMapping("/interests/{id}")
    public ResponseEntity<Interest> put(@PathVariable UUID id, @Valid @RequestBody UpdateInterestRequest request) {
        InterestResource interestResource = new InterestResource(updateInterestCommand.run(id, request));
        return interestResource.toUpdated();
    }

    @DeleteMapping("/interests/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        deleteInterestCommand.run(id);
        return noContent().build();
    }
}
