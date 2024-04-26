package com.mappings.Demos.Mapping.controller;

import com.mappings.Demos.Mapping.exception.ResourceNotFoundException;
import com.mappings.Demos.Mapping.model.TutorialThree;
import com.mappings.Demos.Mapping.model.TutorialTwo;
import com.mappings.Demos.Mapping.repository.TutorialThreeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/apimany")
public class TutorialThreeController {

    @Autowired
    TutorialThreeRepository tutorialThreeRepository;

    @GetMapping("/tutorials")
    public ResponseEntity<List<TutorialThree>> getAllTutorials(@RequestParam(required = false) String title) {

            List<TutorialThree> tutorials = new ArrayList<TutorialThree>();

            if (title == null)
                tutorialThreeRepository.findAll().forEach(tutorials::add);
//            else
//                tutorialThreeRepository.findByTitleContaining(title).forEach(tutorials::add);


            if (tutorials.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(tutorials, HttpStatus.OK);
    }

    @GetMapping("/tutorials/{id}")
    public ResponseEntity<TutorialThree> getTutorialById(@PathVariable("id") long id) {
        TutorialThree tutorial = tutorialThreeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Tutorial with id = " + id));

        return new ResponseEntity<>(tutorial, HttpStatus.OK);
    }


    @PostMapping("/tutorials")
    public ResponseEntity<TutorialThree> createTutorial(@RequestBody TutorialThree tutorial) {
        TutorialThree _tutorial = tutorialThreeRepository.save(new TutorialThree(tutorial.getTitle(), tutorial.getDescription(), true));
        return new ResponseEntity<>(_tutorial, HttpStatus.CREATED);
    }

    @PutMapping("/tutorials/{id}")
    public ResponseEntity<TutorialThree> updateTutorial(@PathVariable("id") long id, @RequestBody TutorialThree tutorial) {
        TutorialThree _tutorial = tutorialThreeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Tutorial with id = " + id));

        _tutorial.setTitle(tutorial.getTitle());
        _tutorial.setDescription(tutorial.getDescription());
        _tutorial.setPublished(tutorial.isPublished());

        return new ResponseEntity<>(tutorialThreeRepository.save(_tutorial), HttpStatus.OK);
    }


    @DeleteMapping("/tutorials")
    public ResponseEntity<HttpStatus> deleteAllTutorials() {
        tutorialThreeRepository.deleteAll();

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @DeleteMapping("/tutorials/{id}")
    public ResponseEntity<HttpStatus> deleteTutorial(@PathVariable("id") long id) {
        tutorialThreeRepository.deleteById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }




}
