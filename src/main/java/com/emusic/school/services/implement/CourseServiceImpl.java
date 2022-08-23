package com.emusic.school.services.implement;

import com.emusic.school.dtos.CourseDTO;
import com.emusic.school.models.Course;
import com.emusic.school.models.CourseTicket;
import com.emusic.school.models.Ticket;
import com.emusic.school.repositories.CourseRepository;
import com.emusic.school.repositories.CourseTicketRepository;
import com.emusic.school.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl implements CourseService {
    @Autowired
    CourseRepository courseRepository;

    @Autowired
    CourseTicketRepository courseTicketRepository;

    @Override
    public List<CourseDTO> getCoursesDTO() {
        return courseRepository.findAll().stream().filter(Course::isActive).map(CourseDTO::new).collect(Collectors.toList());
    }

    @Override
    public void saveCourse(Course course) {
        courseRepository.save(course);
    }

    @Override
    public Course findById(long idCourse) {
        return courseRepository.findById(idCourse).orElse(null);
    }

    @Override
    public void deleteCourse(Course course) {
        course.setActive(false);
        courseRepository.save(course);
    }

    @Override
    public void saveTicketCourse(Course course, Ticket ticket) {
        CourseTicket courseTicket = new CourseTicket(ticket, course);
        courseTicketRepository.save(courseTicket);

    }

}
