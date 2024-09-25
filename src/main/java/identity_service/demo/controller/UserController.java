package identity_service.demo.controller;

import identity_service.demo.dto.request.CreationUserRequest;
import identity_service.demo.dto.request.UpdateUserRequest;
import identity_service.demo.entity.User;
import identity_service.demo.service.impl.UserServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserServiceImpl userService;

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") UUID id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody @Valid CreationUserRequest userCreation) {
        return new  ResponseEntity<>(userService.createUser(userCreation),HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable("id") UUID id) {
        userService.deleteUser(id);
        return "User " + id + " deleted!";
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable("id") UUID id, @RequestBody UpdateUserRequest userUpdate) {
        return ResponseEntity.ok(userService.updateUser(id,userUpdate));
    }
}
