package com.phonik.simpleforum.elements.service;

import com.phonik.simpleforum.dao.ForumElementDao;
import com.phonik.simpleforum.elements.ForumPost;
import com.phonik.simpleforum.elements.ForumReply;
import com.phonik.simpleforum.exceptions.UserPrivilegesException;
import com.phonik.simpleforum.privileges.service.ValidateUser;
import com.phonik.simpleforum.users.GeneralUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ResponseServiceImpl implements ResponseService {

    @Autowired
    private ValidateUser valid;

    @Autowired
    private ForumElementDao forumDao;

    @Override
    @Transactional
    public ForumPost addNewReply(ForumPost parentPost, String replyContent, GeneralUser user) throws UserPrivilegesException {
        ForumReply reply;
        if (valid.canCreateNewReply(user, parentPost)) {
            reply = new ForumReply(parentPost, replyContent, user);
            long i = forumDao.addForumElement(reply);
            reply.setId(i);
            return parentPost;
        } else {
            throw new UserPrivilegesException("You have no permission to do this.");
        }
    }

    @Override
    @Transactional
    public ForumPost updateReply(int replyId, String newContent, GeneralUser user) throws UserPrivilegesException {
        ForumReply forumReply = forumDao.getForumReply(replyId);
        if (valid.canEditReply(user, forumReply)) {
            forumReply.setContent(newContent);
            forumReply.setEditor(user);
            forumDao.updateForumElement(forumReply);
            return forumDao.getForumPost(forumReply.getParentPost().getId());
        } else {
            throw new UserPrivilegesException("You have no permission to do this.");
        }
    }

    @Override
    @Transactional
    public ForumPost deleteReply(int replyId, GeneralUser user) throws UserPrivilegesException {
        ForumReply fetchedReply = forumDao.getForumReply(replyId);
        long parentId = fetchedReply.getParentPost().getId();
        if (valid.canDeleteReply(user, fetchedReply)) {
            forumDao.deleteForumElement(fetchedReply);
            return forumDao.getForumPost(parentId);
        } else {
            throw new UserPrivilegesException("You have no permission to do this.");
        }
    }
}
