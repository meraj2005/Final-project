package ir.maktabsharif.test_app.controllers;

import ir.maktabsharif.test_app.dto.CourseResponse;
import ir.maktabsharif.test_app.service.CourseService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teacher/courses")
@PreAuthorize("hasRole('TEACHER')")
public class TeacherCourseController {

    private final CourseService courseService;

    public TeacherCourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public ResponseEntity<List<CourseResponse>> myCourses() {
        return ResponseEntity.ok(courseService.getMyTeachingCourses());
    }
}
