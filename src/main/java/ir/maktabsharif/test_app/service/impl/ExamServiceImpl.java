package ir.maktabsharif.test_app.service.impl;

import ir.maktabsharif.test_app.dto.CourseResponse;
import ir.maktabsharif.test_app.dto.exam.ExamCreateRequest;
import ir.maktabsharif.test_app.dto.exam.ExamResponse;
import ir.maktabsharif.test_app.dto.exam.ExamUpdateRequest;
import ir.maktabsharif.test_app.exceptions.BusinessException;
import ir.maktabsharif.test_app.mapper.ExamMapper;
import ir.maktabsharif.test_app.model.Course;
import ir.maktabsharif.test_app.model.Exam;
import ir.maktabsharif.test_app.model.User;
import ir.maktabsharif.test_app.model.enums.EventStatus;
import ir.maktabsharif.test_app.repository.CourseRepository;
import ir.maktabsharif.test_app.repository.ExamRepository;
import ir.maktabsharif.test_app.security.SecurityUtil;
import ir.maktabsharif.test_app.service.ExamService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ExamServiceImpl extends BaseServiceImpl<Exam,Long> implements ExamService {

    private final CourseRepository courseRepository;
    private final ExamRepository examRepository;
    private final SecurityUtil securityUtil;
    public ExamServiceImpl(CourseRepository courseRepository,
                           ExamRepository examRepository, SecurityUtil securityUtil) {
        super(examRepository);
        this.courseRepository = courseRepository;
        this.examRepository = examRepository;
        this.securityUtil = securityUtil;
    }

    @Override
    public List<CourseResponse> getMyTeachingCourses() {
        return List.of();
    }

    @Override
    public List<ExamResponse> getCourseExams(Long courseId) {
        Course course = getOwnedCourse(courseId);

        return examRepository.findByCourse(course)
                .stream()
                .map(ExamMapper::toResponse)
                .toList();
    }


    @Override
    public ExamResponse create(Long courseId, ExamCreateRequest request) {
        Course course = getOwnedCourse(courseId);

        Exam exam = ExamMapper.toEntity(request);
        exam.setCourse(course);
        exam.setEventStatus(EventStatus.ESTABLISHED);
        examRepository.save(exam);
        return ExamMapper.toResponse(exam);
    }

    @Override
    public ExamResponse update(Long courseId, Long examId, ExamUpdateRequest request) {
        Course course = getOwnedCourse(courseId);

        Exam exam = examRepository.findByIdAndCourse(examId, course)
                .orElseThrow(() -> new BusinessException("Exam not found"));

        exam.setTitle(request.getTitle());
        exam.setDescription(request.getDescription());
        exam.setDurationMinutes(request.getDurationMinutes());

        return ExamMapper.toResponse(exam);
    }

    @Override
    public void delete(Long courseId, Long examId) {
        Course course = getOwnedCourse(courseId);

        Exam exam = examRepository.findByIdAndCourse(examId, course)
                .orElseThrow(() -> new BusinessException("Exam not found"));

        examRepository.delete(exam);
    }


    private Course getOwnedCourse(Long courseId) {
        User teacher = securityUtil.getCurrentUser();

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new BusinessException("Course not found"));

        if (!course.getTeacher().getId().equals(teacher.getId())) {
            throw new BusinessException("Access denied to this course");
        }

        return course;
    }
}
