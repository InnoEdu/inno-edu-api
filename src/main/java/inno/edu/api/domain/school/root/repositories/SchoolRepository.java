package inno.edu.api.domain.school.root.repositories;

import inno.edu.api.domain.school.root.models.School;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface SchoolRepository extends CrudRepository<School, UUID> {
}