package com.bkitsen.notesapp.service.impl;

import com.bkitsen.notesapp.models.Note;
import com.bkitsen.notesapp.persistence.repository.NotesRepository;
import com.bkitsen.notesapp.service.NotesService;
import com.bkitsen.notesapp.utility.DtoConverter;
import com.bkitsen.notesapp.utility.RequestValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class NotesServiceImpl implements NotesService {

    private static final Logger LOGGER = LoggerFactory.getLogger(NotesServiceImpl.class);

    private static final String CREATE = "create";
    private static final String UPDATE = "update";

    @Autowired
    private DtoConverter dtoConverter;
    @Autowired
    private NotesRepository notesRepository;
    @Autowired
    private RequestValidator requestValidator;


    /**
     * Creates a note
     * @param note : Note object to be created
     * */
    @Override
    public Note createNote(final Note note, final Principal principal) {
        LOGGER.info("Create note service invoked");
        requestValidator.setUserFromPrincipalToNote(principal, note, CREATE);
        requestValidator.validateNoteCreate(note);
        final com.bkitsen.notesapp.persistence.entity.Note saved =  notesRepository.save(dtoConverter.notesCreateModelToEntity(note));
        return dtoConverter.notesEntityToModel(saved);
    }

    /**
     * List all the (unarchived) notes
     * */
    @Override
    public List<Note> getAllNotes() {
        LOGGER.info("Get all notes (unarchived) service invoked");
        final List<Note> noteList = new ArrayList<>();
        Iterable<com.bkitsen.notesapp.persistence.entity.Note> repoNoteList = notesRepository.findAll();
        for (com.bkitsen.notesapp.persistence.entity.Note note : repoNoteList) {
            noteList.add(dtoConverter.notesEntityToModel(note));
        }
        return noteList;
    }

    /**
     * Get note by id
     * @param id : ID of the note to get
     * */
    @Override
    public Note getNoteById(final Long id) {
        LOGGER.info("Get note (unarchived) by ID service invoked");
        final Optional<com.bkitsen.notesapp.persistence.entity.Note> repoNote = notesRepository.findById(id);
        if (repoNote.isPresent()) {
            return dtoConverter.notesEntityToModel(repoNote.get());
        }
        throw new RuntimeException("Note not found for id : " + id);
    }

    /**
     * Update the note
     * @param note : Note object to be updated
     * @param id : ID of the note to be updated
     * */
    @Override
    public Note updateNote(final Long id, final Note note, final Principal principal) {
        LOGGER.info("Update note service invoked");
        requestValidator.setUserFromPrincipalToNote(principal, note, UPDATE);
        requestValidator.validateNoteUpdate(note, id);
        final Optional<com.bkitsen.notesapp.persistence.entity.Note> repoNote = notesRepository.findById(id);
        if (repoNote.isPresent()) {
            final com.bkitsen.notesapp.persistence.entity.Note updated =  notesRepository.save(dtoConverter.notesUpdateModelToEntity(note, repoNote.get()));
            return dtoConverter.notesEntityToModel(updated);
        }
        throw new RuntimeException("Note not found for id : " + id);
    }

    /**
     * Delete the note
     * @param id : ID of the note to be deleted
     * */
    @Override
    public void deleteNote(final Long id) {
        LOGGER.info("Delete note service invoked");
        notesRepository.deleteById(id);
    }
}
