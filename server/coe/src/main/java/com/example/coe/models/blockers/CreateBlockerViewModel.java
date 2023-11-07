package com.example.coe.models.blockers;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateBlockerViewModel {
    @Positive
    private int userId;
    @NotNull
    @Size(min = 5, max = 100)
    private String title;
    @NotNull
    @Size(min = 5, max = 1000)
    private String description;
    @Positive
    private int blockerTypeId;
}
