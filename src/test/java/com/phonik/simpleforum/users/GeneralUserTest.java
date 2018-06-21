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

    private GeneralUser userRegular;
    private BanService userService;

    @Before
    public void initTest() {
        userRegular = new GeneralUser("Donald Duck", "kwack", "donald@disney.com");
        userService = new UserService();
    }



}