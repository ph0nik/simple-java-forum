package com.phonik.simpleforum.privileges;

import com.phonik.simpleforum.elements.ForumRoot;

public class AdminPrivileges extends UserPrivileges {

    public AdminPrivileges() {

        super(ForumRoot.FORUM_ROOT_ID);
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
