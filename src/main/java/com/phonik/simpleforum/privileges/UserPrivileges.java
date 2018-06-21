package com.phonik.simpleforum.privileges;

import com.phonik.simpleforum.BanTimeCounter;
import com.phonik.simpleforum.users.GeneralUser;
import com.phonik.simpleforum.users.UserType;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "privileges_definition")
public class UserPrivileges implements Serializable {

    //TODO change privileges model to map <scope, PRIV_TYPE>, where
    // PRIV_TYPE (enum) will point to specific set of rules
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "priv_id")
    private int id;
    @Column(name = "priv_usertype")
    private UserType userType;
    @Column(name = "priv_new_post")
    private boolean createNewPost;
    @Column(name = "priv_new_reply")
    private boolean createNewReply;
    @Column(name = "priv_new_section")
    private boolean createNewSection;
    @Column(name = "priv_edit_own_post")
    private boolean editOwnPost;
    @Column(name = "priv_edit_any_post")
    private boolean editAnyPost;
    @Column(name = "priv_delete_post")
    private boolean deletePost;
    @Column(name = "priv_edit_own_reply")
    private boolean editOwnReply;
    @Column(name = "priv_edit_any_reply")
    private boolean editAnyReply;
    @Column(name = "priv_delete_reply")
    private boolean deleteReply;
    @Column(name = "priv_edit_section")
    private boolean editSection;
    @Column(name = "priv_delete_section")
    private boolean deleteSection;
    @ManyToOne//(targetEntity = GeneralUser.class)
    private GeneralUser user;

    public UserPrivileges() {
    }

    public GeneralUser getUser() {
        return user;
    }

    public void setUser(GeneralUser user) {
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isCreateNewPost() {
        return createNewPost;
    }

    public void setCreateNewPost(boolean createNewPost) {
        this.createNewPost = createNewPost;
    }

    public boolean isCreateNewReply() {
        return createNewReply;
    }

    public void setCreateNewReply(boolean createNewReply) {
        this.createNewReply = createNewReply;
    }

    public boolean isCreateNewSection() {
        return createNewSection;
    }

    public void setCreateNewSection(boolean createNewSection) {
        this.createNewSection = createNewSection;
    }

    public boolean isEditOwnPost() {
        return editOwnPost;
    }

    public void setEditOwnPost(boolean editOwnPost) {
        this.editOwnPost = editOwnPost;
    }

    public boolean isEditAnyPost() {
        return editAnyPost;
    }

    public void setEditAnyPost(boolean editAnyPost) {
        this.editAnyPost = editAnyPost;
    }

    public boolean isDeletePost() {
        return deletePost;
    }

    public void setDeletePost(boolean deletePost) {
        this.deletePost = deletePost;
    }

    public boolean isEditOwnReply() {
        return editOwnReply;
    }

    public void setEditOwnReply(boolean editOwnReply) {
        this.editOwnReply = editOwnReply;
    }

    public boolean isEditAnyReply() {
        return editAnyReply;
    }

    public void setEditAnyReply(boolean editAnyReply) {
        this.editAnyReply = editAnyReply;
    }

    public boolean isDeleteReply() {
        return deleteReply;
    }

    public void setDeleteReply(boolean deleteReply) {
        this.deleteReply = deleteReply;
    }

    public boolean isEditSection() {
        return editSection;
    }

    public void setEditSection(boolean editSection) {
        this.editSection = editSection;
    }

    public boolean isDeleteSection() {
        return deleteSection;
    }

    public void setDeleteSection(boolean deleteSection) {
        this.deleteSection = deleteSection;
    }

    @Override
    public String toString() {
        return "UserPrivileges{" +
                "id=" + id +
                ", createNewPost=" + createNewPost +
                ", createNewReply=" + createNewReply +
                ", createNewSection=" + createNewSection +
                ", editOwnPost=" + editOwnPost +
                ", editAnyPost=" + editAnyPost +
                ", deletePost=" + deletePost +
                ", editOwnReply=" + editOwnReply +
                ", editAnyReply=" + editAnyReply +
                ", deleteReply=" + deleteReply +
                ", updateSection=" + editSection +
                ", deleteSection=" + deleteSection +
                ", user=" + user.getUserName() +
                '}';
    }
}
