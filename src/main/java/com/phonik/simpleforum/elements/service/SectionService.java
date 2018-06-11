package com.phonik.simpleforum.elements.service;

import com.phonik.simpleforum.elements.ForumSection;
import com.phonik.simpleforum.exceptions.UserPrivilegesException;
import com.phonik.simpleforum.users.GeneralUser;

public interface SectionService {


    ForumSection getRootSection();

    ForumSection createRoot(String rootTitle, String rootDescription, GeneralUser user) throws UserPrivilegesException;

    /**
     * adds new ForumSection element
     *
     * @param   sectionTitle        title of new section
     * @param   sectionDescription  description of new section
     * @param   forumSection        parent forum section
     * @param   user                user that performs action
     *
     * @return  newly created ForumSection element
     * */
    ForumSection addNewSection(String sectionTitle, String sectionDescription, GeneralUser user, ForumSection forumSection) throws UserPrivilegesException;

    /**
     * deletes ForumSection element
     *
     * @param   sectionId   identifier of section to delete
     * @param   forumSection parent forum section
     * @param   user        user that performs action
     *
     * @return  parent ForumSection element
     * */
    ForumSection deleteSection(int sectionId, GeneralUser user, ForumSection forumSection);

    /**
     * edits ForumSection element
     *
     * @param   sectionId   identifier of section to delete
     * @param   user        user that performs action
     * @param   forumSection parent forum section
     *
     * @return  ForumSection element that was edited
     * */
    ForumSection editSection(int sectionId, GeneralUser user, ForumSection forumSection);
}
