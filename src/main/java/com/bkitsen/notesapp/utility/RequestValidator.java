package com.bkitsen.notesapp.utility;

import com.bkitsen.notesapp.models.Note;
import org.springframework.stereotype.Component;

import java.security.Principal;

@Component
public class RequestValidator {

    private static final String CREATE = "create";
    private static final String UPDATE = "update";

    /**
     * Validations for Note creation
     * */
    public void validateNoteCreate(final Note note) {
        if (note.getCreatedBy() == null) {
            throw new RuntimeException("CreateBy field is empty");
        }
    }

    /**
     * Validations for Note updating
     * */
    public void validateNoteUpdate(final Note note, final Long id) {
        if (note.getLastModifiedBy() == null) {
            throw new RuntimeException("CreateBy field is empty");
        }
        if (id == null) {
            throw new RuntimeException("ID is null in update request");
        }
    }

    /**
     * Validations for Note deletion
     * */
    public void validateNoteDelete(final Note note) {
        if (note.getId() == null) {
            throw new RuntimeException("ID is null in delete request");
        }
    }

    /**
     * Set create update user to note
     * */
    public void setUserFromPrincipalToNote(final Principal principal, final Note note, final String type) {
        final String username = principal.getName();
        if (username != null && !username.isEmpty()) {
            if (CREATE.equalsIgnoreCase(type)) {
                note.setCreatedBy(username);
            } else if (UPDATE.equalsIgnoreCase(type)) {
                note.setLastModifiedBy(username);
            }
        }
    }

}
