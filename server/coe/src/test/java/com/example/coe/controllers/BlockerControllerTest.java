package com.example.coe.controllers;

import com.example.coe.entities.Blocker;
import com.example.coe.exception.NotFoundException;
import com.example.coe.models.blockers.BlockerDetailViewModel;
import com.example.coe.models.blockers.BlockerViewModel;
import com.example.coe.repositories.BlockerRepository;
import com.example.coe.repositories.BlockerTypeRepository;
import com.example.coe.utils.mapper.Mapper;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BlockerControllerTest {
    @Mock
    private BlockerRepository blockerRepository;

    @Mock
    private BlockerTypeRepository blockerTypeRepository;

    @Mock
    private Mapper mapper;

    @InjectMocks
    private BlockerController blockerController;


    @Test
    void getAllBlockers_whenCalled_retrieveAllBlockers(){

        // Arrange: Set up test data
        var blockers = Stream.of(new Blocker()).collect(Collectors.toList());
        var blockerViewModels = Stream.of(new BlockerViewModel()).collect(Collectors.toList());

        when(blockerRepository.findAll()).thenReturn(blockers);
        when(mapper.map(blockers, BlockerViewModel.class)).thenReturn(blockerViewModels);

        // Act: The method we are calling
        var result = blockerController.getAllBlockers();

        // Assert: Verify repository was called once and the mapper was called once
        verify(blockerRepository, times(1)).findAll();
        verify(mapper, times(1)).map(blockers, BlockerViewModel.class);

        assertThat(result.getBody()).isEqualTo(blockerViewModels);
    }

    @Test
    void getBlocker_whenCalledWithValidId_retrieveBlocker() throws Exception {

        // Arrange: Set up test data
        var blocker = new Blocker();
        var blockerDetailViewModel = new BlockerDetailViewModel();

        when(blockerRepository.findById(1)).thenReturn(Optional.of(blocker));
        when(mapper.map(blocker, BlockerDetailViewModel.class)).thenReturn(blockerDetailViewModel);

        // Act: The method we are calling
        var result = blockerController.getBlocker(1);

        // Assert: Verify repository was called once and the mapper was called once
        verify(blockerRepository, times(1)).findById(1);
        verify(mapper, times(1)).map(blocker, BlockerDetailViewModel.class);

        assertThat(result.getBody()).isEqualTo(blockerDetailViewModel);
    }

    @Test
    void getBlocker_whenCalledWithInvalidId_throwsNotFoundException() {

        // Arrange
        when(blockerRepository.findById(any())).thenReturn(Optional.empty());

        // Act & Assert
        ThrowableAssert.ThrowingCallable throwingCallable = () -> blockerController.getBlocker(1);
        assertThatThrownBy(throwingCallable).isInstanceOf(NotFoundException.class).hasMessageContaining("Blocker not found");

    }

}
