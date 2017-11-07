package inno.edu.api.domain.user.repositories;

import inno.edu.api.domain.user.models.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
}