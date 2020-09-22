package com.bkitsen.notesapp.service;

import com.bkitsen.notesapp.models.ArchivedNote;
import com.bkitsen.notesapp.models.Note;

import java.security.Principal;
import java.util.List;

public interface ArchivedNoteService {

    ArchivedNote archiveNote(final Long id, final Principal principal) throws Exception;
    Note unarchiveNote(final Long id, final Principal principal) throws Exception;
    List<ArchivedNote> getAllArchivedNotes() throws Exception;
}
