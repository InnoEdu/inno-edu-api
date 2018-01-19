package inno.edu.api.domain.skill.queries;

import inno.edu.api.domain.skill.models.Skill;
import inno.edu.api.domain.skill.repositories.SkillRepository;
import inno.edu.api.infrastructure.annotations.Query;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

@Query
public class GetSkillsQuery {
    private final SkillRepository skillRepository;

    public GetSkillsQuery(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }

    public List<Skill> run() {
        return newArrayList(skillRepository.findAll());
    }
}
