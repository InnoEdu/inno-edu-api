package inno.edu.api.controllers;

import inno.edu.api.controllers.resources.MentorProfileResource;
import inno.edu.api.controllers.resources.ResourceBuilder;
import inno.edu.api.controllers.resources.SchoolResource;
import inno.edu.api.domain.school.commands.CreateSchoolCommand;
import inno.edu.api.domain.school.commands.UpdateSchoolCommand;
import inno.edu.api.domain.school.exceptions.SchoolNotFoundException;
import inno.edu.api.domain.school.models.School;
import inno.edu.api.domain.school.repositories.SchoolRepository;
import inno.edu.api.domain.profile.queries.GetMentorProfilesBySchoolIdQuery;
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
@RequestMapping(value = "/api/schools", produces = "application/hal+json")
public class SchoolController {

    private final SchoolRepository schoolRepository;

    private final CreateSchoolCommand createSchoolCommand;
    private final UpdateSchoolCommand updateSchoolCommand;

    private final GetMentorProfilesBySchoolIdQuery getMentorProfilesBySchoolIdQuery;

    private final ResourceBuilder resourceBuilder;

    @Autowired
    public SchoolController(SchoolRepository schoolRepository, CreateSchoolCommand createSchoolCommand, UpdateSchoolCommand updateSchoolCommand, GetMentorProfilesBySchoolIdQuery getMentorProfilesBySchoolIdQuery, ResourceBuilder resourceBuilder) {
        this.schoolRepository = schoolRepository;
        this.createSchoolCommand = createSchoolCommand;
        this.updateSchoolCommand = updateSchoolCommand;
        this.getMentorProfilesBySchoolIdQuery = getMentorProfilesBySchoolIdQuery;
        this.resourceBuilder = resourceBuilder;
    }

    @GetMapping
    public Resources<SchoolResource> all() {
        Iterable<School> schools = schoolRepository.findAll();
        return resourceBuilder.from(schools, SchoolResource::new);
    }

    @GetMapping("/{id}")
    public SchoolResource get(@PathVariable UUID id) {
        Optional<School> school = ofNullable(schoolRepository.findOne(id));
        return new SchoolResource(school.orElseThrow(() -> new SchoolNotFoundException(id)));
    }

    @GetMapping("/{id}/mentors")
    public Resources<MentorProfileResource> allMentorsProfile(@PathVariable UUID id) {
        if (!schoolRepository.exists(id)) {
            throw new SchoolNotFoundException(id);
        }
        return resourceBuilder.from(getMentorProfilesBySchoolIdQuery.run(id), MentorProfileResource::new);
    }

    @PostMapping
    public ResponseEntity<?> post(@RequestBody School school) {
        SchoolResource schoolResource = new SchoolResource(createSchoolCommand.run(school));
        return schoolResource.toCreated();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> put(@PathVariable UUID id, @RequestBody School school) {
        SchoolResource schoolResource = new SchoolResource(updateSchoolCommand.run(id, school));
        return schoolResource.toUpdated();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        if (!schoolRepository.exists(id)) {
            throw new SchoolNotFoundException(id);
        }
        schoolRepository.delete(id);

        return noContent().build();
    }
}