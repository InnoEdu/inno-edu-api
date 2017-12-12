package inno.edu.api.domain.user.repositories;

import inno.edu.api.domain.user.models.User;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface UserRepository extends CrudRepository<User, UUID> {
    User findOneByUsernameAndPassword(String username, String password);
    User findOneByUsername(String username);
    boolean existsByIdAndIsMentorIsTrue(UUID id);
    boolean existsByIdAndIsMentorIsFalse(UUID id);
    boolean existsByUsername(String username);
}