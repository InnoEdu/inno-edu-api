package inno.edu.api.domain.profile.queries;

import inno.edu.api.domain.profile.exceptions.ExperienceNotFoundException;
import inno.edu.api.domain.profile.models.Experience;
import inno.edu.api.domain.profile.repositories.ExperienceRepository;
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
