package com.example.coe.integration.responses;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class ErrorResponse {

    // status = response code
    private int status;
    private String message;
    private ErrorItemResponse[] fieldErrors;
}
