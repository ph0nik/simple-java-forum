package com.phonik.simpleforum.users;

import com.phonik.simpleforum.privileges.AdminPrivileges;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "admin")
@Deprecated
public class UserAdmin extends GeneralUser {

    public UserAdmin() {
        super();
        setUserType(UserType.ADMIN);
        setChildClassName(this.getClass().getSimpleName());
        setUserPrivileges(new AdminPrivileges());
        setUserCreated(LocalDateTime.now());
    }

    public UserAdmin(String name, String password, String email) {
        this();
        this.setUserName(name);
        this.setUserMail(email);
        this.setUserPassword(password);
    }

}
