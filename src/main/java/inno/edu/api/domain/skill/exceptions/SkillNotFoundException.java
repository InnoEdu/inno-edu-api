package inno.edu.api.domain.skill.exceptions;

import java.util.UUID;

public class SkillNotFoundException extends RuntimeException {
    public SkillNotFoundException(UUID skill) {
        super("Could not find skill '" + skill.toString() + "'.");
    }
}
