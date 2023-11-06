package com.example.coe.models.blockers;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateBlockerViewModel {
    @NotNull
    private int userId;
    private String title;
    private String description;
    private int blockerTypeId;
}
