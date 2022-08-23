package com.emusic.school.services;

import com.emusic.school.dtos.TeacherDTO;
import com.emusic.school.models.Teacher;

import java.util.List;

public interface TeacherService {
    List<TeacherDTO> getTeachersDTO();

    Teacher getTeacherByEmail(String email);

    TeacherDTO getTeacherDTOById(Long id);
    Teacher getTeacherById(Long id);

    void saveTeacher(Teacher teacher);
}
