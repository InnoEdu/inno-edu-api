package inno.edu.api.domain.profile.interest.queries;

import inno.edu.api.domain.profile.interest.exceptions.InterestNotFoundException;
import inno.edu.api.domain.profile.interest.models.Interest;
import inno.edu.api.domain.profile.interest.repositories.InterestRepository;
import inno.edu.api.infrastructure.annotations.Query;

import java.util.UUID;

import static java.util.Optional.ofNullable;

@Query
public class GetInterestByIdQuery {
    private final InterestRepository experienceRepository;

    public GetInterestByIdQuery(InterestRepository experienceRepository) {
        this.experienceRepository = experienceRepository;
    }

    public Interest run(UUID id) {
        return ofNullable(experienceRepository.findOne(id))
                .orElseThrow(() -> new InterestNotFoundException(id));
    }
}
