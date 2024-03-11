package com.example.coe.controllers;

import com.example.coe.entities.Activity;
import com.example.coe.exception.NotFoundException;
import com.example.coe.models.activities.ActivityDetailViewModel;
import com.example.coe.models.activities.ActivityViewModel;
import com.example.coe.models.activities.CreateActivityViewModel;
import com.example.coe.models.activities.UpdateActivityViewModel;
import com.example.coe.repositories.ActivityRepository;
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
public class ActivityControllerTest {
    @Mock
    private ActivityRepository activityRepository;
    @Mock
    private Mapper mapper;
    @InjectMocks
    private ActivityController activityController;


    @Test
    void getAllActivities_whenCalled_retrievesAllActivities() {

        //Arrange: Set up test data
        var activities = Stream.of(new Activity()).collect(Collectors.toList());
        var activityViewModels = Stream.of(new ActivityViewModel()).collect(Collectors.toList());

        when(activityRepository.findAll()).thenReturn(activities);
        when(mapper.map(activities, ActivityViewModel.class)).thenReturn(activityViewModels);

        // Act: The method we are calling
        var result = activityController.getAllActivities();

        // Assert: Verify repository was called once and the mapper was called once
        verify(activityRepository, times(1)).findAll();
        verify(mapper, times(1)).map(activities, ActivityViewModel.class);

        assertThat(result.getBody()).isEqualTo(activityViewModels);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void getActivity_whenCalledWithValidId_retrieveActivity() throws Exception {

        // Arrange: Set up test data
        var activity = new Activity();
        var activityDetailViewModel = new ActivityDetailViewModel();

        when(activityRepository.findById(1)).thenReturn(Optional.of(activity));
        when(mapper.map(activity, ActivityDetailViewModel.class)).thenReturn(activityDetailViewModel);

        // Act: The method we are calling
        var result = activityController.getActivity(1);

        // Assert: Verify repository was called once and the mapper was called once
        verify(activityRepository, times(1)).findById(1);
        verify(mapper, times(1)).map(activity, ActivityDetailViewModel.class);

        assertThat(result.getBody()).isEqualTo(activityDetailViewModel);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void getActivity_whenCalledWithInvalidId_throwsNotFoundException() {

        //Arrange: Set up test data
        when(activityRepository.findById(any())).thenReturn(Optional.empty());

        // Act & Assert
        ThrowableAssert.ThrowingCallable throwingCallable = () -> activityController.getActivity(1);
        assertThatThrownBy(throwingCallable).isInstanceOf(NotFoundException.class).hasMessageContaining("Activity not found");
    }

    @Test
    void createActivity_whenCalledWithValidDetails_addsActivityToRepository() {

        // Arrange
        var createActivityViewModel = new CreateActivityViewModel();
        var activity = new Activity();
        var createdActivity = new Activity();
        var activityViewModel = new ActivityViewModel();


        when(mapper.map(createActivityViewModel, Activity.class)).thenReturn(activity);
        when(activityRepository.save(activity)).thenReturn(createdActivity);
        when(mapper.map(createdActivity, ActivityViewModel.class)).thenReturn(activityViewModel);

        // Act
        var result = activityController.createActivity(createActivityViewModel);

        // Assert
        verify(mapper, times(1)).map(createActivityViewModel, Activity.class);
        verify(activityRepository, times(1)).save(activity);
        verify(mapper, times(1)).map(createdActivity, ActivityViewModel.class);

        assertThat(result.getBody()).isEqualTo(activityViewModel);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    void updateActivity_whenCalledWithValidDetails_returnsNoContent() {

        // Arrange
        var updateActivityViewModel = new UpdateActivityViewModel();
        var activity = new Activity();

        when(activityRepository.findById(1)).thenReturn(Optional.of(activity));

        // Act
        var result = activityController.updateActivity(1, updateActivityViewModel);

        // Assert
        verify(activityRepository, times(1)).findById(1);
        verify(mapper, times(1)).map(updateActivityViewModel, activity);
        verify(activityRepository, times(1)).save(activity);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    void updateActivity_whenCalledWithInValidDetails_throwsNotFoundException() {

        // Arrange
        var updateActivityViewModel = new UpdateActivityViewModel();

        when(activityRepository.findById(any())).thenReturn(Optional.empty());

        // Act & Assert
        ThrowableAssert.ThrowingCallable throwingCallable = () -> activityController.updateActivity(1, updateActivityViewModel);
        assertThatThrownBy(throwingCallable).isInstanceOf(NotFoundException.class).hasMessageContaining("Activity not found");
    }

    @Test
    void deleteActivity_whenCalledWithValidDetails_returnsNoContent() {

        // Arrange
        var activity = new Activity();

        when(activityRepository.findById(1)).thenReturn(Optional.of(activity));

        // Act
        var result = activityController.deleteActivity(1);

        // Assert
        verify(activityRepository, times(1)).findById(1);
        verify(activityRepository, times(1)).delete(activity);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    void deleteActivity_whenCalledWithInValidDetails_throwsNotFoundException() {

        // Arrange
        when(activityRepository.findById(any())).thenReturn(Optional.empty());

        // Act & Assert
        ThrowableAssert.ThrowingCallable throwingCallable = () -> activityController.deleteActivity(1);
        assertThatThrownBy(throwingCallable).isInstanceOf(NotFoundException.class).hasMessageContaining("No activity exists with Id");
    }
}
