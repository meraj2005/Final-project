package ir.maktabsharif.test_app.service;

import ir.maktabsharif.test_app.dto.CourseCreateRequest;
import ir.maktabsharif.test_app.dto.CourseParticipantsResponse;
import ir.maktabsharif.test_app.dto.CourseResponse;
import ir.maktabsharif.test_app.model.Course;
import ir.maktabsharif.test_app.model.User;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;

public interface CourseService extends BaseService<Course, Long>{
    Optional<Course> findByCourseCode(String courseCode);
    CourseResponse create(CourseCreateRequest request);
    void assignTeacher(Long courseId, Long teacherId);
    void addStudent(Long courseId, Long studentId);
    CourseParticipantsResponse getParticipants(Long courseId);
    //Part B
    List<CourseResponse> getMyTeachingCourses();
}
