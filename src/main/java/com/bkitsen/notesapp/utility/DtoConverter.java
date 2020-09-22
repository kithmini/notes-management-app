package com.bkitsen.notesapp.utility;

import com.bkitsen.notesapp.models.ArchivedNote;
import com.bkitsen.notesapp.persistence.entity.Note;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class DtoConverter {

    /**
     * This class contains entity DTO conversion from request to DB entities
     * and DB entities to responses
     * */

    public com.bkitsen.notesapp.models.Note notesEntityToModel(final Note noteEntity) {
        final com.bkitsen.notesapp.models.Note noteModel = new com.bkitsen.notesapp.models.Note();
        if (noteEntity != null) {
            noteModel.setId(noteEntity.getId());
            noteModel.setTitle(noteEntity.getTitle());
            noteModel.setContent(noteEntity.getContent());
            noteModel.setCreatedBy(noteEntity.getCreatedBy());
            noteModel.setLastModifiedBy(noteEntity.getLastModifiedBy());
            noteModel.setCreatedDate(noteEntity.getCreatedDate());
            noteModel.setLastModifiedDate(noteEntity.getLastModifiedDate());
        }
        return noteModel;
    }

    public Note notesCreateModelToEntity(final com.bkitsen.notesapp.models.Note noteModel) {
        final Note noteEntity = new Note();
        if (noteModel != null) {
            noteEntity.setId(noteModel.getId());
            noteEntity.setTitle(noteModel.getTitle());
            noteEntity.setContent(noteModel.getContent());
            noteEntity.setCreatedBy(noteModel.getCreatedBy());
            noteEntity.setCreatedDate(noteModel.getCreatedDate() != null ? noteModel.getCreatedDate() : new Date());
            noteEntity.setLastModifiedDate(noteModel.getLastModifiedDate());
            noteEntity.setLastModifiedBy(noteModel.getLastModifiedBy());
        }
        return noteEntity;
    }

    public Note notesUpdateModelToEntity(final com.bkitsen.notesapp.models.Note noteModel, final Note noteEntity) {
        if (noteModel != null) {
            noteEntity.setTitle(noteModel.getTitle());
            noteEntity.setContent(noteModel.getContent());
            noteEntity.setLastModifiedBy(noteModel.getLastModifiedBy());
            noteEntity.setLastModifiedDate(new Date());
        }
        return noteEntity;
    }

    public ArchivedNote archivedNotesEntityToModel(final com.bkitsen.notesapp.persistence.entity.ArchivedNote archivedNoteEntity) {
        final ArchivedNote archivedNoteModel = new ArchivedNote();
        if (archivedNoteEntity != null) {
            archivedNoteModel.setId(archivedNoteEntity.getId());
            archivedNoteModel.setTitle(archivedNoteEntity.getTitle());
            archivedNoteModel.setContent(archivedNoteEntity.getContent());
            archivedNoteModel.setCreatedBy(archivedNoteEntity.getCreatedBy());
            archivedNoteModel.setLastModifiedBy(archivedNoteEntity.getLastModifiedBy());
            archivedNoteModel.setOriginalCreatedDate(archivedNoteEntity.getOriginalCreatedDate());
            archivedNoteModel.setArchivedDate(archivedNoteEntity.getArchivedDate());
        }
        return archivedNoteModel;
    }

    public com.bkitsen.notesapp.persistence.entity.ArchivedNote NoteModelToArchivedNoteEntity(final com.bkitsen.notesapp.models.Note noteModel) {
        final com.bkitsen.notesapp.persistence.entity.ArchivedNote archivedNoteEntity = new com.bkitsen.notesapp.persistence.entity.ArchivedNote();
        if (noteModel != null) {
            archivedNoteEntity.setId(noteModel.getId());
            archivedNoteEntity.setTitle(noteModel.getTitle());
            archivedNoteEntity.setContent(noteModel.getContent());
            archivedNoteEntity.setCreatedBy(noteModel.getCreatedBy());
            archivedNoteEntity.setLastModifiedBy(noteModel.getLastModifiedBy());
            archivedNoteEntity.setOriginalCreatedDate(noteModel.getCreatedDate());
            archivedNoteEntity.setArchivedDate(new Date());
        }
        return archivedNoteEntity;
    }

    public com.bkitsen.notesapp.models.Note ArchivedNoteEntityToNoteModel(final com.bkitsen.notesapp.persistence.entity.ArchivedNote archivedNoteEntity) {
        final com.bkitsen.notesapp.models.Note noteModel = new com.bkitsen.notesapp.models.Note();
        if (archivedNoteEntity != null) {
            noteModel.setId(archivedNoteEntity.getId());
            noteModel.setTitle(archivedNoteEntity.getTitle());
            noteModel.setContent(archivedNoteEntity.getContent());
            noteModel.setCreatedBy(archivedNoteEntity.getCreatedBy());
            noteModel.setLastModifiedBy(archivedNoteEntity.getLastModifiedBy());
            noteModel.setCreatedDate(archivedNoteEntity.getOriginalCreatedDate());
            noteModel.setLastModifiedDate(new Date());
        }
        return noteModel;
    }

}
