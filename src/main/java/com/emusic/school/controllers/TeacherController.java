package com.emusic.school.controllers;

import com.emusic.school.dtos.TeacherDTO;

import com.emusic.school.models.Teacher;
import com.emusic.school.services.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TeacherController{
    @Autowired
    TeacherService teacherService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @GetMapping("/teachers")
    public List<TeacherDTO> getTeachers(){
        return teacherService.getTeachersDTO();
    }
    @GetMapping("/teacher/{id}")
    public TeacherDTO getTeacher(@PathVariable Long id){
        return teacherService.getTeacherDTOById(id);
    }
    @PostMapping("teachers")
    public ResponseEntity<?> createAccountTeacher(@RequestParam String firstName,
                                                  @RequestParam String lastName, @RequestParam String email,
                                                  @RequestParam String password, @RequestParam String subject){
        if(firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()){
            return new ResponseEntity<>("MISSING DATA", HttpStatus.FORBIDDEN);
        }
        if(teacherService.getTeacherByEmail(email) != null){
            return new ResponseEntity<>("EMAIL ALREADY REGISTERED", HttpStatus.FORBIDDEN);
        }

        Teacher newTeacher = new Teacher(firstName,lastName,email,passwordEncoder.encode(password),subject,true);
        teacherService.saveTeacher(newTeacher);
        return new ResponseEntity<>("REGISTERED TEACHER", HttpStatus.CREATED);
    }

    @PatchMapping("/delete/teacher")
    public ResponseEntity<Object> deleteMerch(@RequestParam long id){
        Teacher teacherToDelete = teacherService.getTeacherById(id);
        if(teacherToDelete == null){
            return new ResponseEntity<>("Missing Data.",HttpStatus.FORBIDDEN);
        }

        teacherToDelete.setActive(false);
        teacherService.saveTeacher(teacherToDelete);
        return new ResponseEntity<>("Successfully deleted.",HttpStatus.ACCEPTED);
    }

    @PatchMapping("edit/teacher")
    public ResponseEntity<Object> editTeacher(@RequestParam long id, @RequestParam String email, @RequestParam String password, @RequestParam String subject){
        Teacher teacherToEdit = teacherService.getTeacherById(id);


        if (email.isEmpty() || teacherToEdit == null || subject.isEmpty() || password.isEmpty()){
           return new ResponseEntity<>("Missing data.", HttpStatus.FORBIDDEN);
        }

        if (password.length() < 4){
           return new ResponseEntity<>("The password is too short.",HttpStatus.FORBIDDEN);
        }


        teacherToEdit.setEmail(email);
        teacherToEdit.setPassword(passwordEncoder.encode(password));
        teacherToEdit.setSubject(subject);
        teacherService.saveTeacher(teacherToEdit);
        return new ResponseEntity<>("Teacher information updated.",HttpStatus.ACCEPTED);
    }
}
