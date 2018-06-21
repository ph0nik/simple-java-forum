package com.phonik.simpleforum.users.service;

import com.phonik.simpleforum.dao.UserDao;
import com.phonik.simpleforum.exceptions.EmailExistException;
import com.phonik.simpleforum.exceptions.EmptyFieldsException;
import com.phonik.simpleforum.privileges.*;
import com.phonik.simpleforum.users.GeneralUser;
import com.phonik.simpleforum.users.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class UserService implements BanService {

    @Autowired
    private UserDao userDao;

    /**
     * creates new user based on name, password and email
     *
     * @param   name    name for the new user
     * @param   password    password for the new user
     * @param   email   email addres for the new user
     * */
    public GeneralUser createNewUser(String name, String password, String email) throws EmptyFieldsException, EmailExistException {
        return createUserWithType(name, password, email, UserType.USER, new RegularPrivileges());
    }

    /**
     * creates new administrator based on name, password and email
     *
     * @param   name        new administrator name
     * @param   password    password for the new administrator
     * @param   email       email address for the new administrator
     * */
    public GeneralUser createNewAdmin(String name, String password, String email) throws EmptyFieldsException, EmailExistException {
        return createUserWithType(name, password, email, UserType.ADMIN, new AdminPrivileges());
    }

    /**
     * Both service methods and class has to be annotated as @Transactional, otherwise attempt to get
     * session within DAO will result in exception
     * */
    @Transactional
    private GeneralUser createUserWithType(String name,
                                           String password,
                                           String email,
                                           UserType type,
                                           UserPrivileges privileges) throws EmailExistException, EmptyFieldsException {
        if (name == null || name.length() == 0) {
            throw new EmptyFieldsException("Name filed cannot be empty");
        } else if (password == null || password.length() == 0) {
            throw new EmptyFieldsException("Password field cannot be empty");
        } else if (email == null || email.length() == 0) {
            throw new EmptyFieldsException("Email field cannot be empty");
        } else if (emailExist(email)) {
            throw new EmailExistException("This email address is already registered");
        } else {
            GeneralUser user = new GeneralUser();
            user.setUserType(type);
            user.setChildClassName(this.getClass().getSimpleName());
            user.setPrivileges(new ElementPrivileges(UserType.USER, 0));
            user.setUserCreated(LocalDateTime.now());
            user.setUserName(name);
            user.setUserPassword(password);
            user.setUserMail(email);
            user.setBanLiftDate(LocalDateTime.now());
            int i = userDao.addUser(user);
            user.setUserId(i);
            return user;
        }

    }

    @Transactional
    private boolean emailExist(String email) {
        GeneralUser generalUser = userDao.selectByEmail(email);
        return generalUser != null;
    }



    /**
     * changes password for selected user
     *
     * @param   user        user element for which property needs to be updated
     * @param   oldPassword old password to verify user
     * @param   newPassword new password value for given user
     * */
    public void changePassword(GeneralUser user, String oldPassword, String newPassword) {

    }

    /**
     * changes email address for given user
     *
     * @param   user        user element for which property needs to be update
     * @param   password    to verify user
     * @param   newEmail    new email address
     * */
    public void changeEmail(GeneralUser user, String password, String newEmail) {

    }


    public void deleteUser(GeneralUser forumUser) {

    }

    @Transactional
    public boolean adminCheck() {
        try {
            List<GeneralUser> generalUser = userDao.selectByRole(UserType.ADMIN);
        } catch (NoSuchElementException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
        return true;
    }

    @Transactional
    public GeneralUser getUserWithId(int id) {
        return userDao.selectUser(id);
    }

    public GeneralUser getUserWithEmail(String email) {
        return userDao.selectByEmail(email);
    }

    @Override
    public void banUser(GeneralUser user, int months, int days, int hours) {
        LocalDateTime expDate = LocalDateTime.now()
                .plusMonths(months)
                .plusDays(days)
                .plusHours(hours);
        user.setTemporarilyBanned(true);
        user.setBanLiftDate(expDate);
        userDao.updateUser(user);
    }

    @Override
    public void banUser(GeneralUser user) {
        user.setPermanentlyBanned(true);
        userDao.updateUser(user);
    }

    @Override
    public void unbanUser(GeneralUser user) {
        user.setBanLiftDate(LocalDateTime.now());
        user.setPermanentlyBanned(false);
        user.setTemporarilyBanned(false);
        userDao.updateUser(user);
    }
}
