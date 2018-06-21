package com.phonik.simpleforum.privileges;

public class ModeratorPrivileges extends UserPrivileges {

    public ModeratorPrivileges() {
        super();
        setCreateNewPost(true);
        setCreateNewSection(true);
        setCreateNewReply(true);

        setDeletePost(true);
        setDeleteSection(true);
        setDeleteReply(true);

        setEditAnyPost(true);
        setEditAnyReply(true);

        setEditOwnPost(true);
        setEditSection(true);
        setEditOwnReply(true);
    }

}
