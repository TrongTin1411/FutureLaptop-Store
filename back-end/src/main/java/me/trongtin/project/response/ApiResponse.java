package me.trongtin.project.response;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
public class ApiResponse<T> {

    private LocalDateTime timestamp = LocalDateTime.now();
    private final String message;
    private final T data;

}
