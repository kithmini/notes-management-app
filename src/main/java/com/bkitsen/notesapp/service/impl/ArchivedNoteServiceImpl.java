// Persistent Systems
//
// All Rights Reserved.
//
// This document or any part thereof may not, without the written
// consent of AePONA Limited, be copied, reprinted or reproduced in
// any material form including but not limited to photocopying,
// transcribing, transmitting or storing it in any medium or
// translating it into any language, in any form or by any means,
// be it electronic, mechanical, xerographic, optical,
// magnetic or otherwise.
//
// bs40414

package com.bkitsen.notesapp.service.impl;

import com.bkitsen.notesapp.models.ArchivedNote;
import com.bkitsen.notesapp.models.Note;
import com.bkitsen.notesapp.persistence.repository.ArchivedNotesRepository;
import com.bkitsen.notesapp.service.ArchivedNoteService;
import com.bkitsen.notesapp.service.NotesService;
import com.bkitsen.notesapp.utility.DtoConverter;
import com.bkitsen.notesapp.utility.RequestValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ArchivedNoteServiceImpl implements ArchivedNoteService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ArchivedNoteServiceImpl.class);

    private static final String UPDATE = "update";

    @Autowired
    private ArchivedNotesRepository archivedNotesRepository;
    @Autowired
    private NotesService notesService;
    @Autowired
    private DtoConverter dtoConverter;
    @Autowired
    private RequestValidator requestValidator;

    /**
     * Archive the note
     * @param id : ID of the note to be archived
     * */
    @Override
    @Transactional
    public ArchivedNote archiveNote(final Long id, final Principal principal) throws Exception {
        LOGGER.info("Archived note service invoked");
        final Note savedNote = notesService.getNoteById(id);
        if (savedNote != null) {
            requestValidator.setUserFromPrincipalToNote(principal, savedNote, UPDATE);
            final com.bkitsen.notesapp.persistence.entity.ArchivedNote savedArchivedNote = archivedNotesRepository.save(dtoConverter.NoteModelToArchivedNoteEntity(savedNote));
            notesService.deleteNote(id);
            return dtoConverter.archivedNotesEntityToModel(savedArchivedNote);
        }
        throw new RuntimeException("Note not found for id " + id);
    }

    /**
     * Unarchive the note
     * @param id : ID of the note to be unarchived
     * */
    @Override
    @Transactional
    public Note unarchiveNote(final Long id, final Principal principal) throws Exception {
        LOGGER.info("Unarchive note service invoked");
        final Optional<com.bkitsen.notesapp.persistence.entity.ArchivedNote> archivedNote = archivedNotesRepository.findById(id);
        if (archivedNote.isPresent()) {
            final Note convertedNote = dtoConverter.ArchivedNoteEntityToNoteModel(archivedNote.get());
            requestValidator.setUserFromPrincipalToNote(principal, convertedNote, UPDATE);
            final Note unarchivedNote = notesService.createNote(convertedNote, principal);
            archivedNotesRepository.deleteById(id);
            return unarchivedNote;
        }
        throw new RuntimeException("Note not found for id " + id);
    }

    /**
     * Get all the archived notes
     * */
    @Override
    public List<ArchivedNote> getAllArchivedNotes() {
        LOGGER.info("Archived note service invoked");
        final List<ArchivedNote> archivedNoteList = new ArrayList<>();
        Iterable<com.bkitsen.notesapp.persistence.entity.ArchivedNote> archivedNotes = archivedNotesRepository.findAll();
        for (com.bkitsen.notesapp.persistence.entity.ArchivedNote archivedNote : archivedNotes) {
            archivedNoteList.add(dtoConverter.archivedNotesEntityToModel(archivedNote));
        }
        return archivedNoteList;
    }
}
