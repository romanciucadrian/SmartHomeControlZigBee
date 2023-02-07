package com.quest.global.SmartHome.test;


import com.quest.global.SmartHome.models.User;
import com.quest.global.SmartHome.repositories.UserRepository;
import com.quest.global.SmartHome.services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    public void testGETUsers() {
        //Given
        User u1 = new User();
        User u2 = new User();

        List<User> users = List.of(u1, u2);

        //When
        when(userRepository.findAll()).thenReturn(users);

        //Then
        List<User>  actualResult = userService.getAll();
        assertEquals(users, actualResult);
    }
}
