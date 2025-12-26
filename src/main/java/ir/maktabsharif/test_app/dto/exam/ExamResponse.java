package ir.maktabsharif.test_app.dto.exam;

import ir.maktabsharif.test_app.model.Course;
import ir.maktabsharif.test_app.model.enums.EventStatus;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ExamResponse {
    private String examCode;
    private String title;
    private String description;
    private Integer durationMinutes;
    private Course course;
    private EventStatus eventStatus;
}
