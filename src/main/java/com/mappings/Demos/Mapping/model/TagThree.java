package com.mappings.Demos.Mapping.model;


// It Represents the Many to Many Relationship.

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tags_three")
public class TagThree {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    private String name;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            },
            mappedBy = "tags")
    @JsonIgnore
    private Set<TutorialThree> tutorials = new HashSet<>();

    public TagThree() {

    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<TutorialThree> getTutorials() {
        return tutorials;
    }

    public void setTutorials(Set<TutorialThree> tutorials) {
        this.tutorials = tutorials;
    }
}