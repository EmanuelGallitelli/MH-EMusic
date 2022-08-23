package com.emusic.school.controllers;

import com.emusic.school.dtos.CourseDTO;
import com.emusic.school.dtos.EditCourseDTO;
import com.emusic.school.dtos.NewCourseDTO;
import com.emusic.school.models.Course;
import com.emusic.school.models.Teacher;
import com.emusic.school.services.CourseService;
import com.emusic.school.services.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CourseController {
    @Autowired
    TeacherService teacherService;
    @Autowired
    CourseService courseService;
    @GetMapping("/courses")
    public List<CourseDTO> getCourses(){
        return courseService.getCoursesDTO();
    }
    @PostMapping("/courses")
    public ResponseEntity<?> createCourse(@RequestBody NewCourseDTO newCourseDTO){
        if(newCourseDTO.getDuration() < 10 || newCourseDTO.getLessons()<0 || newCourseDTO.getLevel().isEmpty() ||
                newCourseDTO.getName().isEmpty() || newCourseDTO.getIdTeacher() == null){
            return new ResponseEntity<>("MISSING DATA", HttpStatus.FORBIDDEN);
        }
        Teacher teacher = teacherService.getTeacherById(newCourseDTO.getIdTeacher());
        if(teacher == null){
            return new ResponseEntity<>("TEACHER DON'T EXISTS", HttpStatus.FORBIDDEN);
        }
        Course course = new Course(newCourseDTO.getLevel(), newCourseDTO.getName(), newCourseDTO.getLessons(),
                newCourseDTO.getPrice().doubleValue(), newCourseDTO.getDuration(), true, teacher);
        courseService.saveCourse(course);
        return new ResponseEntity<>("CREATED", HttpStatus.CREATED);
    }

    @PatchMapping("/delete/courses")
    public ResponseEntity<Object> deleteCourse(@RequestParam long idCourse){
        if(idCourse <= 0){
            return new ResponseEntity<>("MISSING DATA", HttpStatus.FORBIDDEN);
        }
        Course course = courseService.findById(idCourse);
        if(course == null){
            return new ResponseEntity<>("THIS COURSE DOESN'T EXISTS", HttpStatus.FORBIDDEN);
        }
        courseService.deleteCourse(course);
        return new ResponseEntity<>("COURSE DELETED", HttpStatus.OK);
    }

    @PatchMapping("/edit/courses")
    public ResponseEntity<Object> editCourse (@RequestBody EditCourseDTO editCourseDTO){

        Course courseToEdit = courseService.findById(editCourseDTO.getId());
        Teacher teacherOfCourse = teacherService.getTeacherById(editCourseDTO.getTeacherId());

        if (editCourseDTO.getDuration() < 1 || editCourseDTO.getPrice() < 1 || editCourseDTO.getLessons() < 1
                || editCourseDTO.getLevel().isEmpty() || editCourseDTO.getName().isEmpty() || teacherOfCourse == null){
            return new ResponseEntity<>("Missing data.", HttpStatus.FORBIDDEN);
        }

        if (editCourseDTO.getName().equals(courseToEdit.getName()) && editCourseDTO.getPrice() == courseToEdit.getPrice() && editCourseDTO.getLevel().equals(courseToEdit.getLevel())
                && editCourseDTO.getLessons() == courseToEdit.getLessons() && editCourseDTO.getDuration() == courseToEdit.getDuration() && teacherOfCourse == courseToEdit.getTeacher()){
            return new ResponseEntity<>("Nothing to edit.", HttpStatus.FORBIDDEN);
        }

        courseToEdit.setName(editCourseDTO.getName());
        courseToEdit.setPrice(editCourseDTO.getPrice());
        courseToEdit.setLessons(editCourseDTO.getLessons());
        courseToEdit.setDuration(editCourseDTO.getDuration());
        courseToEdit.setLevel(editCourseDTO.getLevel());
        courseToEdit.setTeacher(teacherOfCourse);
        courseService.saveCourse(courseToEdit);
        return new ResponseEntity<>("Changes applied successfully.", HttpStatus.ACCEPTED);
    }

}
