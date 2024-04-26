package com.mappings.Demos.Mapping.repository;

import com.mappings.Demos.Mapping.model.Tutorial;
import com.mappings.Demos.Mapping.model.TutorialThree;
import com.mappings.Demos.Mapping.model.TutorialTwo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TutorialThreeRepository extends JpaRepository<TutorialThree, Long> {
    // ...
    List<TutorialThree> findTutorialsByTagsId(Long tagId);

    List<Tutorial> findByTitleContaining(String title);

    List<TutorialTwo> findByPublished(boolean published);
}
