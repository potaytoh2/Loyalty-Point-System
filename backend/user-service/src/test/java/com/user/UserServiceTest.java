package com.user;

import com.user.repository.*;
import com.user.service.*;
import com.user.entity.Role;
import com.user.entity.User;
import lombok.RequiredArgsConstructor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.Instant;
import java.sql.Timestamp;
import java.util.UUID;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

//import optional
import java.util.Arrays;
import java.util.Optional;
import com.user.exception.ResourceNotFoundException;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private static User mockUser;
    private static User mockUser2;
    private static String userId;
    private static String userId2;

    @BeforeAll
    static void setUp() {

        mockUser = new User(
            "john123",
            "user123@example.com",
            "John",
            "Doe",
            Role.convertRoleFromString("USER")
        );

        mockUser2 = new User(
            "jane123",
            "user124@example.com",
            "Jane",
            "Doe",
            Role.convertRoleFromString("USER")
        );
        userId = UUID.randomUUID().toString();
        userId2 = UUID.randomUUID().toString();
        mockUser.setId(userId);
        mockUser2.setId(userId2);
    }

    @Test
    void getUserById_existingUser_returnsUser() {
        //Arrange
        mockUser.setId(userId);
        when(userRepository.findById(userId)).thenReturn(Optional.of(mockUser));
        
        // Act
        User foundUser = userService.getUserById(userId);
        
        // Assert
        assertNotNull(foundUser);
        assertEquals(userId, foundUser.getId());
    }

    @Test
    void getUserById_nonExistingUser_throwsException() {
        // Arrange
        when(userRepository.findById(userId)).thenReturn(Optional.empty());
        
        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> userService.getUserById(userId));
    }

    @Test
    void getUsers_returnsUserList() {
        // Arrange
        when(userRepository.findAll()).thenReturn(Arrays.asList(mockUser, mockUser2));
        
        // Act
        Iterable<User> users = userService.getUsers();
        
        // Assert
        assertNotNull(users);
        assertTrue(users.iterator().hasNext());
    }

    @Test
    void createUser_savesUser() {
        // Arrange
        when(userRepository.save(any(User.class))).thenReturn(mockUser);
        
        // Act
        User savedUser = userService.createUser(mockUser);
        
        // Assert
        assertNotNull(savedUser);
        assertEquals(mockUser, savedUser);
    }

    @Test
    void updateUser_updatesUser() {
        // Arrange
        User updatedMockUser = new User(
            "jane123",
            "user123@example.com",
            "Jane",
            "Doe",
            Role.convertRoleFromString("USER")
        );
        updatedMockUser.setId(userId);
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));
        
        // Act
        User updatedUser = userService.updateUser(updatedMockUser);
        
        // Assert
        assertEquals("Jane", updatedUser.getFirst_name());
    }

    @Test
    void deleteUserById_existingUser_deletesUser() {
        // Arrange
        when(userRepository.findById(userId)).thenReturn(Optional.of(mockUser));
        doNothing().when(userRepository).deleteById(userId);
        
        // Act
        User deletedUser = userService.deleteUserById(userId);
        
        // Assert
        assertNotNull(deletedUser);
        assertEquals(userId, deletedUser.getId());
        verify(userRepository).deleteById(userId);
    }

}
