package net.jenske.hyttebooking.controller;

import net.jenske.hyttebooking.model.User;
import net.jenske.hyttebooking.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;

    @Test
    public void testGetUserById() throws Exception {
        // Given
        long userId = 1L;  // Simulate an auto-generated ID
        User mockUser = new User("John", "Doe", "john.doe@example.com", "uniquesubwihtnumbers1003");
        mockUser.setUserId(userId);
        when(userRepository.findById(userId)).thenReturn(Optional.of(mockUser));

        // When & Then
        mockMvc.perform(get("/api/users/{id}", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(userId))
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"))
                .andExpect(jsonPath("$.sub").value("uniquesubwihtnumbers1003"));
        // Verify mockUser being called once
        verify(userRepository, times(1)).findById(userId);
    }


    @Test
    public void testGetUserByIdNotFound() throws Exception {
        // Given
        long userId = 1L;  // Example user ID
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // When & Then
        mockMvc.perform(get("/api/users/{id}", userId))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetAllUsers() throws Exception {
        // Given
        User mockUser = new User("John", "Doe", "john.doe@example.com", "uniquesubwihtnumbers1003");
        when(userRepository.findAll()).thenReturn(List.of(mockUser));

        // When & Then
        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName").value("John"))
                .andExpect(jsonPath("$[0].lastName").value("Doe"))
                .andExpect(jsonPath("$[0].email").value("johndoe@example.com"))
                .andExpect(jsonPath("$.sub").value("uniquesubwihtnumbers1003"));
    }

    @Test
    public void testGetAllUsersNotFound() throws Exception {
        // Given
        when(userRepository.findAll()).thenReturn(List.of());

        // When & Then
        mockMvc.perform(get("/api/users"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testCreateUser() throws Exception {
        // Given
        String sub = "uniquesubwihtnumbers1003"; // The sub value from Auth0
        User mockUser = new User("John", "Doe", "john.doe@example.com", sub);
        when(userRepository.save(any(User.class))).thenReturn(mockUser);

        // When & Then
        mockMvc.perform(post("/api/users")
                .contentType("application/json")
                .content("{\"firstName\":\"John\",\"lastName\":\"Doe\",\"email\":\"john.doe@example.com\",\"sub\":\"" + sub + "\"}"));

        verify(userRepository, times(1)).save(any(User.class));
    }


    @Test
    public void testCreateUserWithInvalidData() throws Exception {
        String invalidUserJson = "{\"firstName\":\"\",\"lastName\":\"Doe\",\"email\":\"not-an-email\"}";

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidUserJson))
                .andExpect(status().isBadRequest());
    }


    @Test
    public void testCreateUserInternalServerError() throws Exception {
        // Given
        when(userRepository.save(any(User.class))).thenThrow(new RuntimeException("Failed to save user"));

        // When & Then
        mockMvc.perform(post("/api/users")
                .contentType("application/json")
                .content("{\"firstName\":\"John\",\"lastName\":\"Doe\",\"email\":\"john.doe@example.com\"}"));

        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void testUpdateUser() throws Exception {
        // Given
        long userId = 1L;  // Example user ID
        String sub = "uniquesubwihtnumbers1003"; // The sub value from Auth0
        User existingUser = new User("John", "Doe", "john.doe@example.com", sub);
        existingUser.setUserId(userId);
        User updatedUser = new User("John", "Doe", "new.email@example.com", sub);
        updatedUser.setUserId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(User.class))).thenReturn(updatedUser);

        String updatedUserJson = "{\"firstName\":\"John\",\"lastName\":\"Doe\",\"email\":\"new.email@example.com\",\"sub\":\"" + sub + "\"}";

        // When & Then
        mockMvc.perform(put("/api/users/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedUserJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("new.email@example.com"));

        verify(userRepository).save(any(User.class));  // Verify that userRepository.save() was called
    }


    @Test
    public void testDeleteUser() throws Exception {
        long userId = 1L;
        String sub = "uniquesubwihtnumbers1003"; // The sub value from Auth0
        User mockUser = new User("John", "Doe", "john.doe@example.com", sub);
        mockUser.setUserId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(mockUser));
        doNothing().when(userRepository).delete(mockUser);

        mockMvc.perform(delete("/api/users/{id}", userId))
                .andExpect(status().isNoContent());

        verify(userRepository, times(1)).delete(mockUser);
    }

    @Test
    public void testDeleteUserNotFound() throws Exception {
        long userId = 1L;
        when(userRepository.existsById(userId)).thenReturn(false);

        mockMvc.perform(delete("/api/users/{id}", userId))
                .andExpect(status().isNotFound());
    }
}
