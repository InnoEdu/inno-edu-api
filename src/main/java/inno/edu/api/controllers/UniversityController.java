package inno.edu.api.controllers;

import inno.edu.api.controllers.resources.ResourceBuilder;
import inno.edu.api.controllers.resources.UniversityResource;
import inno.edu.api.domain.university.commands.CreateUniversityCommand;
import inno.edu.api.domain.university.commands.UpdateUniversityCommand;
import inno.edu.api.domain.university.exceptions.UniversityNotFoundException;
import inno.edu.api.domain.university.models.University;
import inno.edu.api.domain.university.repositories.UniversityRepository;
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
@RequestMapping(value = "/api/universities", produces = "application/hal+json")
public class UniversityController {

    private final UniversityRepository universityRepository;
    private final CreateUniversityCommand createUniversityCommand;
    private final UpdateUniversityCommand updateUniversityCommand;

    private final ResourceBuilder resourceBuilder;

    @Autowired
    public UniversityController(UniversityRepository universityRepository, CreateUniversityCommand createUniversityCommand, UpdateUniversityCommand updateUniversityCommand, ResourceBuilder resourceBuilder) {
        this.universityRepository = universityRepository;
        this.createUniversityCommand = createUniversityCommand;
        this.updateUniversityCommand = updateUniversityCommand;
        this.resourceBuilder = resourceBuilder;
    }

    @GetMapping
    public Resources<UniversityResource> all() {
        Iterable<University> universities = universityRepository.findAll();
        return resourceBuilder.from(universities, UniversityResource::new);
    }

    @GetMapping("/{id}")
    public UniversityResource get(@PathVariable UUID id) {
        Optional<University> university = ofNullable(universityRepository.findOne(id));
        return new UniversityResource(university.orElseThrow(() -> new UniversityNotFoundException(id)));
    }

    @PostMapping
    public ResponseEntity<?> post(@RequestBody University university) {
        UniversityResource universityResource = new UniversityResource(createUniversityCommand.run(university));
        return universityResource.toCreated();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> put(@PathVariable UUID id, @RequestBody University university) {
        UniversityResource universityResource = new UniversityResource(updateUniversityCommand.run(id, university));
        return universityResource.toUpdated();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        if (!universityRepository.exists(id)) {
            throw new UniversityNotFoundException(id);
        }
        universityRepository.delete(id);

        return noContent().build();
    }
}