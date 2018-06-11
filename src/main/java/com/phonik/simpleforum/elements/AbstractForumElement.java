package com.phonik.simpleforum.elements;

import com.phonik.simpleforum.users.GeneralUser;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;

@MappedSuperclass
public abstract class AbstractForumElement implements Serializable {

    // Map of paired element id and element title
    // all elements of collection in ascending order / by creation date
    @Column(name = "element_pinned")
    private boolean pinned; // sets element as pinned
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "element_id", unique = true, nullable = false)
    private int id; // element id

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Fetch(FetchMode.SELECT)
    @PrimaryKeyJoinColumn
    private GeneralUser author; // element author


    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Fetch(FetchMode.SELECT)
    @PrimaryKeyJoinColumn
    private GeneralUser editor; // element editor

    @Column(name = "element_creation_date")
    private LocalDateTime creationDate;
    @Column(name = "element_edit_date")
    private LocalDateTime editDate;

    protected AbstractForumElement() {
        creationDate = LocalDateTime.now();

    }

    public abstract Set<Integer> getParents();

    public GeneralUser getEditor() {
        return editor;
    }

    public void setEditor(GeneralUser editor) {
        this.editor = editor;
    }

    public LocalDateTime getEditDate() {
        return editDate;
    }

    public void setEditDate(LocalDateTime editDate) {
        this.editDate = editDate;
    }

    public boolean isPinned() {
        return pinned;
    }

    public void setPinned(boolean pinned) {
        this.pinned = pinned;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public GeneralUser getAuthor() {
        return author;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setAuthor(GeneralUser author) {
        this.author = author;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

}
