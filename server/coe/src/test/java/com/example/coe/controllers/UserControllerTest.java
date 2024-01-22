package com.example.coe.controllers;

import com.example.coe.entities.Todo;
import com.example.coe.entities.User;
import com.example.coe.exception.NotFoundException;
import com.example.coe.models.todos.TodoViewModel;
import com.example.coe.models.users.CreateUserViewModel;
import com.example.coe.models.users.UpdateUserViewModel;
import com.example.coe.models.users.UserDetailViewModel;
import com.example.coe.models.users.UserViewModel;
import com.example.coe.repositories.TodoRepository;
import com.example.coe.repositories.UserRepository;
import com.example.coe.utils.mapper.Mapper;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Captor
    ArgumentCaptor<User> userArgumentCaptor;
    @Mock
    private UserRepository userRepository;
    @Mock
    private TodoRepository todoRepository;
    @Mock
    private Mapper mapper;
    @InjectMocks
    private UserController userController;
    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    void getAllUsers_whenCalled_retrievesAllUsers() {

        //Arrange: Set up test data
        var users = Stream.of(new User()).collect(Collectors.toList());
        var userViewModels = Stream.of(new UserViewModel()).collect(Collectors.toList());

        when(userRepository.findAll()).thenReturn(users);
        when(mapper.map(users, UserViewModel.class)).thenReturn(userViewModels);

        // Act: The method we are calling
        var result = userController.getAllUsers();

        // Assert: Verify repository was called once and the mapper was called once
        verify(userRepository, times(1)).findAll();
        verify(mapper, times(1)).map(users, UserViewModel.class);

        assertThat(result.getBody()).isEqualTo(userViewModels);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void getUser_whenCalledWithValidId_retrieveUser() throws Exception {

        // Arrange: Set up test data
        var user = new User();
        var userDetailViewModel = new UserDetailViewModel();

        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(mapper.map(user, UserDetailViewModel.class)).thenReturn(userDetailViewModel);

        // Act: The method we are calling
        var result = userController.getUser(1);

        // Assert: Verify repository was called once and the mapper was called once
        verify(userRepository, times(1)).findById(1);
        verify(mapper, times(1)).map(user, UserDetailViewModel.class);

        assertThat(result.getBody()).isEqualTo(userDetailViewModel);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);

    }

    @Test
    void getUser_whenCalledWithInvalidId_throwsNotFoundException() {

        //Arrange: Set up test data
        when(userRepository.findById(any())).thenReturn(Optional.empty());

        // Act & Assert
        ThrowableAssert.ThrowingCallable throwingCallable = () -> userController.getUser(1);
        assertThatThrownBy(throwingCallable).isInstanceOf(NotFoundException.class).hasMessageContaining("User not found");
    }

    @Test
    void createUser_whenCalledWithValidDetails_addsUserToRepository() {

        //Arrange: Set up test data
        var createUserViewModel = new CreateUserViewModel();
        var user = new User();
        var createdUser = new User();
        var userViewModel = new UserViewModel();

        when(mapper.map(createUserViewModel, User.class)).thenReturn(user);
        when(passwordEncoder.encode(createUserViewModel.getPassword())).thenReturn("1234");
        when(userRepository.save(user)).thenReturn(createdUser);
        when(mapper.map(createdUser, UserViewModel.class)).thenReturn(userViewModel);

        //Act
        var result = userController.createUser(createUserViewModel);

        //Assert
        verify(mapper, times(1)).map(createUserViewModel, User.class);
        verify(passwordEncoder, times(1)).encode(any());
        verify(userRepository, times(1)).save(user);
        verify(mapper, times(1)).map(createdUser, UserViewModel.class);

        assertThat(result.getBody()).isEqualTo(userViewModel);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        verify(userRepository).save(userArgumentCaptor.capture());
        assertThat(userArgumentCaptor.getValue().getPassword()).isEqualTo("1234");
    }

    @Test
    void updateUser_whenCalledWithValidDetails_returnsNoContent() {

        //Arrange: Set up test data
        var updateUserViewModel = new UpdateUserViewModel();
        var user = new User();

        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(passwordEncoder.encode(updateUserViewModel.getPassword())).thenReturn("1234");

        // Act
        var result = userController.updateUser(1, updateUserViewModel);

        // Assert
        verify(userRepository, times(1)).findById(any());
        verify(passwordEncoder, times(1)).encode(any());
        verify(mapper, times(1)).map(updateUserViewModel, user);
        verify(userRepository, times(1)).save(user);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        verify(userRepository).save(userArgumentCaptor.capture());
        assertThat(userArgumentCaptor.getValue().getPassword()).isEqualTo("1234");
    }

    @Test
    void updateUser_whenCalledWithInvalidId_throwsNotFoundException() {

        //Arrange: Set up test data
        var updateUserViewModel = new UpdateUserViewModel();

        when(userRepository.findById(any())).thenReturn(Optional.empty());

        // Act & Assert
        ThrowableAssert.ThrowingCallable throwingCallable = () -> userController.updateUser(1, updateUserViewModel);
        assertThatThrownBy(throwingCallable).isInstanceOf(NotFoundException.class).hasMessageContaining("User not found");
    }

    @Test
    void deleteUser_whenCalledWithValidId_returnsNoContent() {

        //Arrange: Set up test data
        var user = new User();

        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        // Act
        var result = userController.deleteUser(1);

        // Assert
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    void deleteUser_whenCalledWithInvalidId_throwsNotFoundException() {

        //Arrange: Set up test data
        when(userRepository.findById(1)).thenReturn(Optional.empty());

        // Act & Assert
        ThrowableAssert.ThrowingCallable throwingCallable = () -> userController.deleteUser(1);
        assertThatThrownBy(throwingCallable).isInstanceOf(NotFoundException.class).hasMessageContaining("User not found");
    }

    @Test
    void getAllTodosForUser_whenCalledWithValidId_retrievesAllTodosForUser() {

        //Arrange: Set up test data
        var todos = Stream.of(new Todo()).collect(Collectors.toList());
        var todoViewModels = Stream.of(new TodoViewModel()).collect(Collectors.toList());

        when(todoRepository.findByUserId(1)).thenReturn((todos));
        when(mapper.map(todos, TodoViewModel.class)).thenReturn(todoViewModels);

        //Act
        var result = userController.getAllTodosForUser(1);

        //Assert
        verify(todoRepository, times(1)).findByUserId(1);
        verify(mapper, times(1)).map(todos, TodoViewModel.class);

        assertThat(result.getBody()).isEqualTo(todoViewModels);

    }


}
