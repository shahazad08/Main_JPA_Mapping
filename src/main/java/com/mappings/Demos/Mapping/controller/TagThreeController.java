package com.mappings.Demos.Mapping.controller;

import com.mappings.Demos.Mapping.exception.ResourceNotFoundException;
import com.mappings.Demos.Mapping.model.TagThree;
import com.mappings.Demos.Mapping.model.TutorialThree;
import com.mappings.Demos.Mapping.repository.TagThreeRepository;
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
public class TagThreeController {

    @Autowired
    private TutorialThreeRepository tutorialThreeRepository;

    @Autowired
    private TagThreeRepository tagThreeRepository;

    @GetMapping("/tags")
    public ResponseEntity<List<TagThree>> getAllTags() {
        List<TagThree> tags = new ArrayList<TagThree>();

        tagThreeRepository.findAll().forEach(tags::add);

        if (tags.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(tags, HttpStatus.OK);
    }

    @GetMapping("/tutorials/{tutorialId}/tags")
    public ResponseEntity<List<TagThree>> getAllTagsByTutorialId(@PathVariable(value = "tutorialId") Long tutorialId) {
        if (!tutorialThreeRepository.existsById(tutorialId)) {
            throw new ResourceNotFoundException("Not found Tutorial with id = " + tutorialId);
        }

        List<TagThree> tags = tagThreeRepository.findTagsByTutorialsId(tutorialId);
        return new ResponseEntity<>(tags, HttpStatus.OK);
    }

    @GetMapping("/tags/{id}")
    public ResponseEntity<TagThree> getTagsById(@PathVariable(value = "id") Long id) {
        TagThree tag = tagThreeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Tag with id = " + id));

        return new ResponseEntity<>(tag, HttpStatus.OK);
    }

    @GetMapping("/tags/{tagId}/tutorials")
    public ResponseEntity<List<TutorialThree>> getAllTutorialsByTagId(@PathVariable(value = "tagId") Long tagId) {
        if (!tagThreeRepository.existsById(tagId)) {
            throw new ResourceNotFoundException("Not found Tag  with id = " + tagId);
        }

        List<TutorialThree> tutorials = tutorialThreeRepository.findTutorialsByTagsId(tagId);
        return new ResponseEntity<>(tutorials, HttpStatus.OK);
    }

    @PostMapping("/tutorials/{tutorialId}/tags")
    public ResponseEntity<TagThree> addTag(@PathVariable(value = "tutorialId") Long tutorialId, @RequestBody TagThree tagRequest) {
        TagThree tag = tutorialThreeRepository.findById(tutorialId).map(tutorial -> {
            long tagId = tagRequest.getId();

            // tag is existed
            if (tagId != 0L) {
                TagThree _tag = tagThreeRepository.findById(tagId)
                        .orElseThrow(() -> new ResourceNotFoundException("Not found Tag with id = " + tagId));
                tutorial.addTag(_tag);
                tutorialThreeRepository.save(tutorial);
                return _tag;
            }

            // add and create new Tag
            tutorial.addTag(tagRequest);
            return tagThreeRepository.save(tagRequest);
        }).orElseThrow(() -> new ResourceNotFoundException("Not found Tutorial with id = " + tutorialId));

        return new ResponseEntity<>(tag, HttpStatus.CREATED);

    }
        @PutMapping("/tags/{id}")
        public ResponseEntity<TagThree> updateTag(@PathVariable("id") long id, @RequestBody TagThree tagRequest) {
            TagThree tag = tagThreeRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("TagId " + id + "not found"));

            tag.setName(tagRequest.getName());

            return new ResponseEntity<>(tagThreeRepository.save(tag), HttpStatus.OK);
        }

        @DeleteMapping("/tutorials/{tutorialId}/tags/{tagId}")
        public ResponseEntity<HttpStatus> deleteTagFromTutorial(@PathVariable(value = "tutorialId") Long tutorialId, @PathVariable(value = "tagId") Long tagId) {
            TutorialThree tutorial = tutorialThreeRepository.findById(tutorialId)
                    .orElseThrow(() -> new ResourceNotFoundException("Not found Tutorial with id = " + tutorialId));

            tutorial.removeTag(tagId);
            tutorialThreeRepository.save(tutorial);

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        @DeleteMapping("/tags/{id}")
        public ResponseEntity<HttpStatus> deleteTag(@PathVariable("id") long id) {
            tagThreeRepository.deleteById(id);

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
