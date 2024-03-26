package net.jenske.hyttebooking.controller;

import jakarta.validation.Valid;
import net.jenske.hyttebooking.controller.exception.ResourceNotFoundException;
import net.jenske.hyttebooking.model.User;
import net.jenske.hyttebooking.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:8081")
@RequestMapping(path = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    @Autowired
    UserRepository userRepository;

    /**
     * Gets all users.
     *
     * @return a list of users
     */
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers(@RequestParam(required = false) String email) {
        try {
            List<User> users = new ArrayList<User>();

            if (email == null)
                users.addAll(userRepository.findAll());
            else
                users.addAll(userRepository.findByEmailContaining(email));

            if (users.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Gets user by id.
     *
     * @param id the unique identifier of the user to be retrieved
     * @return a list of users
     */
    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") long id) {
            User user = userRepository.findById(id).
                    orElseThrow(() -> new ResourceNotFoundException("User not found for this id: " + id));
            return ResponseEntity.ok(user);
    }

    /**
     * Creates a new user in the system.
     *
     * @param user the user object to be created
     * @return the newly created user with a 201 status code, or an error message if the creation fails
     */
    @PostMapping("/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        try {
            Optional<User> existingUser = userRepository.findByEmail(user.getEmail());
            if (existingUser.isPresent()) {
                return new ResponseEntity<>(existingUser.get(), HttpStatus.OK); // User already exists
            }
            User _user = userRepository
                    .save(new User(user.getFirstName(), user.getLastName(), user.getEmail(), user.getSub()));
            return new ResponseEntity<>(_user, HttpStatus.CREATED);
        } catch (Exception e) {
            throw new ResourceNotFoundException(e.getMessage());
        }
    }

    /**
     * Updates an existing user's details in the system.
     *
     * @param id the unique identifier of the user to update
     * @param user the updated user information
     * @return the updated user, or a not found response if the user with the specified ID doesn't exist
     */
    @PutMapping("/users/{id}")
    public ResponseEntity<User> updateUser(@Valid @PathVariable("id") long id, @RequestBody User user) {
        Optional<User> userData = userRepository.findById(id);

        if (userData.isPresent()) {
            User _user = userData.get();
            _user.setFirstName(user.getFirstName());
            _user.setLastName(user.getLastName());
            _user.setEmail(user.getEmail());
            return new ResponseEntity<>(userRepository.save(_user), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Deletes a user from the system.
     *
     * @param id the unique identifier of the user to be deleted
     * @return a 204 status code if the deletion is successful, or an error message if the deletion fails
     */
    @DeleteMapping("/users/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable("id") long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        userRepository.delete(user);
        return ResponseEntity.noContent().build();
    }
}
