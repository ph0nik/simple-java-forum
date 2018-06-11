package com.phonik.simpleforum.dao;

import com.phonik.simpleforum.users.GeneralUser;
import com.phonik.simpleforum.users.UserType;

import java.util.List;


public interface UserDao {

    /**
     * ads user to the database
     *
     * @param   user    user object of type T
     * @return          assigned index of element within database
     *
     * */
    int addUser(GeneralUser user);

    /**
     * updates user object
     *
     * @param   user    UserRegular object
     *
     * */
    void updateUser(GeneralUser user);

    /**
     * deletes user from database
     *
     * @param   user    UserRegular object
     *
     * */
    void deleteuser(GeneralUser user);

    /**
     * retrieves user object from database
     *
     * @param   id  user identifier
     * @return      UserRegular object with given id
     *
     * */
    GeneralUser selectUser(int id);

    /**
     * selects user from database based on given email address
     *
     * @param   email   email value that has to be found
     * @return          user element if email address found, null otherwise
     * */
    GeneralUser selectByEmail(String email);

    List<GeneralUser> selectByRole(UserType userType);


}
