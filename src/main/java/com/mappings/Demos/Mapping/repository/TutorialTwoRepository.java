package com.mappings.Demos.Mapping.repository;
// Implementing the One to Many relationship

import java.util.List;

import com.mappings.Demos.Mapping.model.TutorialTwo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TutorialTwoRepository extends JpaRepository<TutorialTwo, Long> {
    List<TutorialTwo> findByPublished(boolean published);

    List<TutorialTwo> findByTitleContaining(String title);
}
