package com.phonik.simpleforum.users;

import com.phonik.simpleforum.privileges.RegularPrivileges;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;
@Deprecated
//@Entity(name = "UserRegular")
public class UserRegular extends GeneralUser {

    public UserRegular() {
        super();
        setUserType(UserType.USER);
        setChildClassName(this.getClass().getSimpleName());
        setUserPrivileges(new RegularPrivileges());
        setUserCreated(LocalDateTime.now());
    }

    public UserRegular(String name, String password, String email) {
        this();
        this.setUserName(name);
        this.setUserMail(email);
        this.setUserPassword(password);

    }

}
