package inno.edu.api.controllers;

import inno.edu.api.controllers.resources.MentorProfileResource;
import inno.edu.api.controllers.resources.ResourceBuilder;
import inno.edu.api.domain.profile.commands.CreateMentorProfileCommand;
import inno.edu.api.domain.profile.commands.DeleteMentorProfileCommand;
import inno.edu.api.domain.profile.commands.UpdateMentorProfileCommand;
import inno.edu.api.domain.profile.commands.UpdateMentorProfileStatusCommand;
import inno.edu.api.domain.profile.commands.dtos.CreateMentorProfileRequest;
import inno.edu.api.domain.profile.commands.dtos.UpdateMentorProfileRequest;
import inno.edu.api.domain.profile.models.MenteeProfile;
import inno.edu.api.domain.profile.models.MentorProfile;
import inno.edu.api.domain.profile.models.ProfileStatus;
import inno.edu.api.domain.profile.queries.GetMentorProfileByIdQuery;
import inno.edu.api.domain.profile.repositories.MentorProfileRepository;
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

import javax.validation.Valid;
import java.util.UUID;

import static org.springframework.http.ResponseEntity.noContent;

@RestController
@RequestMapping(value = "/api/mentor-profiles", produces = "application/hal+json")
public class MentorProfileController {

    private final ResourceBuilder resourceBuilder;
    private final MentorProfileRepository mentorProfileRepository;

    private final GetMentorProfileByIdQuery getMentorProfileByIdQuery;

    private final UpdateMentorProfileCommand updateMentorProfileCommand;
    private final CreateMentorProfileCommand createMentorProfileCommand;
    private final DeleteMentorProfileCommand deleteMentorProfileCommand;

    private final UpdateMentorProfileStatusCommand updateMentorProfileStatusCommand;

    @Autowired
    public MentorProfileController(MentorProfileRepository mentorProfileRepository, ResourceBuilder resourceBuilder, GetMentorProfileByIdQuery getMentorProfileByIdQuery, UpdateMentorProfileCommand updateMentorProfileCommand, CreateMentorProfileCommand createMentorProfileCommand, DeleteMentorProfileCommand deleteMentorProfileCommand, UpdateMentorProfileStatusCommand updateMentorProfileStatusCommand) {
        this.mentorProfileRepository = mentorProfileRepository;
        this.resourceBuilder = resourceBuilder;
        this.getMentorProfileByIdQuery = getMentorProfileByIdQuery;
        this.updateMentorProfileCommand = updateMentorProfileCommand;
        this.createMentorProfileCommand = createMentorProfileCommand;
        this.deleteMentorProfileCommand = deleteMentorProfileCommand;
        this.updateMentorProfileStatusCommand = updateMentorProfileStatusCommand;
    }

    @GetMapping
    @ApiOperation(value = "Find all profiles", notes = "Return all profiles.", response = MenteeProfile.class, responseContainer = "List")
    public Resources<MentorProfileResource> all() {
        Iterable<MentorProfile> profiles = mentorProfileRepository.findAll();
        return resourceBuilder.from(profiles, MentorProfileResource::new);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get an profile", notes = "Get an profile by ID.", response = MenteeProfile.class)
    @ApiResponses({
            @ApiResponse(code = 404, message = "Profile not found."),
    })
    public MentorProfileResource get(@PathVariable UUID id) {
        return new MentorProfileResource(getMentorProfileByIdQuery.run(id));
    }

    @PostMapping
    @ApiOperation(value = "Create a new profile", notes = "Creates a new profile.")
    @ApiResponses({
            @ApiResponse(code = 201, message = "New profile successfully created.", responseHeaders = @ResponseHeader(name = "Location", description = "Link to the new resource created.", response = String.class)),
            @ApiResponse(code = 404, message = "Invalid mentor user ID or school ID supplied."),
    })
    public ResponseEntity<MentorProfile> post(@Valid @RequestBody CreateMentorProfileRequest createMentorProfileRequest) {
        MentorProfileResource mentorProfileResource = new MentorProfileResource(createMentorProfileCommand.run(createMentorProfileRequest));
        return mentorProfileResource.toCreated();
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update an profile", notes = "Update an profile.", response = MenteeProfile.class)
    @ApiResponses({
            @ApiResponse(code = 201, message = "New profile successfully updated.", responseHeaders = @ResponseHeader(name = "Location", description = "Link to the updated resource.", response = String.class)),
            @ApiResponse(code = 404, message = "Profile not found."),
    })
    public ResponseEntity<MentorProfile> put(@PathVariable UUID id, @Valid @RequestBody UpdateMentorProfileRequest updateMentorProfileRequest) {
        MentorProfileResource profileResource = new MentorProfileResource(updateMentorProfileCommand.run(id, updateMentorProfileRequest));
        return profileResource.toUpdated();
    }

    @PutMapping("/{id}/approve")
    @ApiOperation(value = "Approve an profile", notes = "Approve a new mentor profile. All other profiles will be inactivated.", response = MenteeProfile.class)
    @ApiResponses({
            @ApiResponse(code = 201, message = "Profile successfully approved."),
            @ApiResponse(code = 404, message = "Profile not found."),
    })
    public ResponseEntity<?> approve(@PathVariable UUID id) {
        updateMentorProfileStatusCommand.run(id, ProfileStatus.ACTIVE);
        return noContent().build();
    }

    @PutMapping("/{id}/reject")
    @ApiOperation(value = "Reject a profile", notes = "Reject a mentor profile.", response = MenteeProfile.class)
    @ApiResponses({
            @ApiResponse(code = 201, message = "Profile successfully rejected."),
            @ApiResponse(code = 404, message = "Profile not found."),
    })
    public ResponseEntity<?> reject(@PathVariable UUID id) {
        updateMentorProfileStatusCommand.run(id, ProfileStatus.REJECTED);
        return noContent().build();
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete an profile", notes = "Delete an profile, this operation cannot be undone.")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Profile successfully deleted."),
            @ApiResponse(code = 404, message = "Profile not found.")
    })
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        deleteMentorProfileCommand.run(id);
        return noContent().build();
    }
}
