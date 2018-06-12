package com.phonik.simpleforum.privileges.service;

import com.phonik.simpleforum.elements.*;
import com.phonik.simpleforum.users.GeneralUser;
import org.springframework.stereotype.Component;

public interface ValidateUser {

    /**
     * Validates if user is allowed to create new section within given section
     *
     * @param   user    user to perform action
     * @param   forumSection    parent forum section element
     *
     * @return  true if user is allowed to do so, false otherwise
     * */
    public boolean canCreateNewSection(GeneralUser user, ForumSection forumSection);

    public boolean canEditSection(GeneralUser user, ForumSection forumSection);

    public boolean canDeleteSection(GeneralUser user, ForumSection forumSection);

    public boolean canCreateNewPost(GeneralUser user, ForumSection forumSection);

    public boolean canDeletePost(GeneralUser user, ForumPost forumPost);

    public boolean canEditPost(GeneralUser user, ForumPost forumPost);

    public boolean canCreateNewReply(GeneralUser user, ForumPost forumPost);

    public boolean canDeleteReply(GeneralUser user, ForumReply forumReply);

    public boolean canEditReply(GeneralUser user, ForumReply forumReply);


}
