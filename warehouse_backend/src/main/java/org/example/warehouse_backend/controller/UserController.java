package org.example.warehouse_backend.controller;

import lombok.RequiredArgsConstructor;
import org.example.warehouse_backend.entity.User;
import org.example.warehouse_backend.repository.UserRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepo;

    @GetMapping
    public List<User> getAll() {
        return userRepo.findAll();
    }

    @PostMapping
    public User create(@RequestBody User user) {
        return userRepo.save(user);
    }
}
