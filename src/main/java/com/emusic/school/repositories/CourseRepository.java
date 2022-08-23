package com.emusic.school.repositories;

import com.emusic.school.models.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;


@RepositoryRestResource
public interface CourseRepository extends JpaRepository<Course, Long> {
}
