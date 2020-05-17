package server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import server.data.GroupRepository;
import server.data.UserRepository;
import server.domain.User;

import java.util.Collections;

@Component
public class TestDataInit implements CommandLineRunner {

    @Autowired
    UserRepository userRepository;

    @Autowired
    GroupRepository groupRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        userRepository.deleteAll();
        userRepository.save(new User("user", passwordEncoder.encode("pwd"), Collections.singletonList("ROLE_USER")));
        userRepository.save(new User("admin", passwordEncoder.encode("apwd"), Collections.singletonList("ROLE_ADMIN")));
    }
}
