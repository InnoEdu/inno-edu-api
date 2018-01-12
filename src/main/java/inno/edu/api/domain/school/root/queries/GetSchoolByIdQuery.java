package inno.edu.api.domain.school.root.queries;

import inno.edu.api.domain.school.root.exceptions.SchoolNotFoundException;
import inno.edu.api.domain.school.root.models.School;
import inno.edu.api.domain.school.root.repositories.SchoolRepository;
import inno.edu.api.infrastructure.annotations.Query;

import java.util.UUID;

import static java.util.Optional.ofNullable;

@Query
public class GetSchoolByIdQuery {
    private final SchoolRepository schoolRepository;

    public GetSchoolByIdQuery(SchoolRepository schoolRepository) {
        this.schoolRepository = schoolRepository;
    }

    public School run(UUID id) {
        return ofNullable(schoolRepository.findOne(id))
                .orElseThrow(() -> new SchoolNotFoundException(id));
    }
}
