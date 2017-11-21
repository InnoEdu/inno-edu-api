package inno.edu.api.controllers;

import inno.edu.api.controllers.resources.MentorProfileResource;
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
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ResponseHeader;
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
    @ApiOperation(value = "Find all schools", notes = "Return all schools.", response = School.class, responseContainer = "List")
    public Resources<SchoolResource> all() {
        Iterable<School> schools = schoolRepository.findAll();
        return resourceBuilder.from(schools, SchoolResource::new);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get an school", notes = "Get an school by ID.", response = School.class)
    @ApiResponses({
            @ApiResponse(code = 404, message = "School not found."),
    })
    public SchoolResource get(@PathVariable UUID id) {
        return new SchoolResource(getSchoolByIdQuery.run(id));
    }

    @GetMapping("/{id}/mentors")
    @ApiOperation(value = "Get school mentors.", notes = "Get all mentors for a specific school.")
    @ApiResponses({
            @ApiResponse(code = 404, message = "School not found.")
    })
    public Resources<MentorProfileResource> allMentorsProfile(@PathVariable UUID id) {
        return resourceBuilder.from(getMentorProfilesBySchoolIdQuery.run(id), MentorProfileResource::new);
    }

    @PostMapping
    @ApiOperation(value = "Create a new school", notes = "Creates a new school.")
    @ApiResponses({
            @ApiResponse(code = 201, message = "New school successfully created.", responseHeaders = @ResponseHeader(name = "Location", description = "Link to the new resource created.", response = String.class)),
    })
    public ResponseEntity<School> post(@RequestBody CreateSchoolRequest createSchoolRequest) {
        SchoolResource schoolResource = new SchoolResource(createSchoolCommand.run(createSchoolRequest));
        return schoolResource.toCreated();
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update an school", notes = "Update an school.", response = School.class)
    @ApiResponses({
            @ApiResponse(code = 201, message = "New school successfully updated.", responseHeaders = @ResponseHeader(name = "Location", description = "Link to the updated resource.", response = String.class)),
            @ApiResponse(code = 404, message = "School not found."),
    })
    public ResponseEntity<School> put(@PathVariable UUID id, @RequestBody UpdateSchoolRequest updateSchoolRequest) {
        SchoolResource schoolResource = new SchoolResource(updateSchoolCommand.run(id, updateSchoolRequest));
        return schoolResource.toUpdated();
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete an school", notes = "Delete an school, this operation cannot be undone.")
    @ApiResponses({
            @ApiResponse(code = 204, message = "School successfully deleted."),
            @ApiResponse(code = 404, message = "School not found.")
    })
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        deleteSchoolCommand.run(id);
        return noContent().build();
    }
}