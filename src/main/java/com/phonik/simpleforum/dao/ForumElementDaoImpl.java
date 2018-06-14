package com.phonik.simpleforum.dao;

import com.phonik.simpleforum.HibernateUtil;
import com.phonik.simpleforum.elements.*;
import com.phonik.simpleforum.users.GeneralUser;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import java.util.NoSuchElementException;
import java.util.Optional;

@Repository
public class ForumElementDaoImpl implements ForumElementDao {

//    private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public ForumSection getRoot() {
        Session session = sessionFactory.getCurrentSession();
        ForumSection root = null;
        String hql = "FROM ForumSection FS WHERE FS.elementType = :element_type";
        Query query = session.createQuery(hql)
                .setParameter("element_type", ElementType.ROOT);
        try {
            root = (ForumSection) query.getSingleResult();
        } catch (NoResultException ex) {
            System.out.println("No result found");
        }
        return root;
    }

    @Override
    public int addForumElement(AbstractForumElement forumElement) {
        Session session = sessionFactory.getCurrentSession();
        return (int) session.save(forumElement);
    }

    @Override
    public void updateForumElement(AbstractForumElement forumElement) {
        Session session = sessionFactory.getCurrentSession();
        session.update(forumElement);
    }

    @Override
    public void deleteForumElement(AbstractForumElement forumElement) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(forumElement);
    }

    @Override
    public ForumSection getForumSection(int id) throws NoSuchElementException {
        ForumSection section = null;
        try (final Session session = sessionFactory.openSession()) {
            String hql = "FROM ForumSection FS WHERE FS.id = :section_id";
            Query query = session.createQuery(hql).setParameter("section_id", id);
            Optional<Object> result = Optional.ofNullable(query.uniqueResult());
            section = (ForumSection) result.orElseThrow(() -> new NoSuchElementException("No element with given id found :: " + id));
            // Force initialization of a proxy or persistent collection.
            Hibernate.initialize(section.getPostsList());
            Hibernate.initialize(section.getSectionsList());
            Hibernate.initialize(section.getAuthor());
            Hibernate.initialize(section.getAuthor().getUserPrivilegesMap());
            Hibernate.initialize(section.getEditor());
            if (section.getEditor() != null) {
                Hibernate.initialize(section.getEditor().getUserPrivilegesMap());
            }


        } catch (HibernateException he) {

        }
        return section;
    }

    @Override
    public ForumPost getForumPost(int id) {
        return null;
    }

    @Override
    public ForumReply getForumReply(int id) {
        return null;
    }

}
