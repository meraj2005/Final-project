package ir.maktabsharif.test_app.service;

import ir.maktabsharif.test_app.dto.CourseResponse;
import ir.maktabsharif.test_app.dto.exam.ExamCreateRequest;
import ir.maktabsharif.test_app.dto.exam.ExamResponse;
import ir.maktabsharif.test_app.dto.exam.ExamUpdateRequest;
import ir.maktabsharif.test_app.model.Course;
import ir.maktabsharif.test_app.model.Exam;
import ir.maktabsharif.test_app.model.User;
import ir.maktabsharif.test_app.model.enums.EventStatus;

import java.util.List;
import java.util.Optional;

public interface ExamService extends BaseService<Exam,Long>{

    List<CourseResponse> getMyTeachingCourses();
    List<ExamResponse> getCourseExams(Long courseId);
    ExamResponse create(Long courseId, ExamCreateRequest request);
    ExamResponse update(Long courseId, Long examId, ExamUpdateRequest request);
    void delete(Long courseId, Long examId);

}
