package ir.maktabsharif.test_app.dto.exam;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ExamUpdateRequest {
    @NotNull(message = "The name of the desired test must not be empty.")
    private String title;
    @NotNull
    @Size(max = 1000,message = "Description must not be null and must be less than 1000 characters. " )
    private String description;
    @NotNull
    private Integer durationMinutes;
}
