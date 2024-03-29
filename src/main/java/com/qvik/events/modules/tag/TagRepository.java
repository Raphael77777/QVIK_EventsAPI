package com.qvik.events.modules.tag;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;

/** Repository for entity Tag */
@Transactional(readOnly = true)
@RepositoryRestResource
public interface TagRepository extends JpaRepository <Tag, Long> {
	Tag findByName(String name);
}
