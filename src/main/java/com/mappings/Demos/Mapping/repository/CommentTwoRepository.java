package com.mappings.Demos.Mapping.repository;

import com.mappings.Demos.Mapping.model.CommentTwo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentTwoRepository extends JpaRepository<CommentTwo, Long> {

}