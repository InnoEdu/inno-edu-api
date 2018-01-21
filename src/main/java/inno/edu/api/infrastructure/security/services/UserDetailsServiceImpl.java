package inno.edu.api.infrastructure.security.services;

import inno.edu.api.domain.user.root.models.ApplicationUser;
import inno.edu.api.domain.user.root.repositories.UserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static java.util.Collections.emptyList;
import static java.util.Optional.ofNullable;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{
    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ApplicationUser applicationUser = ofNullable(userRepository.findOneByUsername(username))
                .orElseThrow(() -> new UsernameNotFoundException(username));

        return new User(applicationUser.getUsername(), applicationUser.getPassword(), emptyList());
    }
}
