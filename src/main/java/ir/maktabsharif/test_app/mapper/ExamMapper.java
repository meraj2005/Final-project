package ir.maktabsharif.test_app.mapper;

import ir.maktabsharif.test_app.dto.exam.ExamCreateRequest;
import ir.maktabsharif.test_app.dto.exam.ExamResponse;
import ir.maktabsharif.test_app.model.Exam;

public class ExamMapper {

    public static ExamResponse toResponse(Exam exam){
        return ExamResponse.builder()
                .examCode(exam.getExamCode())
                .title(exam.getTitle())
                .description(exam.getDescription())
                .durationMinutes(exam.getDurationMinutes())
                .course(exam.getCourse())
                .eventStatus(exam.getEventStatus())
                .build();
    }
    public static Exam toEntity(ExamCreateRequest examCreateRequest){
        return Exam.builder()
                .examCode(examCreateRequest.getExamCode())
                .title(examCreateRequest.getTitle())
                .description(examCreateRequest.getDescription())
                .durationMinutes(examCreateRequest.getDurationMinutes())
                .build();
    }
}
