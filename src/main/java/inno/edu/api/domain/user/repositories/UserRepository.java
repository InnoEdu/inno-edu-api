package inno.edu.api.domain.user.repositories;

import inno.edu.api.domain.user.models.User;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface UserRepository extends CrudRepository<User, UUID> {
    User findOneByUserNameAndPassword(String userName, String password);
    boolean existsByIdAndIsMentorIsTrue(UUID id);
    boolean existsByIdAndIsMentorIsFalse(UUID id);
    boolean existsByUserName(String userName);
}