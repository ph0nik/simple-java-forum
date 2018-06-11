package com.phonik.simpleforum.elements.service;

import com.phonik.simpleforum.elements.ForumPost;
import com.phonik.simpleforum.users.GeneralUser;

public interface ResponseService {

    /**
     * adds new reply inside existing forum post
     *
     * @param   postId          identifier of parent post to which reply will be added
     * @param   replyContent    content of reply
     * @param   generalUser     author of new reply
     *
     * @return  parent ForumPost element
     * */
    ForumPost newResponse(int postId, String replyContent, GeneralUser generalUser);

    /**
     * edits given ForumReply element
     *
     * @param   replyId     identifier of reply element to be edited
     * @param   newContent  new content to be put inside existing reply
     * @param   generalUser user that performs action
     *
     * @return  parent ForumPost element
     * */
    ForumPost editResponse(int replyId, String newContent, GeneralUser generalUser);

    /**
     * deletes given ForumReply element
     *
     * @param   replyId     identifier of reply element to be deleted
     * @param   generalUser user that performs action
     *
     * @return  parent ForumPost element
     * */
    ForumPost deleteResponse(int replyId, GeneralUser generalUser);
}
