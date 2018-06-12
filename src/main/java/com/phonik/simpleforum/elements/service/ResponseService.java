package com.phonik.simpleforum.elements.service;

import com.phonik.simpleforum.elements.ForumPost;
import com.phonik.simpleforum.elements.ForumReply;
import com.phonik.simpleforum.exceptions.UserPrivilegesException;
import com.phonik.simpleforum.users.GeneralUser;

public interface ResponseService {

    /**
     * adds new reply inside existing forum post
     *
     * @param   parentPost      parent post to which reply will be added
     * @param   replyContent    content of reply
     * @param   generalUser     author of new reply
     *
     * @return  parent ForumPost element
     * */
    ForumPost addNewReply(ForumPost parentPost, String replyContent, GeneralUser generalUser) throws UserPrivilegesException;


    /**
     * edits given ForumReply element
     *
     * @param   replyId     identifier of reply element to be edited
     * @param   newContent  new content to be put inside existing reply, must be non empty
     * @param   generalUser user that performs action
     *
     * @return  parent ForumPost element
     * */
    ForumPost updateReply(int replyId, String newContent, GeneralUser generalUser) throws UserPrivilegesException;

    /**
     * deletes given ForumReply element
     *
     * @param   replyId     identifier of reply element to be deleted
     * @param   generalUser user that performs action
     *
     * @return  parent ForumPost element
     * */
    ForumPost deleteReply(int replyId, GeneralUser generalUser) throws UserPrivilegesException;

}
