package com.phonik.simpleforum.elements.service;

import com.phonik.simpleforum.dao.ForumElementDao;
import com.phonik.simpleforum.elements.ElementType;
import com.phonik.simpleforum.elements.ForumPost;
import com.phonik.simpleforum.elements.ForumReply;
import com.phonik.simpleforum.elements.ForumSection;
import com.phonik.simpleforum.exceptions.UserPrivilegesException;
import com.phonik.simpleforum.privileges.service.ValidateUser;
import com.phonik.simpleforum.privileges.service.ValidateUserImpl;
import com.phonik.simpleforum.users.GeneralUser;
import com.phonik.simpleforum.users.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@Transactional
public class SectionServiceImpl implements SectionService {

    @Autowired
    private ValidateUser valid;

    @Autowired
    private ForumElementDao forumDao;

//    public SectionServiceImpl() {
//        valid = new ValidateUserImpl();
//    }

    @Override
    @Transactional
    public ForumSection getRootSection() {
        return forumDao.getRoot();
    }

    @Override
    @Transactional
    public ForumSection createRoot(String rootTitle,
                                   String rootDescription,
                                   GeneralUser user) throws UserPrivilegesException {
        ForumSection root = null;
        if (getRootSection() == null) {
            if (user.getUserType() == UserType.ADMIN) {
                root = new ForumSection();
                root.setAuthor(user);
                root.setElementType(ElementType.ROOT);
                root.setTitle(rootTitle);
                root.setDescription(rootDescription);
                root.setParentElement(root);
                int i = forumDao.addForumElement(root);
                root.setId(i);
            } else {
                throw new UserPrivilegesException("You have no permission to do this.");
            }
        }
        return root;
    }

    @Override
    @Transactional
    public ForumSection addNewSection(String sectionTitle,
                                      String sectionDescription,
                                      GeneralUser user,
                                      ForumSection parentSection) throws UserPrivilegesException {
        ForumSection section;
        if (valid.canCreateNewSection(user, parentSection)) {
            section = new ForumSection(sectionTitle, sectionDescription, user, parentSection);
            int i = forumDao.addForumElement(section);
            section.setId(i);
        } else {
            throw new UserPrivilegesException("You have no permission to do this.");
        }
        return section;
    }

    @Override
    @Transactional
    public ForumSection updateSection(ForumSection forumSection, GeneralUser user) throws UserPrivilegesException {
        if (valid.canEditSection(user, forumSection)) {
            forumDao.updateForumElement(forumSection);
            return forumDao.getForumSection(forumSection.getId());
        } else {
            throw new UserPrivilegesException("You have no permission to do this.");
        }
    }


    @Override
    @Transactional
    public ForumSection deleteSection(int sectionId,
                              GeneralUser user) throws UserPrivilegesException {
        ForumSection fetchedSectionId = forumDao.getForumSection(sectionId);
        int parentId = fetchedSectionId.getParentElement().getId();
        if (valid.canDeleteSection(user, fetchedSectionId.getParentElement())) {
            forumDao.deleteForumElement(fetchedSectionId);
            return forumDao.getForumSection(parentId);
        } else {
            throw new UserPrivilegesException("You have no permission to do this.");
        }
    }

}
