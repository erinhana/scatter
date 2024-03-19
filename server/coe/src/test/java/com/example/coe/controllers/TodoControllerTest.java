package com.example.coe.controllers;

import com.example.coe.entities.Todo;
import com.example.coe.entities.User;
import com.example.coe.exception.NotFoundException;
import com.example.coe.models.todos.CreateTodoViewModel;
import com.example.coe.models.todos.TodoDetailViewModel;
import com.example.coe.models.todos.TodoViewModel;
import com.example.coe.models.todos.UpdateTodoViewModel;
import com.example.coe.repositories.TodoRepository;
import com.example.coe.repositories.UserRepository;
import com.example.coe.utils.mapper.Mapper;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TodoControllerTest {

    @Mock
    private TodoRepository todoRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private Mapper mapper;
    @InjectMocks
    private TodoController todoController;

    @Test
    void getAllTodos_whenCalled_retrievesAllTodos() {

        //Arrange: Set up test data
        var todos = Stream.of(new Todo()).collect(Collectors.toList());
        var todoViewModels = Stream.of(new TodoViewModel()).collect(Collectors.toList());

        when(todoRepository.findAll()).thenReturn(todos);
        when(mapper.map(todos, TodoViewModel.class)).thenReturn(todoViewModels);

        // Act: The method we are calling
        var result = todoController.getAllTodos(Optional.empty());

        // Assert: Verify repository was called once and the mapper was called once
        verify(todoRepository, times(1)).findAll();
        verify(mapper, times(1)).map(todos, TodoViewModel.class);

        assertThat(result.getBody()).isEqualTo(todoViewModels);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void getTodo_whenCalledWithValidId_retrieveTodo() throws Exception {

        // Arrange: Set up test data
        var todo = new Todo();
        var todoDetailViewModel = new TodoDetailViewModel();

        when(todoRepository.findById(1)).thenReturn(Optional.of(todo));
        when(mapper.map(todo, TodoDetailViewModel.class)).thenReturn(todoDetailViewModel);

        // Act: The method we are calling
        var result = todoController.getTodo(1);

        // Assert: Verify repository was called once and the mapper was called once
        verify(todoRepository, times(1)).findById(1);
        verify(mapper, times(1)).map(todo, TodoDetailViewModel.class);

        assertThat(result.getBody()).isEqualTo(todoDetailViewModel);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void getTodo_whenCalledWithInvalidId_throwsNotFoundException() {

        //Arrange: Set up test data
        when(todoRepository.findById(any())).thenReturn(Optional.empty());

        // Act & Assert
        ThrowableAssert.ThrowingCallable throwingCallable = () -> todoController.getTodo(1);
        assertThatThrownBy(throwingCallable).isInstanceOf(NotFoundException.class).hasMessageContaining("Todo not found");
    }

    @Test
    void createTodo_whenCalledWithValidDetails_addsTodoToRepository() {

        // Arrange
        var createTodoViewModel = new CreateTodoViewModel();
        var todo = new Todo();
        var createdTodo = new Todo();
        var todoViewModel = new TodoViewModel();
        var user = new User();

        when(userRepository.findById(any())).thenReturn(Optional.of(user));
        when(mapper.map(createTodoViewModel, Todo.class)).thenReturn(todo);
        when(todoRepository.save(todo)).thenReturn(createdTodo);
        when(mapper.map(createdTodo, TodoViewModel.class)).thenReturn(todoViewModel);

        // Act
        var result = todoController.createTodo(createTodoViewModel);

        // Assert
        verify(mapper, times(1)).map(createTodoViewModel, Todo.class);
        verify(todoRepository, times(1)).save(todo);
        verify(mapper, times(1)).map(createdTodo, TodoViewModel.class);

        assertThat(result.getBody()).isEqualTo(todoViewModel);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    void updateTodo_whenCalledWithValidDetails_returnsNoContent() {

        // Arrange
        var updateTodoViewModel = new UpdateTodoViewModel();
        var todo = new Todo();

        when(todoRepository.findById(1)).thenReturn(Optional.of(todo));

        // Act
        var result = todoController.updateTodo(1, updateTodoViewModel);

        // Assert
        verify(todoRepository, times(1)).findById(1);
        verify(mapper, times(1)).map(updateTodoViewModel, todo);
        verify(todoRepository, times(1)).save(todo);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    void updateTodo_whenCalledWithInValidDetails_throwsNotFoundException() {

        // Arrange
        var updateTodoViewModel = new UpdateTodoViewModel();

        when(todoRepository.findById(any())).thenReturn(Optional.empty());

        // Act & Assert
        ThrowableAssert.ThrowingCallable throwingCallable = () -> todoController.updateTodo(1, updateTodoViewModel);
        assertThatThrownBy(throwingCallable).isInstanceOf(NotFoundException.class).hasMessageContaining("Todo not found");
    }

    @Test
    void deleteTodo_whenCalledWithValidDetails_returnsNoContent() {

        // Arrange
        var todo = new Todo();

        when(todoRepository.findById(1)).thenReturn(Optional.of(todo));

        // Act
        var result = todoController.deleteTodo(1);

        // Assert
        verify(todoRepository, times(1)).findById(1);
        verify(todoRepository, times(1)).delete(todo);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    void deleteTodo_whenCalledWithInValidDetails_throwsNotFoundException() {

        // Arrange
        when(todoRepository.findById(any())).thenReturn(Optional.empty());

        // Act & Assert
        ThrowableAssert.ThrowingCallable throwingCallable = () -> todoController.deleteTodo(1);
        assertThatThrownBy(throwingCallable).isInstanceOf(NotFoundException.class).hasMessageContaining("No todo exists with Id");
    }

}
