package com.example.coe.models.blockers;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BlockerViewModel {

    private int id;
    private int userId;
    private String title;
    private String description;
    private int blockerTypeId;


}
