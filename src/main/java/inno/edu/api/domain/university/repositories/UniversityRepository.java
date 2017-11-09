package inno.edu.api.domain.university.repositories;

import inno.edu.api.domain.university.models.University;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface UniversityRepository extends CrudRepository<University, UUID> {
}