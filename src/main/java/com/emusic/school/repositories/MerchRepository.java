package com.emusic.school.repositories;

import com.emusic.school.models.Merch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface MerchRepository extends JpaRepository<Merch, Long> {
}
