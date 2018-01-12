package inno.edu.api.domain.school.root.queries;

import inno.edu.api.domain.school.root.models.School;
import inno.edu.api.domain.school.root.repositories.SchoolRepository;
import inno.edu.api.infrastructure.annotations.Query;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

@Query
public class GetSchoolsQuery {
    private final SchoolRepository schoolRepository;

    public GetSchoolsQuery(SchoolRepository schoolRepository) {
        this.schoolRepository = schoolRepository;
    }

    public List<School> run() {
        return newArrayList(schoolRepository.findAll());
    }
}
