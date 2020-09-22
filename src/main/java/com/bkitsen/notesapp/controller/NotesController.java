package com.bkitsen.notesapp.controller;

import com.bkitsen.notesapp.models.Note;

import java.security.Principal;
import java.util.List;

import com.bkitsen.notesapp.service.NotesService;
import com.bkitsen.notesapp.utility.RequestValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/notes")
public class NotesController {

    private static final Logger LOGGER = LoggerFactory.getLogger(NotesController.class);

    @Autowired
    private NotesService notesService;
    @Autowired
    private RequestValidator requestValidator;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Object> getNotes() {
        LOGGER.trace("Listing all notes (unarchived)");
        try {
            final List<Note> noteList = notesService.getAllNotes();
            return new ResponseEntity<>(noteList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Object> createNote(@RequestBody Note note, Principal principal) {
        LOGGER.trace("Create note");
        try {
            final Note savedNote = notesService.createNote(note, principal);
            return new ResponseEntity<>(savedNote, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Object> updateNote(@PathVariable("id") Long id, @RequestBody Note note, Principal principal) {
        LOGGER.trace("Update note");
        try {
            final Note updatedNote = notesService.updateNote(id, note, principal);
            return new ResponseEntity<>(updatedNote, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> deleteNote(@PathVariable("id") Long id) {
        LOGGER.trace("Delete note");
        try {
            notesService.deleteNote(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
