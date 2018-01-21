package inno.edu.api.domain.user.root.queries;

import inno.edu.api.domain.user.root.models.ApplicationUser;
import inno.edu.api.domain.user.root.repositories.UserRepository;
import inno.edu.api.infrastructure.annotations.Query;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

@Query
public class GetUsersQuery {
    private final UserRepository userRepository;

    public GetUsersQuery(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<ApplicationUser> run() {
        return newArrayList(userRepository.findAll());
    }
}
