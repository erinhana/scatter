package com.example.coe.models.blockers;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateBlockerViewModel {
    @Min(1)
    private int userId;
    @NotBlank
    private String title;
    @NotBlank
    private String description;
    @Min(1)
    private int blockerTypeId;
}
