package com.bkitsen.notesapp.controller;

import com.bkitsen.notesapp.models.ArchivedNote;
import com.bkitsen.notesapp.models.Note;
import com.bkitsen.notesapp.service.ArchivedNoteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/archived-notes")
public class ArchiveNotesController {

    private static final Logger LOGGER = LoggerFactory.getLogger(NotesController.class);

    @Autowired
    private ArchivedNoteService archivedNoteService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Object> getArchivedNotes() {
        LOGGER.trace("Listing all archived notes");
        try {
            final List<ArchivedNote> noteList = archivedNoteService.getAllArchivedNotes();
            return new ResponseEntity<>(noteList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/{id}/archive", method = RequestMethod.PUT)
    public ResponseEntity<Object> archiveNote(@PathVariable("id") Long id, Principal principal) {
        LOGGER.trace("Archive note");
        try {
            final ArchivedNote archivedNote = archivedNoteService.archiveNote(id, principal);
            return new ResponseEntity<>(archivedNote, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/{id}/unarchive", method = RequestMethod.PUT)
    public ResponseEntity<Object> unarchiveNote(@PathVariable("id") Long id, Principal principal) {
        LOGGER.trace("Unarchive note");
        try {
            final Note unarchivedNote = archivedNoteService.unarchiveNote(id, principal);
            return new ResponseEntity<>(unarchivedNote, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
