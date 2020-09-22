package com.bkitsen.notesapp.persistence.repository;

import com.bkitsen.notesapp.persistence.entity.ArchivedNote;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArchivedNotesRepository extends CrudRepository<ArchivedNote, Long> {
}
