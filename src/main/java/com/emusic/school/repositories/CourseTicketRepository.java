package com.emusic.school.repositories;

import com.emusic.school.models.CourseTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface CourseTicketRepository extends JpaRepository<CourseTicket,Long> {
}
