package com.phonik.simpleforum.dao;

import com.phonik.simpleforum.users.GeneralUser;
import com.phonik.simpleforum.users.UserType;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Repository
public class UserDaoImpl implements UserDao {


    /**
     * Regular Hibernate SessionFactory is used so all the methods that framework provides can be used
     *
     * */
    @Autowired
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sf) {
        this.sessionFactory = sf;
    }

    @Override
    public int addUser(GeneralUser user) {
        Session session = sessionFactory.getCurrentSession();
        int userId = (int) session.save(user);
        return userId;
    }


    @Override
    public void updateUser(GeneralUser user) {
        Session session = sessionFactory.getCurrentSession();
        session.update(user);
    }

    @Override
    public void deleteuser(GeneralUser user) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(user);
    }

    @Override
    public GeneralUser selectUser(int id) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "FROM GeneralUser GU WHERE GU.id = :user_id";
        Query query = session.createQuery(hql)
                            .setParameter("user_id", id);
        Optional<Object> singleResult = Optional.ofNullable(query.uniqueResult());
        GeneralUser user = (GeneralUser) singleResult.orElseThrow(() -> new NoSuchElementException("No user with id :: " + id));
        // additional initialization is needed for lazy fetched properties
        Hibernate.initialize(user.getUserPrivilegesMap());
        return user;
    }


    @Override
    public GeneralUser selectByEmail(String email) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "FROM GeneralUser GU WHERE GU.userMail LIKE :user_mail";
        Query query = session.createQuery(hql)
                            .setParameter("user_mail", email);
        Optional<Object> result = Optional.ofNullable(query.uniqueResult());
        GeneralUser generalUser = (GeneralUser) result.orElse(null);
        return generalUser;
    }

    @Override
    public List<GeneralUser> selectByRole(UserType userType) {
        Session session  = sessionFactory.getCurrentSession();
        String hql = "FROM GeneralUser GU WHERE GU.userType LIKE :user_type";
        Query query = session.createQuery(hql).setParameter("user_type", userType);
        List<GeneralUser> resultList = query.getResultList();
        if (resultList.isEmpty()) throw new NoSuchElementException("No users of this type :: " + userType);
        else return resultList;
    }
}
