package inno.edu.api.domain.skill.assertions;

import inno.edu.api.domain.common.assertions.EntityExistsAssertion;
import inno.edu.api.domain.skill.exceptions.SkillNotFoundException;
import inno.edu.api.domain.skill.repositories.SkillRepository;
import inno.edu.api.infrastructure.annotations.Assertion;

import java.util.UUID;
import java.util.function.Function;

@Assertion
public class SkillExistsAssertion extends EntityExistsAssertion<SkillRepository, SkillNotFoundException, Function<UUID, SkillNotFoundException>> {
    protected SkillExistsAssertion(SkillRepository repository) {
        super(repository, SkillNotFoundException::new);
    }
}
