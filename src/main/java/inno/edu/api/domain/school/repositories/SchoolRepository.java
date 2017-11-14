package inno.edu.api.domain.school.repositories;

import inno.edu.api.domain.school.models.School;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface SchoolRepository extends CrudRepository<School, UUID> {
}