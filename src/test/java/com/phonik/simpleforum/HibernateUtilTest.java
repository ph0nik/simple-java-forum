package com.phonik.simpleforum;

import com.phonik.simpleforum.dao.UserDao;
import com.phonik.simpleforum.exceptions.EmailExistException;
import com.phonik.simpleforum.exceptions.EmptyFieldsException;
import com.phonik.simpleforum.privileges.BanService;
import com.phonik.simpleforum.users.GeneralUser;
import com.phonik.simpleforum.users.service.UserService;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class) // lets run test with spring
// points to class that has to be autowired or to configuration class that points to other classess
@ContextConfiguration(classes = {UserService.class})
public class HibernateUtilTest {

    private SessionFactory sf;
    @Autowired
    private UserDao userDao;
    @Autowired
    private UserService userService;
    @Autowired
    private BanService banService;

    @Before
    public void setUpSession() {
        sf = HibernateUtil.getSessionFactory();
    }

    @Test
    public void createDbAndInsertData() {
        // create sample user
        GeneralUser regularUser = new GeneralUser();
        regularUser.setUserName("Tom");
        regularUser.setUserMail("bee@hive.com");
        regularUser.setUserPassword("unknown");

        // insert user into database
        userDao.addUser(regularUser);

        // retrieve user
        GeneralUser userReturned = (GeneralUser) userDao.selectUser(1);

        // check if user is allowed to delete posts
        boolean deletePost = userReturned.getPrivilegesForSpecificElement(0).isDeletePost();
        assertEquals(false, deletePost);
    }

    @Test
    public void insertUserWithService() throws EmptyFieldsException, EmailExistException {
        GeneralUser newAdmin = userService.createNewAdmin("Skynet Admin", "youAREallGOINGtoDIE", "admin@skynet.com");
        GeneralUser user = userDao.selectUser(newAdmin.getUserId());
        System.out.println("admin type: " + user.getUserPrivilegesMap().get(1).getUser().getClass().getSimpleName());

        GeneralUser insertedUser = userService.createNewUser("Terminator", "K1llJ0hnConnor", "destroy@skynet.com");
        GeneralUser selectedUser = userDao.selectUser(insertedUser.getUserId());
        assertFalse(selectedUser.isPermanentlyBanned());
        banService.banUser(selectedUser);
        selectedUser = userDao.selectUser(insertedUser.getUserId());
        assertTrue(selectedUser.isPermanentlyBanned());
    }

    @Test
    public void updateUserProperties() throws EmptyFieldsException, EmailExistException {
        GeneralUser insertedUser = userService.createNewUser("Mickey Mouse", "iLOVEcheese", "mickey@disney.com");
        GeneralUser selectUser = userDao.selectUser(insertedUser.getUserId());
        assertEquals(selectUser.getUserPassword(), "iLOVEcheese");
        userService.changePassword(selectUser, "iLOVEcheese", "iHATEcheese");

        selectUser = userDao.selectUser(selectUser.getUserId());
        assertNotEquals(selectUser.getUserPassword(), "iLOVEcheese");
    }



}