package com.phonik.simpleforum.dao;

import com.phonik.simpleforum.elements.ForumSection;
import com.phonik.simpleforum.elements.service.SectionService;
import com.phonik.simpleforum.exceptions.EmailExistException;
import com.phonik.simpleforum.exceptions.EmptyFieldsException;
import com.phonik.simpleforum.exceptions.UserPrivilegesException;
import com.phonik.simpleforum.users.GeneralUser;
import com.phonik.simpleforum.users.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SectionService.class,
        ForumElementDaoImpl.class,
        UserDaoImpl.class,
        UserService.class})
public class ForumElementDaoImplTest {

    @Autowired
    ForumElementDao forumElementDao;

    @Autowired
    SectionService sectionService;

    @Autowired
    private UserService userService;

    private GeneralUser admin;
    private ForumSection root;
    private long rootId;
    private ForumSection filmy;
    private long filmyId;


    @Before
    public void initAdmin() throws EmptyFieldsException, UserPrivilegesException, EmailExistException {
        admin = userService.createNewAdmin("Skynet Admin", "youAREallGOINGtoDIE", "admin@skynet.com");
        root = sectionService.createRoot("Tytuł forum", "opis forum", admin);
        rootId = forumElementDao.addForumElement(root);
        filmy = sectionService.addNewSection("filmy", "dyskusja o filmach", admin, 0);
        filmyId = forumElementDao.addForumElement(filmy);
    }

    @Test
    public void insertNewSection() throws UserPrivilegesException {
        assertTrue(userService.adminCheck());
        System.out.println(admin);
        ForumSection forumSection = forumElementDao.getForumSection(filmyId);

    }


}