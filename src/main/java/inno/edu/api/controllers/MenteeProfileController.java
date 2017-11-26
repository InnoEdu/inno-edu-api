package inno.edu.api.controllers;

import inno.edu.api.controllers.resources.MenteeProfileResource;
import inno.edu.api.controllers.resources.ResourceBuilder;
import inno.edu.api.domain.profile.commands.CreateMenteeProfileCommand;
import inno.edu.api.domain.profile.commands.DeleteMenteeProfileCommand;
import inno.edu.api.domain.profile.commands.UpdateMenteeProfileCommand;
import inno.edu.api.domain.profile.commands.dtos.CreateMenteeProfileRequest;
import inno.edu.api.domain.profile.commands.dtos.UpdateMenteeProfileRequest;
import inno.edu.api.domain.profile.models.MenteeProfile;
import inno.edu.api.domain.profile.queries.GetMenteeProfileByIdQuery;
import inno.edu.api.domain.profile.repositories.MenteeProfileRepository;
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
@RequestMapping(value = "/api/mentee-profiles", produces = "application/hal+json")
public class MenteeProfileController {

    private final ResourceBuilder resourceBuilder;
    private final MenteeProfileRepository menteeProfileRepository;

    private final GetMenteeProfileByIdQuery getMenteeProfileByIdQuery;

    private final UpdateMenteeProfileCommand updateMenteeProfileCommand;
    private final CreateMenteeProfileCommand createMenteeProfileCommand;
    private final DeleteMenteeProfileCommand deleteMenteeProfileCommand;

    @Autowired
    public MenteeProfileController(MenteeProfileRepository menteeProfileRepository, ResourceBuilder resourceBuilder, GetMenteeProfileByIdQuery getMenteeProfileByIdQuery, UpdateMenteeProfileCommand updateMenteeProfileCommand, CreateMenteeProfileCommand createMenteeProfileCommand, DeleteMenteeProfileCommand deleteMenteeProfileCommand) {
        this.menteeProfileRepository = menteeProfileRepository;
        this.resourceBuilder = resourceBuilder;
        this.getMenteeProfileByIdQuery = getMenteeProfileByIdQuery;
        this.updateMenteeProfileCommand = updateMenteeProfileCommand;
        this.createMenteeProfileCommand = createMenteeProfileCommand;
        this.deleteMenteeProfileCommand = deleteMenteeProfileCommand;
    }

    @GetMapping
    @ApiOperation(value = "Find all profiles", notes = "Return all profiles.", response = MenteeProfile.class, responseContainer = "List")
    public Resources<MenteeProfileResource> all() {
        Iterable<MenteeProfile> profiles = menteeProfileRepository.findAll();
        return resourceBuilder.from(profiles, MenteeProfileResource::new);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get an profile", notes = "Get an profile by ID.", response = MenteeProfile.class)
    @ApiResponses({
            @ApiResponse(code = 404, message = "Profile not found."),
    })
    public MenteeProfileResource get(@PathVariable UUID id) {
        return new MenteeProfileResource(getMenteeProfileByIdQuery.run(id));
    }

    @PostMapping
    @ApiOperation(value = "Create a new profile", notes = "Creates a new profile.")
    @ApiResponses({
            @ApiResponse(code = 201, message = "New profile successfully created.", responseHeaders = @ResponseHeader(name = "Location", description = "Link to the new resource created.", response = String.class)),
            @ApiResponse(code = 400, message = "Mentee already has a profile created."),
            @ApiResponse(code = 404, message = "Invalid mentee user ID supplied."),
    })
    public ResponseEntity<MenteeProfile> post(@RequestBody CreateMenteeProfileRequest createMenteeProfileRequest) {
        MenteeProfileResource profileReource = new MenteeProfileResource(createMenteeProfileCommand.run(createMenteeProfileRequest));
        return profileReource.toCreated();
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update an profile", notes = "Update an profile.", response = MenteeProfile.class)
    @ApiResponses({
            @ApiResponse(code = 201, message = "New profile successfully updated.", responseHeaders = @ResponseHeader(name = "Location", description = "Link to the updated resource.", response = String.class)),
            @ApiResponse(code = 404, message = "Profile not found."),
    })
    public ResponseEntity<MenteeProfile> put(@PathVariable UUID id, @RequestBody UpdateMenteeProfileRequest updateMenteeProfileRequest) {
        MenteeProfileResource profileResource = new MenteeProfileResource(updateMenteeProfileCommand.run(id, updateMenteeProfileRequest));
        return profileResource.toUpdated();
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete an profile", notes = "Delete an profile, this operation cannot be undone.")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Profile successfully deleted."),
            @ApiResponse(code = 404, message = "Profile not found.")
    })
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        deleteMenteeProfileCommand.run(id);

        return noContent().build();
    }
}
