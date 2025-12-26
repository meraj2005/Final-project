package ir.maktabsharif.test_app.service.impl;

import ir.maktabsharif.test_app.dto.CourseCreateRequest;
import ir.maktabsharif.test_app.dto.CourseParticipantsResponse;
import ir.maktabsharif.test_app.dto.CourseResponse;
import ir.maktabsharif.test_app.exceptions.BusinessException;
import ir.maktabsharif.test_app.mapper.CourseMapper;
import ir.maktabsharif.test_app.mapper.UserMapper;
import ir.maktabsharif.test_app.model.Course;
import ir.maktabsharif.test_app.model.User;
import ir.maktabsharif.test_app.repository.CourseRepository;
import ir.maktabsharif.test_app.repository.UserRepository;
import ir.maktabsharif.test_app.security.SecurityUtil;
import ir.maktabsharif.test_app.service.CourseService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl extends BaseServiceImpl<Course, Long> implements CourseService {
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final SecurityUtil securityUtil;
    public CourseServiceImpl(CourseRepository courseRepository, UserRepository userRepository, SecurityUtil securityUtil) {
        super(courseRepository);
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
        this.securityUtil = securityUtil;
    }
    @Override
    public Optional<Course> findByCourseCode(String courseCode) {
        return courseRepository.findByCourseCode(courseCode);
    }
    @Override
    public CourseResponse create(CourseCreateRequest request) {

        if (findByCourseCode(request.getCourseCode()).isPresent()) {
            throw new BusinessException("This course code has already been used.");
        }

        Course course = CourseMapper.toEntity(request);
        course.setTeacher(userRepository.findById(request.getTeacherId()).orElseThrow());
        courseRepository.save(course);
        return CourseMapper.toDTO(course);
    }

    @Override
    public void assignTeacher(Long courseId, Long teacherId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new BusinessException("CourseID not find.")) ;
        User user = userRepository.findById(teacherId)
                .orElseThrow(() -> new BusinessException("teacherId not find."));
        course.setTeacher(user);
        courseRepository.save(course);
    }

    @Override
    public void addStudent(Long courseId, Long studentId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new BusinessException("CourseID not find.")) ;
        User user = userRepository.findById(studentId)
                .orElseThrow(() -> new BusinessException("studentId not find."));
        course.addStudents(user);
        courseRepository.save(course);
    }

    @Override
    public CourseParticipantsResponse getParticipants(Long courseId) {
        Course course=courseRepository.findById(courseId)
                .orElseThrow(() -> new BusinessException("CourseID not find.")) ;
        CourseParticipantsResponse response = new CourseParticipantsResponse();
        response.setCourseCode(course.getCourseCode());
        response.setCourseTitle(course.getTitle());
        response.setStudents(course.getStudents().stream().map(UserMapper::toDTO).collect(Collectors.toSet()));
        response.setTeacher(UserMapper.toDTO(course.getTeacher()));
        return response;
    }


    @Override
    public List<CourseResponse> getMyTeachingCourses() {
        User teacher = securityUtil.getCurrentUser();

        return courseRepository.findByTeacher(teacher)
                .stream()
                .map(CourseMapper::toDTO)
                .toList();
    }
}
