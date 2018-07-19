package com.phonik.simpleforum.elements.service;

import com.phonik.simpleforum.dao.ForumElementDao;
import com.phonik.simpleforum.elements.ForumPost;
import com.phonik.simpleforum.elements.ForumSection;
import com.phonik.simpleforum.exceptions.UserPrivilegesException;
import com.phonik.simpleforum.privileges.service.ValidateUser;
import com.phonik.simpleforum.users.GeneralUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PostService {

    @Autowired
    private ValidateUser valid;

    @Autowired
    private ForumElementDao forumDao;

    @Autowired
    private SectionService sectionService;

    /**
     * get post by identifier
     * */
    public ForumPost getPost(int id) {
        return forumDao.getForumPost(id);
    }

    /**
     * adds new forum post
     *
     * @param   postTitle   title of new post
     * @param   postContent content of new post
     * @param   author      author of new section
     * @param   parentSectionId   parent section of element
     *
     * @return  newly addred ForumPost element
     * */
    @Transactional
    public ForumPost addNewPost(String postTitle,
                                String postContent,
                                GeneralUser author,
                                int parentSectionId) throws UserPrivilegesException {
        ForumSection parentSection = sectionService.getSection(parentSectionId);
        ForumPost post;
        if (valid.canCreateNewPost(author, parentSection)) {
            post = new ForumPost(parentSection, postTitle, postContent, author);
            long i = forumDao.addForumElement(post);
            post.setId(i);
            return post;
        } else {
            throw new UserPrivilegesException("You have no permission to do this.");
        }
    }

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
    @Transactional
    public ForumPost updatePost(int postId,
                                String postTitle,
                                String postContent,
                                GeneralUser user) throws UserPrivilegesException {
        ForumPost forumPost = forumDao.getForumPost(postId);
        if (valid.canEditPost(user, forumPost)) {
            forumPost.setContent(postContent);
            forumPost.setTitle(postTitle);
            forumPost.setEditor(user);
            forumDao.updateForumElement(forumPost);
            return forumPost;
        } else {
            throw new UserPrivilegesException("You have no permission to do this.");
        }
    }

    /**
     * deletes existing forum post
     *
     * @param   postId      identifier of post to be deleted
     * @param   user        user that performs action
     *
     * @return  parent ForumSection element
     * */
    @Transactional
    public ForumSection deletePost(int postId, GeneralUser user) throws UserPrivilegesException {
        ForumPost fetchedPost = forumDao.getForumPost(postId);
        long parentId = fetchedPost.getParentElement().getId();
        if (valid.canDeletePost(user, fetchedPost)) {
            forumDao.deleteForumElement(fetchedPost);
            return forumDao.getForumSection(parentId);
        } else {
            throw new UserPrivilegesException("You have no permission to do this.");
        }
    }

    /**
     * Flags given ForumPost element to be pinned on top of the parent section, pinned elements are shown
     * at the top of section and sorted separately from rest of the section children
     *
     * @param   postId  identifier of post to be pinned
     * @param   user    user that performs action
     *
     * @return  parent ForumSection element
     * */
    @Transactional
    public ForumSection pinPost(int postId, GeneralUser user) throws UserPrivilegesException {
        ForumPost forumPost = forumDao.getForumPost(postId);
        ForumSection parentSection = forumPost.getParentElement();
        if (valid.canEditSection(user, parentSection)) {
            forumPost.setPinned(true);
            forumDao.updateForumElement(forumPost);
            return parentSection;
        } else {
            throw new UserPrivilegesException("You have no permission to do this.");
        }
    }

    /**
     * Flags given ForumPost element to be unpinned from the parent section
     *
     * @param   postId  identifier of post to be unpinned
     * @param   user    user that performs action
     *
     * @return  parent section element
     * */
    @Transactional
    public ForumSection unpinPost(int postId, GeneralUser user) throws UserPrivilegesException {
        ForumPost forumPost = forumDao.getForumPost(postId);
        ForumSection parentSection = forumPost.getParentElement();
        if (valid.canEditSection(user, parentSection)) {
            forumPost.setPinned(false);
            forumDao.updateForumElement(forumPost);
            return parentSection;
        } else {
            throw new UserPrivilegesException("You have no permission to do this.");
        }
    }

}
