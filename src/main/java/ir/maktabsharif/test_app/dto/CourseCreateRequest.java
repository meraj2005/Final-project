package ir.maktabsharif.test_app.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NonNull;

import java.time.LocalDateTime;

@Data
public class CourseCreateRequest {
    @NotNull(message = " courseCode ")
    @Size(max = 10,message = "courseCode m")
    private String courseCode;
    @NotNull
    private String title;
    @NotNull
    private LocalDateTime startTime;
    @NotNull
    private LocalDateTime endTime;
    @NotNull
    private Long teacherId;
}
