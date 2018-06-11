package com.phonik.simpleforum.dao;

import com.phonik.simpleforum.HibernateUtil;
import com.phonik.simpleforum.users.UserAdmin;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AdminDaoImpl {

    private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();


}
