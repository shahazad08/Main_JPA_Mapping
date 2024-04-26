package com.mappings.Demos.Mapping.repository;

import com.mappings.Demos.Mapping.model.TagThree;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TagThreeRepository extends JpaRepository<TagThree, Long> {
    List<TagThree> findTagsByTutorialsId(Long tutorialId);
}