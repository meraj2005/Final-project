package ir.maktabsharif.test_app.controllers;


import ir.maktabsharif.test_app.dto.exam.ExamCreateRequest;
import ir.maktabsharif.test_app.dto.exam.ExamResponse;
import ir.maktabsharif.test_app.dto.exam.ExamUpdateRequest;
import ir.maktabsharif.test_app.service.ExamService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teacher/courses/{courseId}/exams")
@PreAuthorize("hasRole('TEACHER')")
public class TeacherExamController {

    private final ExamService examService;

    public TeacherExamController(ExamService examService) {
        this.examService = examService;
    }

    @GetMapping
    public ResponseEntity<List<ExamResponse>> getCourseExams(
            @PathVariable Long courseId) {
        return ResponseEntity.ok(examService.getCourseExams(courseId));
    }

    @PostMapping
    public ResponseEntity<ExamResponse> createExam(
            @PathVariable Long courseId,
            @Valid @RequestBody ExamCreateRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(examService.create(courseId, request));
    }

    @PutMapping("/{examId}")
    public ResponseEntity<ExamResponse> updateExam(
            @PathVariable Long courseId,
            @PathVariable Long examId,
            @Valid @RequestBody ExamUpdateRequest request) {

        return ResponseEntity.ok(
                examService.update(courseId, examId, request)
        );
    }

    @DeleteMapping("/{examId}")
    public ResponseEntity<Void> deleteExam(
            @PathVariable Long courseId,
            @PathVariable Long examId) {

        examService.delete(courseId, examId);
        return ResponseEntity.noContent().build();
    }
}
