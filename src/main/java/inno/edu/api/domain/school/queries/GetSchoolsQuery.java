package inno.edu.api.domain.school.queries;

import inno.edu.api.domain.school.models.School;
import inno.edu.api.domain.school.repositories.SchoolRepository;
import inno.edu.api.infrastructure.annotations.Query;

import java.util.List;
import java.util.UUID;

import static com.google.common.collect.Lists.newArrayList;

@Query
public class GetSchoolsQuery {
    private final SchoolRepository schoolRepository;

    public GetSchoolsQuery(SchoolRepository schoolRepository) {
        this.schoolRepository = schoolRepository;
    }

    public List<School> run(UUID id) {
        return newArrayList(schoolRepository.findAll());
    }
}
