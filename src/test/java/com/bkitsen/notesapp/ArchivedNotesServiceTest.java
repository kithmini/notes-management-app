package com.bkitsen.notesapp;

import com.bkitsen.notesapp.models.ArchivedNote;
import com.bkitsen.notesapp.models.Note;
import com.bkitsen.notesapp.persistence.repository.ArchivedNotesRepository;
import com.bkitsen.notesapp.service.ArchivedNoteService;
import com.bkitsen.notesapp.service.NotesService;
import com.bkitsen.notesapp.utility.DtoConverter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {NotesAppApplication.class, ArchivedNoteService.class})

public class ArchivedNotesServiceTest {

    @Autowired
    private ArchivedNoteService archivedNoteService;
    @MockBean
    private NotesService notesService;
    @MockBean
    private DtoConverter dtoConverter;
    @MockBean
    private ArchivedNotesRepository archivedNotesRepository;

    private String date1;
    private String date2;

    private Note testNote1;
    private Note testNote2;
    private ArchivedNote testArchiveNote1;
    private ArchivedNote testArchiveNote2;
    private com.bkitsen.notesapp.persistence.entity.ArchivedNote testArchiveEntityNote1;
    private com.bkitsen.notesapp.persistence.entity.ArchivedNote testArchiveEntityNote2;
    private List<Note> noteList;
    private List<com.bkitsen.notesapp.persistence.entity.ArchivedNote> archivedNoteEntityList;
    private List<ArchivedNote> archivedNoteList;
    private Principal principal;

    @Before
    public void setUp() throws Exception {
        // Prepare
        date1 = "2020-09-22 08:57:58";
        date2 = "2020-09-27 10:57:58";

        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        testNote1 = new Note();
        testNote1.setId(1L);
        testNote1.setTitle("Test Title 1");
        testNote1.setTitle("Test content body 1");
        testNote1.setCreatedDate(fmt.parse(date1));
        testNote1.setCreatedBy("admin");

        testNote2 = new Note();
        testNote2.setId(2L);
        testNote2.setTitle("Test Title 2");
        testNote2.setTitle("Test content body 2");
        testNote2.setCreatedDate(fmt.parse(date2));
        testNote2.setCreatedBy("admin");

        testArchiveNote1 = new ArchivedNote();
        testArchiveNote1.setId(1L);
        testArchiveNote1.setTitle("Test Title 1");
        testArchiveNote1.setTitle("Test content body 1");
        testArchiveNote1.setArchivedDate(fmt.parse(date1));
        testArchiveNote1.setCreatedBy("admin");

        testArchiveNote2 = new ArchivedNote();
        testArchiveNote2.setId(2L);
        testArchiveNote2.setTitle("Test Title 2");
        testArchiveNote2.setContent("Test content body 2");
        testArchiveNote2.setArchivedDate(fmt.parse(date2));
        testArchiveNote2.setCreatedBy("admin");


        testArchiveEntityNote1 = new com.bkitsen.notesapp.persistence.entity.ArchivedNote();
        testArchiveEntityNote1.setId(1L);
        testArchiveEntityNote1.setTitle("Test Title 1");
        testArchiveEntityNote1.setContent("Test content body 1");
        testArchiveEntityNote1.setArchivedDate(fmt.parse(date1));
        testArchiveEntityNote1.setCreatedBy("admin");

        testArchiveEntityNote2 = new com.bkitsen.notesapp.persistence.entity.ArchivedNote();
        testArchiveEntityNote2.setId(2L);
        testArchiveEntityNote2.setTitle("Test Title 2");
        testArchiveEntityNote2.setTitle("Test content body 2");
        testArchiveEntityNote2.setArchivedDate(fmt.parse(date2));
        testArchiveEntityNote2.setCreatedBy("admin");

        noteList = new ArrayList<>();
        noteList.add(testNote1);
        noteList.add(testNote2);

        archivedNoteList = new ArrayList<>();
        archivedNoteList.add(testArchiveNote1);
        archivedNoteList.add(testArchiveNote2);

        archivedNoteEntityList = new ArrayList<>();
        archivedNoteEntityList.add(testArchiveEntityNote1);
        archivedNoteEntityList.add(testArchiveEntityNote2);

        principal = () -> "admin";

    }

    @Test
    public void testWhenGetAllArchivedNotes_thenReturnArchivedNoteList() throws Exception {

        when(archivedNotesRepository.findAll()).thenReturn(archivedNoteEntityList);
        when(dtoConverter.archivedNotesEntityToModel(testArchiveEntityNote1)).thenReturn(testArchiveNote1);
        when(dtoConverter.archivedNotesEntityToModel(testArchiveEntityNote2)).thenReturn(testArchiveNote2);

        // Test
        final List<ArchivedNote> archivedNoteList = archivedNoteService.getAllArchivedNotes();

        assertEquals(2, noteList.size());

        verify(archivedNotesRepository, times(1)).findAll();
    }

    @Test
    public void testWhenArchiveNote_thenReturnArchivedNote() throws Exception {

        when(notesService.getNoteById(1L)).thenReturn(testNote1);
        when(dtoConverter.noteModelToArchivedNoteEntity(testNote1)).thenReturn(testArchiveEntityNote1);
        when(archivedNotesRepository.save(testArchiveEntityNote1)).thenReturn(testArchiveEntityNote1);
        when(dtoConverter.archivedNotesEntityToModel(testArchiveEntityNote1)).thenReturn(testArchiveNote1);

        // Test
        final ArchivedNote archivedNote = archivedNoteService.archiveNote(1L, principal);

        assertNotNull(archivedNote);
        assertThat(archivedNote.getId()).isEqualTo(testNote1.getId());
        assertThat(archivedNote.getTitle()).isEqualTo(testNote1.getTitle());
        assertThat(archivedNote.getContent()).isEqualTo(testNote1.getContent());
    }

    @Test
    public void testWhenUnarchiveNote_thenReturnNote() throws Exception {

        when(archivedNotesRepository.findById(1L)).thenReturn(java.util.Optional.ofNullable(testArchiveEntityNote1));
        when(dtoConverter.archivedNoteEntityToNoteModel(testArchiveEntityNote1)).thenReturn(testNote1);
        when(notesService.createNote(testNote1, principal)).thenReturn(testNote1);

        // Test
        final Note unarchivedNote = archivedNoteService.unarchiveNote(1L, principal);

        assertNotNull(unarchivedNote);
        assertThat(unarchivedNote.getId()).isEqualTo(testNote1.getId());
        assertThat(unarchivedNote.getTitle()).isEqualTo(testNote1.getTitle());
        assertThat(unarchivedNote.getContent()).isEqualTo(testNote1.getContent());
    }
}
