package com.phonik.simpleforum;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


public class HibernateUtil {

    private static SessionFactory sessionFactory;

    private static SessionFactory buildSessionFactory() {
        Configuration config = new Configuration().configure();
        // TODO change to logger
        System.out.println("Hibernate config loaded");
//        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
//                .applySettings(config.getProperties())
//                .build();
        // TODO change to logger
        System.out.println("Hibernate Annotation service registry created");
        // this solution ignores classes that are mapped in the xml file
//        SessionFactory sessionFactory = config.buildSessionFactory(serviceRegistry);
        SessionFactory sessionFactory = config.buildSessionFactory();
        return sessionFactory;
    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) sessionFactory = buildSessionFactory();
        return sessionFactory;
    }
}
