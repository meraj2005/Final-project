package ir.maktabsharif.test_app.repository;

import ir.maktabsharif.test_app.model.Course;
import ir.maktabsharif.test_app.model.User;

import java.util.List;
import java.util.Optional;

public interface CourseRepository extends BaseRepository<Long, Course> {
    Optional<Course> findByCourseCode(String courseCode);
    List<Course> findByTeacher(User teacher);
}
