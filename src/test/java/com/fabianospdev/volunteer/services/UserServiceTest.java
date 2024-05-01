package com.fabianospdev.volunteer.services;

import com.fabianospdev.volunteer.dto.UserDTO;
import com.fabianospdev.volunteer.models.User;
import com.fabianospdev.volunteer.repositories.UserRepository;
import com.fabianospdev.volunteer.usecases.user.UserUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserUseCase userUseCase;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testFindAll() {
//        List<UserDTO> userList = new ArrayList<>();
//        userList.add(new UserDTO(new User("1", "John", "john@example.com", "+123456789")));
//        userList.add(new UserDTO(new User("2", "Maria", "maria@example.com", "+987654321")));
//
//        User exampleUser = new User("1", "John", 30, "Pastors", "Pastor",new ArrayList<>( Arrays.asList("Coding", "Testing", "Debugging")),
//                "Active", "+55123456789", "joao.silva@example.com","123 Main Street, City, Country", "Full-stack Developer");
//
//        when(userUseCase.findAllDTO(exampleUser)).thenReturn(userList);
//        List<UserDTO> result = userService.findAllDTO(exampleUser).getBody();
//        assertEquals(2, result.size());


        List<User> userListFull = new ArrayList<>();
        userListFull.add(new User("1", "John", 30, "Pastors","Pastor",new ArrayList<>( Arrays.asList("Coding", "Testing", "Debugging")),"Active","+55123456789", "joao.silva@example.com","123 Main Street, City, Country","Full-stack Developer"));
        userListFull.add(new User("2", "Maria", 25, "Teachers", "Teacher", new ArrayList<>(Arrays.asList("Teaching", "Planning", "Grading")), "Active", "+55123456788", "maria.rodrigues@example.com", "456 Elm Street, City, Country", "Math Teacher"));

        when(userUseCase.findAll()).thenReturn(userListFull);
        List<User> res = userService.findAll();
       // assertEquals(2, result.size());
    }

    @Test
    void testFindById() {
        User user = new User("1", "John", "john@example.com", "+123456789");

        when(userUseCase.findById("1")).thenReturn(user);

        User result = userService.findById("1");

        assertEquals("John", result.getName());
    }

    @Test
    void testInsert() {
        User user = new User("1", "John", "john@example.com", "+123456789");

        when(userUseCase.insert(user)).thenReturn(user);

        User result = userService.insert(user);

        assertEquals("John", result.getName());
    }

    @Test
    void testDelete() {
        String userId = "1";

        assertDoesNotThrow(() -> {
            userService.delete(userId);
        });

        verify(userUseCase, times(1)).deleteById(userId);
    }

    @Test
    void testUpdate() {
        User user = new User("1", "John", "john@example.com", "+123456789");

        when(userUseCase.update(user)).thenReturn(user);

        User result = userService.update(user);

        assertEquals("John", result.getName());
    }

    @Test
    void testFromDTO() {
        // Criar um UserDTO para teste
        UserDTO userDTO = new UserDTO();
        userDTO.setId("1");
        userDTO.setName("John");
        userDTO.setEmail("john@example.com");
        userDTO.setPhone("+123456789");

        // Converter UserDTO para User
        User result = userService.fromDTO(userDTO);

        assertEquals("John", result.getName());
        assertEquals("john@example.com", result.getEmail());
        assertEquals("+123456789", result.getPhone());
    }
}