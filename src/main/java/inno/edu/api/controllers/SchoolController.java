package inno.edu.api.controllers;

import inno.edu.api.controllers.resources.MentorProfileUserResource;
import inno.edu.api.controllers.resources.ResourceBuilder;
import inno.edu.api.controllers.resources.SchoolResource;
import inno.edu.api.domain.profile.queries.GetMentorProfilesBySchoolIdQuery;
import inno.edu.api.domain.school.commands.CreateSchoolCommand;
import inno.edu.api.domain.school.commands.DeleteSchoolCommand;
import inno.edu.api.domain.school.commands.UpdateSchoolCommand;
import inno.edu.api.domain.school.commands.dtos.CreateSchoolRequest;
import inno.edu.api.domain.school.commands.dtos.UpdateSchoolRequest;
import inno.edu.api.domain.school.models.School;
import inno.edu.api.domain.school.queries.GetSchoolByIdQuery;
import inno.edu.api.domain.school.repositories.SchoolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
@RequestMapping(value = "/api/schools", produces = "application/hal+json")
public class SchoolController {

    private final SchoolRepository schoolRepository;

    private final CreateSchoolCommand createSchoolCommand;
    private final UpdateSchoolCommand updateSchoolCommand;
    private final DeleteSchoolCommand deleteSchoolCommand;

    private final GetSchoolByIdQuery getSchoolByIdQuery;
    private final GetMentorProfilesBySchoolIdQuery getMentorProfilesBySchoolIdQuery;

    private final ResourceBuilder resourceBuilder;

    @Autowired
    public SchoolController(SchoolRepository schoolRepository, CreateSchoolCommand createSchoolCommand, UpdateSchoolCommand updateSchoolCommand, DeleteSchoolCommand deleteSchoolCommand, GetSchoolByIdQuery getSchoolByIdQuery, GetMentorProfilesBySchoolIdQuery getMentorProfilesBySchoolIdQuery, ResourceBuilder resourceBuilder) {
        this.schoolRepository = schoolRepository;
        this.createSchoolCommand = createSchoolCommand;
        this.updateSchoolCommand = updateSchoolCommand;
        this.deleteSchoolCommand = deleteSchoolCommand;
        this.getSchoolByIdQuery = getSchoolByIdQuery;
        this.getMentorProfilesBySchoolIdQuery = getMentorProfilesBySchoolIdQuery;
        this.resourceBuilder = resourceBuilder;
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Resources<Object> all() {
        Iterable<School> schools = schoolRepository.findAll();
        return resourceBuilder.wrappedFrom(schools, SchoolResource::new, SchoolResource.class);
    }

    @GetMapping("/{id}")
    public SchoolResource get(@PathVariable UUID id) {
        return new SchoolResource(getSchoolByIdQuery.run(id));
    }

    @GetMapping("/{id}/mentors")
    public Resources<Object> allMentorsProfile(@PathVariable UUID id) {
        return resourceBuilder.wrappedFrom(getMentorProfilesBySchoolIdQuery.run(id), MentorProfileUserResource::new, MentorProfileUserResource.class);
    }

    @PostMapping
    public ResponseEntity<School> post(@Valid @RequestBody CreateSchoolRequest request) {
        SchoolResource schoolResource = new SchoolResource(createSchoolCommand.run(request));
        return schoolResource.toCreated();
    }

    @PutMapping("/{id}")
    public ResponseEntity<School> put(@PathVariable UUID id, @Valid @RequestBody UpdateSchoolRequest request) {
        SchoolResource schoolResource = new SchoolResource(updateSchoolCommand.run(id, request));
        return schoolResource.toUpdated();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        deleteSchoolCommand.run(id);
        return noContent().build();
    }
}