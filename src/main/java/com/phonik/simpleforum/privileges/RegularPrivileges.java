package com.phonik.simpleforum.privileges;

import com.phonik.simpleforum.elements.ForumRoot;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "PRIVILEGES")
public class RegularPrivileges extends UserPrivileges {

    // TODO update new parameters
    public RegularPrivileges() {
        super(ForumRoot.FORUM_ROOT_ID);
        setCreateNewPost(true);
        setCreateNewSection(false);
        setCreateNewReply(true);

        setDeletePost(false);
        setDeleteSection(false);
        setDeleteReply(false);

        setEditAnyPost(false);
        setEditAnyReply(false);

        setEditOwnPost(true);
        setEditSection(false);
        setEditOwnReply(true);
    }
}
