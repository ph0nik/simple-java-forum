package com.phonik.simpleforum.elements.service;

import com.phonik.simpleforum.elements.ForumPost;
import com.phonik.simpleforum.elements.ForumSection;
import com.phonik.simpleforum.exceptions.UserPrivilegesException;
import com.phonik.simpleforum.users.GeneralUser;

public interface PostService {

    /**
     * adds new forum post
     *
     * @param   postTitle   title of new post
     * @param   postContent content of new post
     * @param   author      author of new section
     * @param   parentSection   parent section of element
     *
     * @return  newly addred ForumPost element
     * */
    ForumPost addNewPost(String postTitle, String postContent, GeneralUser author, ForumSection parentSection) throws UserPrivilegesException;

    /**
     * edits existing ForumPost element
     *    <p>
     *     Given id number of post to be edited changes are made to existing object
     *    if postTitle or postContent parameters are empty strings, those fields are
     *    omitted
     *    </p>
     *
     * @param   postId      identifier for post to be edited
     * @param   postTitle   new title for existing post
     * @param   postContent new content for existing post
     * @param   user        editor of given element
     *
     * @return  edited ForumPost element
     * */
    ForumPost updatePost(int postId, String postTitle, String postContent, GeneralUser user) throws UserPrivilegesException;

    /**
     * deletes existing forum post
     *
     * @param   postId      identifier of post to be deleted
     * @param   user        user that performs action
     *
     * @return  parent ForumSection element
     * */
    ForumSection deletePost(int postId, GeneralUser user) throws UserPrivilegesException;

    /**
     * Flags given ForumPost element to be pinned on top of the parent section, pinned elements are shown
     * at the top of section and sorted separately from rest of the section children
     *
     * @param   postId  identifier of post to be pinned
     * @param   user    user that performs action
     *
     * @return  parent ForumSection element
     * */
    ForumSection pinPost(int postId, GeneralUser user) throws UserPrivilegesException;

    /**
     * Flags given ForumPost element to be unpinned from the parent section
     *
     * @param   postId  identifier of post to be unpinned
     * @param   user    user that performs action
     *
     * @return  parent section element
     * */
    ForumSection unpinPost(int postId, GeneralUser user) throws UserPrivilegesException;

}
