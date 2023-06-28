package com.example.coe.models.blockers;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateBlockerViewModel {
    @NotBlank
    private int userId;
    private String title;
    private String description;
    private int blockerTypeId;
}
