package com.emusic.school.services;

import com.emusic.school.dtos.CourseDTO;
import com.emusic.school.models.Course;
import com.emusic.school.models.Ticket;

import java.util.List;

public interface CourseService {
    List<CourseDTO> getCoursesDTO();

    void saveCourse(Course course);

    Course findById(long idCourse);

    void deleteCourse(Course course);

    void saveTicketCourse(Course course, Ticket ticket);
}
