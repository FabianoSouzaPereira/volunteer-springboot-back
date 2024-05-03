package com.fabianospdev.volunteer.usecases.user;

import com.fabianospdev.volunteer.models.User;
import com.fabianospdev.volunteer.repositories.UserRepository;
import com.fabianospdev.volunteer.services.exception.ObjectNotExistsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


class UserUseCaseTest{

    @Mock
    private KafkaTemplate<String, String> kafkaTemplate;

    @Mock
    private UserRepository userRepository;

    @Mock
    private MessageSource messageSource;

    @InjectMocks
    private UserUseCase userUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testFindAll() {
        List<User> userList = new ArrayList<>();

        userList.add(new User("1", "John", 30, "Pastors", "Pastor", new ArrayList<>(Arrays.asList("Coding", "Testing", "Debugging")), "Active", "+55123456789", "joao.silva@example.com", "123 Main Street, City, Country", "Full-stack Developer"));
        userList.add(new User("2", "Maria", 25, "Teachers", "Teacher", new ArrayList<>(Arrays.asList("Teaching", "Planning", "Grading")), "Active", "+55123456788", "maria.rodrigues@example.com", "456 Elm Street, City, Country", "Math Teacher"));
        userList.add(new User("3", "Carlos", 35, "Engineers", "Engineer", new ArrayList<>(Arrays.asList("Designing", "Analyzing", "Building")), "Inactive", "+55123456787", "carlos.ferreira@example.com", "789 Oak Street, City, Country", "Civil Engineer"));
        userList.add(new User("4", "Ana", 28, "Doctors", "Doctor", new ArrayList<>(Arrays.asList("Diagnosing", "Treating", "Caring")), "Active", "+55123456786", "ana.santos@example.com", "321 Pine Street, City, Country", "Pediatrician"));
        userList.add(new User("5", "Lucas", 40, "Lawyers", "Lawyer", new ArrayList<>(Arrays.asList("Advising", "Litigating", "Negotiating")), "Active", "+55123456785", "lucas.oliveira@example.com", "654 Birch Street, City, Country", "Corporate Lawyer"));
        userList.add(new User("6", "Juliana", 27, "Designers", "Designer", new ArrayList<>(Arrays.asList("Sketching", "Prototyping", "Presenting")), "Inactive", "+55123456784", "juliana.almeida@example.com", "987 Maple Street, City, Country", "Graphic Designer"));
        userList.add(new User("7", "Ricardo", 33, "Scientists", "Scientist", new ArrayList<>(Arrays.asList("Researching", "Experimenting", "Publishing")), "Active", "+55123456783", "ricardo.silveira@example.com", "147 Walnut Street, City, Country", "Biologist"));
        userList.add(new User("8", "Camila", 31, "Artists", "Artist", new ArrayList<>(Arrays.asList("Painting", "Sculpting", "Exhibiting")), "Inactive", "+55123456782", "camila.rocha@example.com", "369 Cedar Street, City, Country", "Painter"));
        userList.add(new User("9", "Gabriel", 29, "Musicians", "Musician", new ArrayList<>(Arrays.asList("Composing", "Performing", "Recording")), "Active", "+55123456781", "gabriel.pereira@example.com", "753 Spruce Street, City, Country", "Pianist"));
        userList.add(new User("10", "Laura", 26, "Writers", "Writer", new ArrayList<>(Arrays.asList("Writing", "Editing", "Publishing")), "Active", "+55123456780", "laura.fernandes@example.com", "159 Pine Street, City, Country", "Author"));

        when(userRepository.findAll()).thenReturn(userList);

        List<User> result = userUseCase.findAll();

        assertEquals(10, result.size());
    }

    @Test
    void testFindById() {
        User user = new User("1", "John", 30, "Pastors", "Pastor", new ArrayList<>(Arrays.asList("Coding", "Testing", "Debugging")), "Active", "+55123456789", "joao.silva@example.com", "123 Main Street, City, Country", "Full-stack Developer");

        when(userRepository.findById("1")).thenReturn(Optional.of(user));

        User result = userUseCase.findById("1");

        assertEquals("John", result.getName());
    }

//    @Test
//    void testInsert() {
//        User user = new User("1", "John", 30, "Pastors", "Pastor", new ArrayList<>(Arrays.asList("Coding", "Testing", "Debugging")), "Active", "+55123456789", "joao.silva@example.com", "123 Main Street, City, Country", "Full-stack Developer");
//
//        when(userRepository.insert(user)).thenReturn(user);
//
//        User result = userUseCase.insert(user);
//
//        assertEquals("John", result.getName());
//
//        verify(kafkaTemplate).send(eq("user-registration-events"), eq("Created new register: John"));
//    }

    @Test
    void testUpdate() {
        User user = new User("1", "John", 30, "Pastors", "Pastor", new ArrayList<>(Arrays.asList("Coding", "Testing", "Debugging")), "Active", "+55123456789", "joao.silva@example.com", "123 Main Street, City, Country", "Full-stack Developer");

        when(userRepository.findById("1")).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);

        User updatedUser = new User("1", "Updated John", 30, "Pastors", "Pastor", new ArrayList<>(Arrays.asList("Coding", "Testing", "Debugging")), "Active", "+55123456789", "joao.silva@example.com", "123 Main Street, City, Country", "Full-stack Developer");

        user = update(updatedUser);

        User result = userUseCase.update(user);

        assertEquals("Updated John", result.getName());
    }

    @Test
    void testUpdateWhenUserNotExists() {
        User user = new User("1", "John", 30, "Pastors", "Pastor", new ArrayList<>(Arrays.asList("Coding", "Testing", "Debugging")), "Active", "+55123456789", "joao.silva@example.com", "123 Main Street, City, Country", "Full-stack Developer");

        when(userRepository.findById("1")).thenReturn(Optional.empty());

        assertThrows(ObjectNotExistsException.class, () -> {
            userUseCase.update(user);
        });
    }

    @Test
    void testDeleteById() {
        // No need to mock userRepository.deleteById(), as it's a void method

        assertDoesNotThrow(() -> {
            userUseCase.deleteById("1");
        });
    }

    @Test
    void testFindAllDTO() {
        // Test findAllDTO method separately
    }

    // Add more test methods as needed

    private User update(User updatedUser) {
        Optional<User> optionalUser = userRepository.findById(updatedUser.getId());

        if(optionalUser.isEmpty()) {
            String message = messageSource.getMessage("object.not.exists", new Object[]{updatedUser.getId()}, LocaleContextHolder.getLocale());
            throw new ObjectNotExistsException(message);
        }

        User existingUser = optionalUser.get();

        existingUser.setName(updatedUser.getName());

        return userRepository.save(existingUser);
    }
}