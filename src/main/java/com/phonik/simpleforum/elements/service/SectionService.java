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
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
//@Transactional
public class SectionService {

    @Autowired
    private ValidateUser valid;

    @Autowired
    private ForumElementDao forumDao;

//    public SectionService() {
//        valid = new ValidateUserImpl();
//    }

    @Transactional
    public ForumSection getRootSection() {
        return forumDao.getRoot();
    }

    /**
     * Returns section with given identifier
     * */
    @Transactional
    public ForumSection getSection(long id) {
        return forumDao.getForumSection(id);
    }

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
                long i = forumDao.addForumElement(root);
                root.setId(i);
            } else {
                throw new UserPrivilegesException("You have no permission to do this.");
            }
        }
        return root;
    }

    /**
     * adds new ForumSection element
     *
     * @param   sectionTitle        title of new section
     * @param   sectionDescription  description of new section
     * @param   parentSectionId     parent forum section identifier
     * @param   user                user that performs action
     *
     * @return  newly created ForumSection element
     * */

    @Transactional
    public ForumSection addNewSection(String sectionTitle,
                                      String sectionDescription,
                                      GeneralUser user,
                                      long parentSectionId) throws UserPrivilegesException {
        ForumSection section;
        ForumSection parent = getSection(parentSectionId);
        if (valid.canCreateNewSection(user, parent)) {
            section = new ForumSection(sectionTitle, sectionDescription, user, parent);
            long i = forumDao.addForumElement(section);
            section.setId(i);
        } else {
            throw new UserPrivilegesException("You have no permission to do this.");
        }
        return section;
    }

    /**
     * edits ForumSection element
     *
     * @param   user        user that performs action
     * @param   forumSection parent forum section
     *
     * @return  ForumSection element that was edited
     * */

    @Transactional
    public ForumSection updateSection(ForumSection forumSection, GeneralUser user) throws UserPrivilegesException {
        if (valid.canEditSection(user, forumSection)) {
            forumDao.updateForumElement(forumSection);
            return forumDao.getForumSection(forumSection.getId());
        } else {
            throw new UserPrivilegesException("You have no permission to do this.");
        }
    }

    /**
     * deletes ForumSection element
     *
     * @param   sectionId   identifier of section to delete
     * @param   user        user that performs action
     *
     * @return  parent ForumSection element
     * */

    @Transactional
    public ForumSection deleteSection(int sectionId,
                              GeneralUser user) throws UserPrivilegesException {
        ForumSection fetchedSectionId = forumDao.getForumSection(sectionId);
        long parentId = fetchedSectionId.getParentElement().getId();
        forumDao.deleteForumElement(fetchedSectionId);
        return forumDao.getForumSection(parentId);
    }

}
