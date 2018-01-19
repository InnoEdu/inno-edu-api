package inno.edu.api.domain.skill.repositories;

import inno.edu.api.domain.skill.models.Skill;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface SkillRepository extends CrudRepository<Skill, UUID> {
}
