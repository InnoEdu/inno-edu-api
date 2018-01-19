package inno.edu.api.domain.profile.skill.models;

import inno.edu.api.domain.skill.models.Skill;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.util.UUID;

import static javax.persistence.FetchType.LAZY;

@Entity
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@IdClass(ProfileSkillPrimaryKey.class)
@EqualsAndHashCode(exclude = {"skill"})
public class ProfileSkill {
    @Id
    private UUID profileId;

    @Id
    private UUID skillId;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "skillId", updatable = false, insertable = false)
    private Skill skill;
}
