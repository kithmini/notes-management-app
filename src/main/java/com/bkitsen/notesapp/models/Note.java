package com.bkitsen.notesapp.models;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Note {
    private Long id;
    private String title;
    private String content;
    private String createdBy;
    private String lastModifiedBy;
    private Date createdDate;
    private Date lastModifiedDate;
}
