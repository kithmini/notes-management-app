package com.bkitsen.notesapp;

import com.bkitsen.notesapp.models.Note;
import com.bkitsen.notesapp.persistence.repository.NotesRepository;
import com.bkitsen.notesapp.service.NotesService;
import com.bkitsen.notesapp.utility.DtoConverter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
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
@SpringBootTest(classes = {NotesAppApplication.class, NotesService.class})

public class NotesServiceTest {

    @Autowired
    private NotesService notesService;
    @MockBean
    private DtoConverter dtoConverter;
    @MockBean
    private NotesRepository notesRepository;

    private String date1;
    private String date2;

    private Note testNote1;
    private Note testNote2;
    private com.bkitsen.notesapp.persistence.entity.Note testEntityNote1;
    private com.bkitsen.notesapp.persistence.entity.Note testEntityNote2;
    private List<Note> returnList;
    private List<com.bkitsen.notesapp.persistence.entity.Note> entityList;
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

        testEntityNote1 = new com.bkitsen.notesapp.persistence.entity.Note();
        testEntityNote1.setId(1L);
        testEntityNote1.setTitle("Test Title 1");
        testEntityNote1.setTitle("Test content body 1");
        testEntityNote1.setCreatedDate(fmt.parse(date1));
        testEntityNote1.setCreatedBy("admin");

        testEntityNote2 = new com.bkitsen.notesapp.persistence.entity.Note();
        testEntityNote2.setId(2L);
        testEntityNote2.setTitle("Test Title 2");
        testEntityNote2.setTitle("Test content body 2");
        testEntityNote2.setCreatedDate(fmt.parse(date2));
        testEntityNote2.setCreatedBy("admin");

        returnList = new ArrayList<>();
        returnList.add(testNote1);
        returnList.add(testNote2);

        entityList = new ArrayList<>();
        entityList.add(testEntityNote1);
        entityList.add(testEntityNote2);

        principal = () -> "admin";

    }

    @Test
    public void testWhenGetAllNotes_thenReturnNoteList() throws Exception {

        when(notesRepository.findAll()).thenReturn(entityList);
        when(dtoConverter.notesEntityToModel(testEntityNote1)).thenReturn(testNote1);
        when(dtoConverter.notesEntityToModel(testEntityNote2)).thenReturn(testNote2);

        // Test
        final List<Note> noteList = notesService.getAllNotes();

        assertEquals(2, noteList.size());

        verify(notesRepository, times(1)).findAll();
    }

    @Test
    public void testWhenCreateNote_thenReturnNote() throws Exception {

        when(dtoConverter.notesCreateModelToEntity(testNote1)).thenReturn(testEntityNote1);
        when(notesRepository.save(testEntityNote1)).thenReturn(testEntityNote1);
        when(dtoConverter.notesEntityToModel(testEntityNote1)).thenReturn(testNote1);

        // Test
        final Note note = notesService.createNote(testNote1, principal);

        assertNotNull(note);
        assertThat(note.getId()).isEqualTo(testNote1.getId());
        assertThat(note.getTitle()).isEqualTo(testNote1.getTitle());
        assertThat(note.getContent()).isEqualTo(testNote1.getContent());
        assertThat(note.getCreatedDate()).isEqualTo(testNote1.getCreatedDate());
    }

    @Test
    public void testWhenGetNoteById_thenReturnNote() throws Exception {

        when(notesRepository.findById(1L)).thenReturn(java.util.Optional.ofNullable(testEntityNote1));
        when(dtoConverter.notesEntityToModel(testEntityNote1)).thenReturn(testNote1);

        // Test
        final Note note = notesService.getNoteById(1L);

        assertNotNull(note);
        assertThat(note.getId()).isEqualTo(testNote1.getId());
        assertThat(note.getTitle()).isEqualTo(testNote1.getTitle());
        assertThat(note.getContent()).isEqualTo(testNote1.getContent());
        assertThat(note.getCreatedDate()).isEqualTo(testNote1.getCreatedDate());
    }

    @Test
    public void testWhenUpdateNote_thenReturnNote() throws Exception {

        when(notesRepository.findById(1L)).thenReturn(java.util.Optional.ofNullable(testEntityNote1));
        when(dtoConverter.notesUpdateModelToEntity(testNote1, testEntityNote1)).thenReturn(testEntityNote1);
        when(notesRepository.save(testEntityNote1)).thenReturn(testEntityNote1);
        when(dtoConverter.notesEntityToModel(testEntityNote1)).thenReturn(testNote1);

        // Test
        final Note note = notesService.updateNote(1L, testNote1, principal);

        assertNotNull(note);
        assertThat(note.getId()).isEqualTo(testNote1.getId());
        assertThat(note.getTitle()).isEqualTo(testNote1.getTitle());
        assertThat(note.getContent()).isEqualTo(testNote1.getContent());
        assertThat(note.getCreatedDate()).isEqualTo(testNote1.getCreatedDate());
    }

    @Test
    public void testDeleteNoteById() throws Exception {
        notesService.deleteNote(1L);
        Mockito.verify(notesRepository, Mockito.times(1)).deleteById(1L);
    }

}
