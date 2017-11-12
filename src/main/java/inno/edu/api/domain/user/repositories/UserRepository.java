package inno.edu.api.domain.user.repositories;

import inno.edu.api.domain.user.models.User;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface UserRepository extends CrudRepository<User, UUID> {
    boolean existsUserByIdAndIsMentorIsTrue(UUID id);
    boolean existsUserByUserName(String userName);
}