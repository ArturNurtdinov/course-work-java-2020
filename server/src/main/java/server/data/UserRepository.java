package server.data;

import org.springframework.data.jpa.repository.JpaRepository;
import server.domain.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByUserName(String userName);
}
