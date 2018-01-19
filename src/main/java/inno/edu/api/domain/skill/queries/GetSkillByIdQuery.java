package inno.edu.api.domain.skill.queries;

import inno.edu.api.domain.skill.exceptions.SkillNotFoundException;
import inno.edu.api.domain.skill.models.Skill;
import inno.edu.api.domain.skill.repositories.SkillRepository;
import inno.edu.api.infrastructure.annotations.Query;

import java.util.UUID;

import static java.util.Optional.ofNullable;

@Query
public class GetSkillByIdQuery {
    private final SkillRepository skillRepository;

    public GetSkillByIdQuery(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }

    public Skill run(UUID id) {
        return ofNullable(skillRepository.findOne(id))
                .orElseThrow(() -> new SkillNotFoundException(id));
    }
}
