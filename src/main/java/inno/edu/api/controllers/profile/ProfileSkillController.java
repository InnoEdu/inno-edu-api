package inno.edu.api.controllers.profile;

import inno.edu.api.domain.profile.skill.models.resources.ProfileSkillResource;
import inno.edu.api.presentation.resources.ResourceBuilder;
import inno.edu.api.domain.skill.models.resources.SkillResource;
import inno.edu.api.domain.profile.skill.commands.CreateProfileSkillCommand;
import inno.edu.api.domain.profile.skill.commands.DeleteProfileSkillCommand;
import inno.edu.api.domain.profile.skill.queries.GetProfileSkillsByProfileIdQuery;
import inno.edu.api.domain.skill.models.Skill;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.ResponseEntity.noContent;

@RestController
@RequestMapping(value = "/api/profiles", produces = "application/hal+json")
public class ProfileSkillController {
    private final ResourceBuilder resourceBuilder;
    private final GetProfileSkillsByProfileIdQuery getProfileSkillsByProfileIdQuery;
    private final CreateProfileSkillCommand createProfileSkillCommand;
    private final DeleteProfileSkillCommand deleteProfileSkillCommand;

    public ProfileSkillController(ResourceBuilder resourceBuilder, GetProfileSkillsByProfileIdQuery getProfileSkillsByProfileIdQuery, CreateProfileSkillCommand createProfileSkillCommand, DeleteProfileSkillCommand deleteProfileSkillCommand) {
        this.resourceBuilder = resourceBuilder;
        this.getProfileSkillsByProfileIdQuery = getProfileSkillsByProfileIdQuery;
        this.createProfileSkillCommand = createProfileSkillCommand;
        this.deleteProfileSkillCommand = deleteProfileSkillCommand;
    }

    @GetMapping("/{profileId}/skills")
    public Resources<Object> all(@PathVariable UUID profileId) {
        List<Skill> skills = getProfileSkillsByProfileIdQuery.run(profileId);
        return resourceBuilder.wrappedFrom(skills, SkillResource::new, SkillResource.class);
    }

    @PostMapping("/{profileId}/skills/{id}")
    public ProfileSkillResource post(@PathVariable UUID profileId, @PathVariable UUID id) {
        return new ProfileSkillResource(createProfileSkillCommand.run(profileId, id));
    }

    @DeleteMapping("/{profileId}/skills/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID profileId, @PathVariable UUID id) {
        deleteProfileSkillCommand.run(profileId, id);
        return noContent().build();
    }
}
