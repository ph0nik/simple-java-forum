package com.phonik.simpleforum.users;

import org.junit.Test;

public class UserAdminTest {


    @Test
    public void createNewAdmin() {
        GeneralUser admin = new UserAdmin();
        admin.setUserName("admin");
        admin.setUserMail("admin@mymail.com");
        admin.setUserPassword("secret");
        admin.setUserId(0);

        System.out.println(admin);
    }

}