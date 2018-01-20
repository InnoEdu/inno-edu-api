package inno.edu.api.controllers;

import inno.edu.api.infrastructure.web.ResourceBuilder;
import inno.edu.api.domain.skill.models.resources.SkillResource;
import inno.edu.api.domain.skill.commands.CreateSkillCommand;
import inno.edu.api.domain.skill.commands.DeleteSkillCommand;
import inno.edu.api.domain.skill.commands.UpdateSkillCommand;
import inno.edu.api.domain.skill.models.dtos.CreateSkillRequest;
import inno.edu.api.domain.skill.models.dtos.UpdateSkillRequest;
import inno.edu.api.domain.skill.models.Skill;
import inno.edu.api.domain.skill.queries.GetSkillByIdQuery;
import inno.edu.api.domain.skill.queries.GetSkillsQuery;
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
import java.util.List;
import java.util.UUID;

import static org.springframework.http.ResponseEntity.noContent;

@RestController
@RequestMapping(value = "/api/skills", produces = "application/hal+json")
public class SkillController {
    private final ResourceBuilder resourceBuilder;

    private final CreateSkillCommand createSkillCommand;
    private final UpdateSkillCommand updateSkillCommand;
    private final DeleteSkillCommand deleteSkillCommand;

    private final GetSkillsQuery getSkillsQuery;
    private final GetSkillByIdQuery getSkillByIdQuery;

    @Autowired
    public SkillController(CreateSkillCommand createSkillCommand, UpdateSkillCommand updateSkillCommand, DeleteSkillCommand deleteSkillCommand, GetSkillsQuery getSkillsQuery, GetSkillByIdQuery getSkillByIdQuery, ResourceBuilder resourceBuilder) {
        this.getSkillsQuery = getSkillsQuery;
        this.createSkillCommand = createSkillCommand;
        this.updateSkillCommand = updateSkillCommand;
        this.deleteSkillCommand = deleteSkillCommand;
        this.getSkillByIdQuery = getSkillByIdQuery;
        this.resourceBuilder = resourceBuilder;
    }

    @GetMapping
    public Resources<Object> all() {
        List<Skill> skills = getSkillsQuery.run();
        return resourceBuilder.wrappedFrom(skills, SkillResource::new, SkillResource.class);
    }

    @GetMapping("/{id}")
    public SkillResource get(@PathVariable UUID id) {
        return new SkillResource(getSkillByIdQuery.run(id));
    }

    @PostMapping
    public ResponseEntity<Skill> post(@Valid @RequestBody CreateSkillRequest request) {
        SkillResource skillResource = new SkillResource(createSkillCommand.run(request));
        return skillResource.toCreated();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Skill> put(@PathVariable UUID id, @Valid @RequestBody UpdateSkillRequest request) {
        SkillResource skillResource = new SkillResource(updateSkillCommand.run(id, request));
        return skillResource.toUpdated();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        deleteSkillCommand.run(id);
        return noContent().build();
    }
}