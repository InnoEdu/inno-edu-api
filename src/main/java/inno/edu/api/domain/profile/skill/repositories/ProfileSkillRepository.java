package inno.edu.api.domain.profile.skill.repositories;

import inno.edu.api.domain.profile.skill.models.ProfileSkill;
import inno.edu.api.domain.profile.skill.models.ProfileSkillPrimaryKey;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface ProfileSkillRepository extends CrudRepository<ProfileSkill, ProfileSkillPrimaryKey> {
    List<ProfileSkill> findByProfileId(UUID profileId);
}
