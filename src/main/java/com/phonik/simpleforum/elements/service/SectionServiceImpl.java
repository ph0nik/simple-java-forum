package com.phonik.simpleforum.elements.service;

import com.phonik.simpleforum.dao.ForumElementDao;
import com.phonik.simpleforum.elements.ForumRoot;
import com.phonik.simpleforum.elements.ForumSection;
import com.phonik.simpleforum.exceptions.UserPrivilegesException;
import com.phonik.simpleforum.privileges.service.ValidateUser;
import com.phonik.simpleforum.privileges.service.ValidateUserImpl;
import com.phonik.simpleforum.users.GeneralUser;
import com.phonik.simpleforum.users.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class SectionServiceImpl implements SectionService {

    private ValidateUser valid;

    @Autowired
    private ForumElementDao forumDao;

    public SectionServiceImpl() {
        valid = new ValidateUserImpl();
    }

    // TODO set apart root section, either store it in separate table, or add enum to point to it
    @Override
    public ForumSection getRootSection() {
        ForumSection root = null;
        try {
            root = forumDao.getForumSection(1);
        } catch (NoSuchElementException ex) {
            System.out.println(ex.getMessage());
        }
        return root;
    }

    @Override
    public ForumSection createRoot(String rootTitle,
                                   String rootDescription,
                                   GeneralUser user) throws UserPrivilegesException {
        ForumSection root;
        if (user.getUserType() == UserType.ADMIN) {
            root = new ForumSection();
            root.setAuthor(user);
            root.setId(ForumRoot.FORUM_ROOT_ID);
            root.setTitle(rootTitle);
            root.setDescription(rootDescription);
            root.setParentElement(root);

        } else {
            throw new UserPrivilegesException("You have no permission to do this.");
        }
        return root;
    }

    @Override
    public ForumSection addNewSection(String sectionTitle,
                                      String sectionDescription,
                                      GeneralUser user,
                                      ForumSection forumSection) throws UserPrivilegesException {
        ForumSection section;
        if (valid.canCreateNewSection(user, forumSection)) {
            section = new ForumSection(sectionTitle, sectionDescription, user, forumSection);
        } else {
            throw new UserPrivilegesException("You have no permission to do this.");
        }
        return section;
    }

    @Override
    public ForumSection deleteSection(int sectionId,
                                      GeneralUser user,
                                      ForumSection forumSection) {
        if (valid.canDeleteSection(user, forumSection)) {
            // TODO
        }
        return null;
    }

    @Override
    public ForumSection editSection(int sectionId,
                                    GeneralUser user,
                                    ForumSection forumSection) {
        return null;
    }
}
