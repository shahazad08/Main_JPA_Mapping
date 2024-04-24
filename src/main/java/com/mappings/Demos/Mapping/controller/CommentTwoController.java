package com.mappings.Demos.Mapping.controller;

import com.mappings.Demos.Mapping.exception.ResourceNotFoundException;
import com.mappings.Demos.Mapping.model.CommentTwo;
import com.mappings.Demos.Mapping.model.TutorialTwo;
import com.mappings.Demos.Mapping.repository.CommentTwoRepository;
import com.mappings.Demos.Mapping.repository.TutorialTwoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

// One to Many relationship...
// One Tutorial has many comments...


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/apis")
public class CommentTwoController {

    @Autowired
    private TutorialTwoRepository tutorialTwoRepository;

    @Autowired
    private CommentTwoRepository commentTwoRepository;

    @PostMapping("/tutorials/{tutorialId}/comments")
    public ResponseEntity<CommentTwo> createComment(@PathVariable(value = "tutorialId") Long tutorialId,
                                                    @RequestBody CommentTwo commentRequest) {
        CommentTwo comment = tutorialTwoRepository.findById(tutorialId).map(tutorial -> {
            tutorial.getComments().add(commentRequest);
            return commentTwoRepository.save(commentRequest);
        }).orElseThrow(() -> new ResourceNotFoundException("Not found Tutorial with id = " + tutorialId));

        return new ResponseEntity<>(comment, HttpStatus.CREATED);
    }

    @GetMapping("/tutorials/{tutorialId}/comments")
    public ResponseEntity<List<CommentTwo>> getAllCommentsByTutorialId(@PathVariable(value = "tutorialId") Long tutorialId) {
        TutorialTwo tutorial = tutorialTwoRepository.findById(tutorialId)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Tutorial with id = " + tutorialId));

        List<CommentTwo> comments = new ArrayList<CommentTwo>();
        comments.addAll(tutorial.getComments());

        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    @GetMapping("/comments/{id}")
    public ResponseEntity<CommentTwo> getCommentsByTutorialId(@PathVariable(value = "id") Long id) {
        CommentTwo comment = commentTwoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Comment with id = " + id));

        return new ResponseEntity<>(comment, HttpStatus.OK);
    }

    @PutMapping("/comments/{id}")
    public ResponseEntity<CommentTwo> updateComment(@PathVariable("id") long id, @RequestBody CommentTwo commentRequest) {
        CommentTwo comment = commentTwoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("CommentId " + id + "not found"));

        comment.setContent(commentRequest.getContent());

        return new ResponseEntity<>(commentTwoRepository.save(comment), HttpStatus.OK);
    }

    @DeleteMapping("/comments/{id}")
    public ResponseEntity<HttpStatus> deleteComment(@PathVariable("id") long id) {
        commentTwoRepository.deleteById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/tutorials/{tutorialId}/comments")
    public ResponseEntity<List<CommentTwo>> deleteAllCommentsOfTutorial(@PathVariable(value = "tutorialId") Long tutorialId) {
        TutorialTwo tutorial = tutorialTwoRepository.findById(tutorialId)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Tutorial with id = " + tutorialId));

        tutorial.removeComments();
        tutorialTwoRepository.save(tutorial);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
