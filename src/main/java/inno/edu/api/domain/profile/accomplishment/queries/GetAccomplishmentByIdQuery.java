package inno.edu.api.domain.profile.accomplishment.queries;

import inno.edu.api.domain.profile.accomplishment.exceptions.AccomplishmentNotFoundException;
import inno.edu.api.domain.profile.accomplishment.models.Accomplishment;
import inno.edu.api.domain.profile.accomplishment.repositories.AccomplishmentRepository;
import inno.edu.api.infrastructure.annotations.Query;

import java.util.UUID;

import static java.util.Optional.ofNullable;

@Query
public class GetAccomplishmentByIdQuery {
    private final AccomplishmentRepository experienceRepository;

    public GetAccomplishmentByIdQuery(AccomplishmentRepository experienceRepository) {
        this.experienceRepository = experienceRepository;
    }

    public Accomplishment run(UUID id) {
        return ofNullable(experienceRepository.findOne(id))
                .orElseThrow(() -> new AccomplishmentNotFoundException(id));
    }
}
