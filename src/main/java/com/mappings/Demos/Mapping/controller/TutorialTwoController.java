package com.mappings.Demos.Mapping.controller;

// One to Many Relationship!

import com.mappings.Demos.Mapping.exception.ResourceNotFoundException;
import com.mappings.Demos.Mapping.model.TutorialTwo;
import com.mappings.Demos.Mapping.repository.TutorialTwoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/apis")
public class TutorialTwoController {

    @Autowired
    TutorialTwoRepository tutorialTwoRepository;

    @GetMapping("/tutorials")
    public ResponseEntity<List<TutorialTwo>> getAllTutorials(@RequestParam(required = false) String title) {
        List<TutorialTwo> tutorials = new ArrayList<TutorialTwo>();

        if (title == null)
            tutorialTwoRepository.findAll().forEach(tutorials::add);
//        else
//            tutorialTwoRepository.findByTitleContaining(title).forEach(tutorials::add);

        if (tutorials.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(tutorials, HttpStatus.OK);
    }

    @GetMapping("/tutorials/{id}")
    public ResponseEntity<TutorialTwo> getTutorialById(@PathVariable("id") long id) {
        TutorialTwo tutorial = tutorialTwoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Tutorial with id = " + id));

        return new ResponseEntity<>(tutorial, HttpStatus.OK);
    }

    @PostMapping("/tutorials")
    public ResponseEntity<TutorialTwo> createTutorial(@RequestBody TutorialTwo tutorial) {
        TutorialTwo _tutorial = tutorialTwoRepository.save(new TutorialTwo(tutorial.getTitle(), tutorial.getDescription(), true));
        return new ResponseEntity<>(_tutorial, HttpStatus.CREATED);
    }

    @PutMapping("/tutorials/{id}")
    public ResponseEntity<TutorialTwo> updateTutorial(@PathVariable("id") long id, @RequestBody TutorialTwo tutorial) {
        TutorialTwo _tutorial = tutorialTwoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Tutorial with id = " + id));

        _tutorial.setTitle(tutorial.getTitle());
        _tutorial.setDescription(tutorial.getDescription());
        _tutorial.setPublished(tutorial.isPublished());

        return new ResponseEntity<>(tutorialTwoRepository.save(_tutorial), HttpStatus.OK);
    }

    @DeleteMapping("/tutorials/{id}")
    public ResponseEntity<HttpStatus> deleteTutorial(@PathVariable("id") long id) {
        tutorialTwoRepository.deleteById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/tutorials")
    public ResponseEntity<HttpStatus> deleteAllTutorials() {
        tutorialTwoRepository.deleteAll();

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/tutorials/published")
    public ResponseEntity<List<TutorialTwo>> findByPublished() {
        List<TutorialTwo> tutorials = tutorialTwoRepository.findByPublished(true);

        if (tutorials.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(tutorials, HttpStatus.OK);
    }
}
