package ir.maktabsharif.test_app.repository;

import ir.maktabsharif.test_app.model.Course;
import ir.maktabsharif.test_app.model.Exam;
import ir.maktabsharif.test_app.model.User;
import ir.maktabsharif.test_app.model.enums.EventStatus;
import jakarta.validation.constraints.AssertFalse;


import java.util.List;
import java.util.Optional;

public interface ExamRepository extends BaseRepository<Long, Exam>{
    Optional<Exam> findByExamCode (String examCode);
    List<Exam> findByCourse_Teacher(User teacher);
    List<Exam> findByCourse(Course course);
    List<Exam> findByCourseAndEventStatus(Course course, EventStatus eventStatus);
    Optional<Exam> findByIdAndCourse(Long examId, Course course);
}
