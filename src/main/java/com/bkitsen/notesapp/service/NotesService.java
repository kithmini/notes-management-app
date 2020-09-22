package com.bkitsen.notesapp.service;

import com.bkitsen.notesapp.models.Note;

import java.security.Principal;
import java.util.List;

public interface NotesService {

    Note createNote(final Note note, Principal principal) throws Exception;
    List<Note> getAllNotes() throws Exception;
    Note getNoteById(final Long id) throws Exception;
    Note updateNote(final Long id, final Note note, Principal principal) throws Exception;
    void deleteNote(final Long id) throws Exception;
}
