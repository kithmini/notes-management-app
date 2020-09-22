package com.bkitsen.notesapp.persistence.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Getter
@Setter
@Entity
public class ArchivedNote {
    @Id
    private Long id;
    private String title;
    @Column(length=1000)
    private String content;
    private String createdBy;
    private String lastModifiedBy;
    private Date originalCreatedDate;
    @CreatedDate
    private Date archivedDate;
}
