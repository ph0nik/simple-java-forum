package com.phonik.simpleforum.privileges;

import com.phonik.simpleforum.elements.ForumRoot;

import javax.persistence.Entity;

@Entity
public class AdminPrivileges extends UserPrivileges {

    public AdminPrivileges() {

        super();
        setCreateNewPost(true);
        setDeletePost(true);
        setEditAnyPost(true);
        setEditOwnPost(true);

        setCreateNewSection(true);
        setDeleteSection(true);
        setEditSection(true);

        setCreateNewReply(true);
        setDeleteReply(true);
        setEditAnyReply(true);
        setEditOwnReply(true);
    }

}
