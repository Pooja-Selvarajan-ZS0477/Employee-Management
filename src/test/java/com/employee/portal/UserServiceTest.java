package com.employee.portal;

import com.employee.user.UserRepository;
import com.employee.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

public class UserServiceTest {
    @InjectMocks
    private UserService userService;
    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this); // Initialize the mocks

        // Mocking behavior for userRepository.deleteById() to return true
    }


    @Test
    public void testDeleteUser() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        // Given
        int userId = 1; // Assuming user ID

        // Accessing the private method deleteUser using ReflectionUtils
        Method deleteUserMethod = UserService.class.getDeclaredMethod("deleteUser", int.class);
        deleteUserMethod.setAccessible(true);

        System.out.println("Before invoking deleteUser method...");

        // Check if userService is null (debugging purposes)
        if (userService == null) {
            System.out.println("userService is null!");
        }

        // Invoking the private method deleteUser
        boolean result = (boolean) deleteUserMethod.invoke(userService, userId);

        System.out.println("After invoking deleteUser method...");

        // Verifying the result
        assertTrue(result);
        // Optionally, you can further verify other aspects of the delete operation
    }

}
