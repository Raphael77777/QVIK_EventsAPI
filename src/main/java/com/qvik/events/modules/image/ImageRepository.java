package com.qvik.events.modules.image;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;

/** Repository for entity Image */
@Transactional(readOnly = true)
@RepositoryRestResource
public interface ImageRepository extends JpaRepository<Image, Long> {
}
