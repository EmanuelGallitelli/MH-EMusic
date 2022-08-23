package com.emusic.school.services.implement;

import com.emusic.school.dtos.TeacherDTO;
import com.emusic.school.models.Teacher;
import com.emusic.school.repositories.TeacherRepository;
import com.emusic.school.services.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TeacherServiceImpl implements TeacherService {
    @Autowired
    TeacherRepository teacherRepository;


    @Override
    public List<TeacherDTO> getTeachersDTO() {
        return teacherRepository.findAll().stream().filter(Teacher::isActive).map(TeacherDTO::new).collect(Collectors.toList());
    }

    @Override
    public Teacher getTeacherByEmail(String email) {
        return teacherRepository.findByEmail(email);
    }

    @Override
    public TeacherDTO getTeacherDTOById(Long id) {
        return teacherRepository.findById(id).map(TeacherDTO :: new).orElse(null);
    }

    @Override
    public Teacher getTeacherById(Long id) {
        return teacherRepository.findById(id).orElse(null);
    }

    @Override
    public void saveTeacher(Teacher teacher) {
        teacherRepository.save(teacher);
    }
}
