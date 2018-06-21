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
@Transactional
public class PostServiceImpl implements PostService {

    @Autowired
    private ValidateUser valid;

    @Autowired
    private ForumElementDao forumDao;

    @Override
    @Transactional
    public ForumPost addNewPost(String postTitle,
                                String postContent,
                                GeneralUser author,
                                ForumSection parentSection) throws UserPrivilegesException {
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

    @Override
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

    @Override
    @Transactional
    public ForumSection deletePost(int postId, GeneralUser user) throws UserPrivilegesException {
        ForumPost fetchedPost = forumDao.getForumPost(postId);
        long parentId = fetchedPost.getParentSection().getId();
        if (valid.canDeletePost(user, fetchedPost)) {
            forumDao.deleteForumElement(fetchedPost);
            return forumDao.getForumSection(parentId);
        } else {
            throw new UserPrivilegesException("You have no permission to do this.");
        }
    }

    @Override
    @Transactional
    public ForumSection pinPost(int postId, GeneralUser user) throws UserPrivilegesException {
        ForumPost forumPost = forumDao.getForumPost(postId);
        ForumSection parentSection = forumPost.getParentSection();
        if (valid.canEditSection(user, parentSection)) {
            forumPost.setPinned(true);
            forumDao.updateForumElement(forumPost);
            return parentSection;
        } else {
            throw new UserPrivilegesException("You have no permission to do this.");
        }
    }

    @Override
    @Transactional
    public ForumSection unpinPost(int postId, GeneralUser user) throws UserPrivilegesException {
        ForumPost forumPost = forumDao.getForumPost(postId);
        ForumSection parentSection = forumPost.getParentSection();
        if (valid.canEditSection(user, parentSection)) {
            forumPost.setPinned(false);
            forumDao.updateForumElement(forumPost);
            return parentSection;
        } else {
            throw new UserPrivilegesException("You have no permission to do this.");
        }
    }

}
