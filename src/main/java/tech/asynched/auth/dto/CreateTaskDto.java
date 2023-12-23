package tech.asynched.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateTaskDto(
                @NotBlank() @Size(min = 1, max = 255) String content) {

}
