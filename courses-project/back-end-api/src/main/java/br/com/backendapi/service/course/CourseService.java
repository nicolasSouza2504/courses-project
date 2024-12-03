package br.com.backendapi.service.course;

import br.com.backendapi.domain.course.Course;
import br.com.backendapi.domain.course.CourseExibitionData;
import br.com.backendapi.domain.course.CourseRegisterDTO;
import br.com.backendapi.domain.course.CourseResponseData;
import br.com.backendapi.domain.subscribe.Subscribe;
import br.com.backendapi.domain.subscribe.RegisterSubscriptionDTO;
import br.com.backendapi.domain.user.User;
import br.com.backendapi.exception.Validation;
import br.com.backendapi.repository.ICourseRepository;
import br.com.backendapi.repository.ISubscriptionRepository;
import br.com.backendapi.repository.IUserRepository;
import io.micrometer.common.util.StringUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.locks.ReentrantLock;

@Service
@RequiredArgsConstructor
public class CourseService implements ICourseService {

    private final ICourseRepository courseRepository;
    private final ReentrantLock lock = new ReentrantLock();
    private final IUserRepository userRepository;
    private final ISubscriptionRepository subscriptionRepository;

    @Override
    public CourseResponseData create(CourseRegisterDTO courseRegisterDTO) {
        validateCourseNotExists(courseRegisterDTO);
        validateCourseFields(courseRegisterDTO);

        Course course = buildCourse(courseRegisterDTO);
        courseRepository.save(course);

        return new CourseResponseData(course.getId(), "Curso criado com sucesso");
    }

    @Override
    public List<CourseExibitionData> getAll() {
        return courseRepository.findAllExibition();
    }

    @Override
    public void subscribe(RegisterSubscriptionDTO registerSubscriptionDTO) {

        lock.lock();

        try {

            validateSubscriptionFields(registerSubscriptionDTO);

            Course course = getCourseById(registerSubscriptionDTO.idCourse());
            User user = getUserByCpf(registerSubscriptionDTO.cpf());

            validateSubscriptionAvailability(course, user);

            createSubscription(course, user);

        } catch (Throwable t) {
            throw t;
        } finally {
            lock.unlock();
        }

    }

    @Transactional
    public void createSubscription(Course course, User user) {
        subscriptionRepository.save(new Subscribe(course, user));
    }

    private void validateCourseNotExists(CourseRegisterDTO courseRegisterDTO) {
        boolean courseExists = courseRepository.existsCourseByName(courseRegisterDTO.name());
        if (courseExists) {
            throw new Validation().add("name", "Curso já cadastrado");
        }
    }

    private void validateCourseFields(CourseRegisterDTO courseRegisterDTO) {
        Validation validation = new Validation();

        if (StringUtils.isEmpty(courseRegisterDTO.name())) {
            validation.add("name", "Nome do curso é obrigatório");
        }

        if (courseRegisterDTO.avaiableSubscribes() == null || courseRegisterDTO.avaiableSubscribes() < 0) {
            validation.add("avaiableSubscribes", "Número de vagas inválido");
        }

        validation.throwIfHasErrors();
    }

    private void validateSubscriptionFields(RegisterSubscriptionDTO registerSubscriptionDTO) {

        Validation validation = new Validation();

        if (StringUtils.isEmpty(registerSubscriptionDTO.cpf())) {
            validation.add("cpf", "Informe o CPF do usuário para inscrição");
        }

        if (registerSubscriptionDTO.idCourse() == null) {
            validation.add("idCourse", "Informe o curso para inscrição");
        }

        validation.throwIfHasErrors();
    }

    private Course buildCourse(CourseRegisterDTO courseRegisterDTO) {
        return new Course(courseRegisterDTO.name(), courseRegisterDTO.avaiableSubscribes());
    }

    private Course getCourseById(Long courseId) {

        return courseRepository.findById(courseId)
                .orElseThrow(() -> new Validation().add("idCourse", "Curso não encontrado"));

    }

    private User getUserByCpf(String cpf) {

        String sanitizedCpf = cpf.replaceAll("[^0-9]", "");

        return Optional.ofNullable(userRepository.findByCpf(sanitizedCpf))
                .orElseThrow(() -> new Validation().add("cpf", "Usuário não encontrado"));

    }

    private void validateSubscriptionAvailability(Course course, User user) {

        if (subscriptionRepository.existsByIdUserAndIdCourse(user.getId(), course.getId())) {
            throw new Validation().add("usuario", "Usuário já inscrito no curso");
        }

        if (!courseRepository.avaiableForSubscription(course.getId())) {
            throw new Validation().add("curso", "Curso sem vagas disponíveis");
        }

    }

}
