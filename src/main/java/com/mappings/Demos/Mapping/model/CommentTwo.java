package com.mappings.Demos.Mapping.model;

// One to Many Relationship!

import jakarta.persistence.*;

@Entity
@Table(name = "comments_two")
public class CommentTwo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    private String content;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    // getters and setters
}
