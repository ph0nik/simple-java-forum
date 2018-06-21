package com.phonik.simpleforum.elements;

import com.phonik.simpleforum.elements.service.SectionService;
import com.phonik.simpleforum.exceptions.UserPrivilegesException;
import com.phonik.simpleforum.privileges.service.ValidateUser;
import com.phonik.simpleforum.privileges.service.ValidateUserImpl;
import com.phonik.simpleforum.users.GeneralUser;
import com.phonik.simpleforum.users.UserType;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AbstractForumElementTest {

    private GeneralUser admin;
    private ValidateUser validateUser;
    private SectionService sectionService;

    @Before
    public void initTestSuite() {
        admin = new GeneralUser();
        admin.setUserType(UserType.ADMIN);
//        admin.setUserPrivileges(new AdminPrivileges());
        admin.setUserName("Forum Admin");
        admin.setUserPassword("very_secret");
        admin.setUserMail("boss@ms.com");
        validateUser = new ValidateUserImpl();
        sectionService = new SectionService();
    }


    @Test
    public void checkParentsLists() {
        // create forum root
        ForumSection root = new ForumSection();
        root.setId(ForumRoot.FORUM_ROOT_ID);
        root.setTitle("Forum title");
        root.setDescription("root of forum");
        root.setAuthor(admin);
        root.setParentElement(root);
        root.addCurrentToElementPath();

        int i = ForumRoot.FORUM_ROOT_ID + 1;
        // add two forum sections that belongs to root
        ForumSection filmy = new ForumSection("Filmy", "dyskusje na temat filmów", admin, root);
        filmy.setId(i);
        filmy.addCurrentToElementPath();

        i++;
        ForumSection seriale = new ForumSection("Seriale", "dyskusje na temat seriali", admin, root);
        seriale.setId(i);

        i++;
        // add two sections that belongs to Filmy section
        ForumSection horrory = new ForumSection("Horrory", "dział horrorów", admin, filmy);
        horrory.setId(i);
        i++;
        ForumSection komedie = new ForumSection("Komedie", "dział komedii", admin, filmy);
        komedie.setId(i);

        i++;
        // add nested section that belongs to "Komedie amerykanskie" section
        ForumSection komedieAmerykanskie = new ForumSection("Komedie amerykanskie", "wylacznie komedie amerykanskie", admin, komedie);
        komedieAmerykanskie.setId(i);
        i++;

        ForumPost matrix = new ForumPost(filmy, "matrix", "dyskusja o filmie", admin);
        matrix.setId(i);
        i++;
        ForumReply matrix1 = new ForumReply(matrix, "całkiem niezły film", admin);
        matrix1.setId(i);
        i++;
        ForumReply matrix2 = new ForumReply(matrix, "nie zgadzam się z powyższą opinią", admin);
        matrix2.setId(i);

        // checks if nested Reply element is child to given Section element
        assertTrue(matrix2.getParents().contains(filmy.getId()));

        // checks if nested element is not child to given section element
        assertFalse(matrix1.getParents().contains(seriale.getId()));

        GeneralUser user = new GeneralUser();

        // does regular user can create new section
        assertFalse(validateUser.canCreateNewSection(user, filmy));

        // promote user to moderator within given forum scope
//        user.setUserPrivileges(new ModeratorPrivileges(filmy.getId()));

        // check if he can now create sections
        assertTrue(validateUser.canCreateNewSection(user, filmy));
        assertTrue(validateUser.canDeleteReply(user, matrix1));

        // demote user given forum scope
//        user.removePrivileges(filmy);
        assertFalse(validateUser.canCreateNewSection(user, filmy));
    }

    @Test
    public void sectionFactory() throws UserPrivilegesException {
        ForumSection root = sectionService.createRoot("Tytuł forum", "opis forum", admin);
        sectionService.addNewSection("filmy", "dyskusja o filmach", admin, 0);
    }

}