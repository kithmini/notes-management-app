package com.bkitsen.notesapp.persistence.repository;

import com.bkitsen.notesapp.persistence.entity.Note;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotesRepository extends CrudRepository<Note, Long> {
}
