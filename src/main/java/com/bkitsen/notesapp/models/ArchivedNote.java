package com.bkitsen.notesapp.models;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ArchivedNote {
    private Long id;
    private String title;
    private String content;
    private String createdBy;
    private String lastModifiedBy;
    private Date originalCreatedDate;
    private Date archivedDate;
}
