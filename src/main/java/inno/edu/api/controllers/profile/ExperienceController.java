package inno.edu.api.controllers.profile;

import inno.edu.api.controllers.resources.ExperienceResource;
import inno.edu.api.controllers.resources.ResourceBuilder;
import inno.edu.api.domain.profile.experience.commands.CreateExperienceCommand;
import inno.edu.api.domain.profile.experience.commands.DeleteExperienceCommand;
import inno.edu.api.domain.profile.experience.commands.UpdateExperienceCommand;
import inno.edu.api.domain.profile.experience.commands.dtos.CreateExperienceRequest;
import inno.edu.api.domain.profile.experience.commands.dtos.UpdateExperienceRequest;
import inno.edu.api.domain.profile.experience.models.Experience;
import inno.edu.api.domain.profile.experience.queries.GetExperienceByIdQuery;
import inno.edu.api.domain.profile.experience.queries.GetExperiencesByProfileIdQuery;
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
public class ExperienceController {
    private final ResourceBuilder resourceBuilder;

    private final GetExperienceByIdQuery getExperienceByIdQuery;
    private final GetExperiencesByProfileIdQuery getExperiencesByProfileIdQuery;

    private final CreateExperienceCommand createExperienceCommand;
    private final UpdateExperienceCommand updateExperienceCommand;
    private final DeleteExperienceCommand deleteExperienceCommand;

    @Autowired
    public ExperienceController(ResourceBuilder resourceBuilder, GetExperienceByIdQuery getExperienceByIdQuery, GetExperiencesByProfileIdQuery getExperiencesByProfileIdQuery, CreateExperienceCommand createExperienceCommand, UpdateExperienceCommand updateExperienceCommand, DeleteExperienceCommand deleteExperienceCommand) {
        this.resourceBuilder = resourceBuilder;
        this.getExperienceByIdQuery = getExperienceByIdQuery;
        this.getExperiencesByProfileIdQuery = getExperiencesByProfileIdQuery;
        this.createExperienceCommand = createExperienceCommand;
        this.updateExperienceCommand = updateExperienceCommand;
        this.deleteExperienceCommand = deleteExperienceCommand;
    }

    @GetMapping("/{id}/experiences")
    public Resources<Object> all(@PathVariable UUID id) {
        Iterable<Experience> experiences = getExperiencesByProfileIdQuery.run(id);
        return resourceBuilder.wrappedFrom(experiences, ExperienceResource::new, ExperienceResource.class);
    }

    @GetMapping("/experiences/{id}")
    public ExperienceResource get(@PathVariable UUID id) {
        return new ExperienceResource(getExperienceByIdQuery.run(id));
    }

    @PostMapping("/{id}/experiences")
    public ResponseEntity<Experience> post(@PathVariable UUID id, @Valid @RequestBody CreateExperienceRequest request) {
        ExperienceResource experienceResource = new ExperienceResource(createExperienceCommand.run(id, request));
        return experienceResource.toCreated();
    }

    @PutMapping("/experiences/{id}")
    public ResponseEntity<Experience> put(@PathVariable UUID id, @Valid @RequestBody UpdateExperienceRequest request) {
        ExperienceResource experienceResource = new ExperienceResource(updateExperienceCommand.run(id, request));
        return experienceResource.toUpdated();
    }

    @DeleteMapping("/experiences/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        deleteExperienceCommand.run(id);
        return noContent().build();
    }
}
