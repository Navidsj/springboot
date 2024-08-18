package com.example.restapi;

import com.example.restapi.model.User;
import com.example.restapi.repository.UserRepository;
import com.example.restapi.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.awaitility.Awaitility.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DataJpaTest
public class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserService userService;

    User user = new User();

    @BeforeEach
    void setUp() {
        user.setId(1);
        user.setName("name");
        user.setEmail("email");
        user.setAge(20);

    }

    @Test
    public void checkGetUser(){
        Optional<User> userOptional = Optional.of(user);

        when(userRepository.findById((long) user.getId())).thenReturn(userOptional);

        User dbUser  = userService.getUser(user.getId());
        assertThat(dbUser).isEqualTo(user);
    }

    @Test
    public void checkAddUserWhenItNotExists(){
        // Give

        ArrayList<User> users = new ArrayList<>();

        when(userRepository.findAll()).thenReturn(users);
        // When
        String answer = userService.addUser(user);

        // Then
        verify(userRepository, Mockito.times(1)).save(user);
        assertThat(answer).isEqualTo("User added");

    }

    @Test
    public void checkAddUserWhenItExists(){
        // Give
        ArrayList<User> users = new ArrayList<>();
        users.add(user);

        when(userRepository.findAll()).thenReturn(users);
        // When
        String answer = userService.addUser(user);

        // Then
        assertThat(answer).isEqualTo("User already exists");

    }

    @Test
    void checkDeleteUser(){
        String answer = userService.deleteUser(user.getId());

        verify(userRepository, Mockito.times(1)).deleteById((long) user.getId());

        assertThat(answer).isEqualTo("User deleted");

    }




}
