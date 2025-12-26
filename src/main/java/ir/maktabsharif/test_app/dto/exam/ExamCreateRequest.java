package ir.maktabsharif.test_app.dto.exam;

import ir.maktabsharif.test_app.model.Course;
import ir.maktabsharif.test_app.model.enums.EventStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import javax.crypto.Mac;

@Data
public class ExamCreateRequest {
    @NotNull
    @Size(max = 10 , min = 4 )
    private String examCode;
    @NotNull(message = "The name of the desired test must not be empty.")
    private String title;
    @NotNull
    @Size(max = 1000,message = "Description must not be null and must be less than 1000 characters. " )
    private String description;
    @NotNull
    private Integer durationMinutes;


}
