package com.phonik.simpleforum.elements;

import com.phonik.simpleforum.users.GeneralUser;
import org.hibernate.annotations.Cascade;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "element_id", unique = true, nullable = false)
    private long id; // element id

    @Column(name = "element_type")
    @Enumerated(EnumType.STRING)
    private ElementType elementType;

    @ManyToOne(fetch = FetchType.LAZY)
//    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
////    @Fetch(FetchMode.JOIN)
//    @PrimaryKeyJoinColumn
    @JoinColumn(name = "author_user_id", referencedColumnName = "user_id", updatable = false, insertable = false)
    private GeneralUser author; // element author

    @ManyToOne(fetch = FetchType.LAZY)
//    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
//    @PrimaryKeyJoinColumn
    @JoinColumn(name = "editor_user_id", referencedColumnName = "user_id", updatable = false, insertable = false)
    private GeneralUser editor; // element editor

    @Column(name = "element_creation_date")
    private LocalDateTime creationDate;
    @Column(name = "element_edit_date")
    private LocalDateTime editDate;

    protected AbstractForumElement() {
        creationDate = LocalDateTime.now();
    }

    public ElementType getElementType() {
        return elementType;
    }

    public void setElementType(ElementType elementType) {
        this.elementType = elementType;
    }

    public abstract Set<Long> getParents();

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

    public long getId() {
        return id;
    }

    public void setId(long id) {
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
