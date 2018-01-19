package inno.edu.api.support;

import inno.edu.api.domain.skill.commands.dtos.CreateSkillRequest;
import inno.edu.api.domain.skill.commands.dtos.UpdateSkillRequest;
import inno.edu.api.domain.skill.models.Skill;

import java.util.List;
import java.util.UUID;

import static com.google.common.collect.Lists.newArrayList;
import static java.util.UUID.fromString;

public class SkillFactory {
    public static Skill skill() {
        return Skill.builder()
                .id(fromString("52c048db-062f-42a6-918d-e9aeea8d3a0d"))
                .title("Communication")
                .description("Description.")
                .build();
    }

    public static Skill skillToDelete() {
        return skill().toBuilder()
                .id(fromString("094dad92-4488-4b36-9c27-dcfde9a1a32d"))
                .build();
    }

    public static Skill newSkill(UUID id) {
        return skill().toBuilder()
                .id(id)
                .build();
    }

    public static CreateSkillRequest createSkillRequest() {
        return CreateSkillRequest.builder()
                .title(skill().getTitle())
                .description(skill().getDescription())
                .build();
    }

    public static UpdateSkillRequest updateSkillRequest() {
        return UpdateSkillRequest.builder()
                .title(updatedSkill().getTitle())
                .description(updatedSkill().getDescription())
                .build();
    }

    public static Skill updatedSkill() {
        return skill().toBuilder()
                .title("UpdatedSkill")
                .description("UpdatedDescription.")
                .build();
    }

    public static List<Skill> skills() {
        return newArrayList(skill());
    }
}
