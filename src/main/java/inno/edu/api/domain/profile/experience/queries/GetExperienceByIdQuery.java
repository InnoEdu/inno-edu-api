package inno.edu.api.domain.profile.experience.queries;

import inno.edu.api.domain.profile.experience.exceptions.ExperienceNotFoundException;
import inno.edu.api.domain.profile.experience.models.Experience;
import inno.edu.api.domain.profile.experience.repositories.ExperienceRepository;
import inno.edu.api.infrastructure.annotations.Query;

import java.util.UUID;

import static java.util.Optional.ofNullable;

@Query
public class GetExperienceByIdQuery {
    private final ExperienceRepository experienceRepository;

    public GetExperienceByIdQuery(ExperienceRepository experienceRepository) {
        this.experienceRepository = experienceRepository;
    }

    public Experience run(UUID id) {
        return ofNullable(experienceRepository.findOne(id))
                .orElseThrow(() -> new ExperienceNotFoundException(id));
    }
}
