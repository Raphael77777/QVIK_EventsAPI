package com.qvik.events.modules.presenter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/** Repository for entity Presenter */
@RepositoryRestResource
public interface PresenterRepository extends JpaRepository<Presenter, Long> {
}
