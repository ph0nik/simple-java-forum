package com.phonik.simpleforum.users;

import com.phonik.simpleforum.elements.ForumRoot;
import com.phonik.simpleforum.privileges.BanService;
import com.phonik.simpleforum.privileges.ModeratorPrivileges;
import com.phonik.simpleforum.users.service.UserService;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.*;

public class GeneralUserTest {

    private UserRegular userRegular;
    private BanService userService;

    @Before
    public void initTest() {
        userRegular = new UserRegular("Donald Duck", "kwack", "donald@disney.com");
        userService = new UserService();
    }

    @Test
    public void testRegularUserParameters() {
        boolean deletePost = userRegular.getPrivilegesForSpecificElement(0).isDeletePost();
        assertEquals(deletePost, false);

    }

    @Test
    public void testModeratorParameters() {
        int forumScope = 12;
        int forumRoot = ForumRoot.FORUM_ROOT_ID;
        UserRegular userModerator = new UserRegular("Piggy", "ilovekermit", "hotty@hensons.com");
        userModerator.setUserPrivileges(new ModeratorPrivileges(forumScope));
        boolean editAnyPost = userModerator.getPrivilegesForSpecificElement(forumScope).isEditAnyPost();
        // check if moderator is allowed to edit posts within given scope
        assertTrue(editAnyPost);

        boolean deleteSection = userModerator.getPrivilegesForSpecificElement(forumScope).isDeleteSection();
        // check if moderator is allowed to delete sections within given scope
        assertTrue(deleteSection);

        boolean deleteTopic = userModerator.getPrivilegesForSpecificElement(forumRoot).isDeleteReply();
        // check if moderator is allowed to delete topics within given root scope
        assertFalse(deleteTopic);
    }

    @Test
    public void promoteRegularUserToModerator() {
        // create new set of privileges
        userRegular.setUserPrivileges(new ModeratorPrivileges(2));
        assertEquals(UserType.MODERATOR, userRegular.getUserType());
    }

    @Test
    public void demoteUserFromModeratorToNormal() {
        // adding moderator privileges to regular user
        userRegular.setUserPrivileges(new ModeratorPrivileges(10));
        // removing given privileges

        userRegular.removePrivileges(10);
        // check if user type is properly attached
        assertEquals(UserType.USER, userRegular.getUserType());

        // check if object has proper class name parameter
        assertEquals("UserRegular", userRegular.getClass().getSimpleName());
    }

    @Test
    public void banAndUnbanRegularUser() {
        // set permanent ban on selected user
        userService.banUser(userRegular);

        // check for right user type
        assertEquals(UserType.BANNED, userRegular.getUserType());
        // release ban
        userService.unbanUser(userRegular);

        // check for the right type
        assertEquals(UserType.USER, userRegular.getUserType());
    }

    @Test
    public void temporalBanRegularUser() {
        int banHours = 24;
        // set temporal ban with lift time
        userService.banUser(userRegular, 0,0,banHours);

        // check if user got properly banned
        assertEquals(UserType.BANNED, userRegular.getUserType());
        LocalDateTime banLift = userRegular.getBanLiftDate();
        // check if banlift time is different than
        assertNotEquals(banLift, LocalDateTime.now());

    }
}