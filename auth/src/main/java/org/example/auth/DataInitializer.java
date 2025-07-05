package org.example.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (userRepo.findByUsername("superadmin").isEmpty()) {
            User user = new User();
            user.setUsername("superadmin");
            user.setPassword(passwordEncoder.encode("superadmin123"));
            user.setRoles(Set.of(Role.ROLE_SUPERADMIN, Role.ROLE_ADMIN, Role.ROLE_USER));
            userRepo.save(user);
        }

        if (userRepo.findByUsername("admin").isEmpty()) {
            User user = new User();
            user.setUsername("admin");
            user.setPassword(passwordEncoder.encode("admin123"));
            user.setRoles(Set.of(Role.ROLE_ADMIN, Role.ROLE_USER));
            userRepo.save(user);
        }
    }
}
