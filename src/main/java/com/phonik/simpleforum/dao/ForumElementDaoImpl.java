package com.phonik.simpleforum.dao;

import com.phonik.simpleforum.HibernateUtil;
import com.phonik.simpleforum.elements.AbstractForumElement;
import com.phonik.simpleforum.elements.ForumPost;
import com.phonik.simpleforum.elements.ForumSection;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.NoSuchElementException;
import java.util.Optional;

@Repository
public class ForumElementDaoImpl implements ForumElementDao {

    private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    @Override
    public int addForumElement(AbstractForumElement forumElement) {
        int insertId = 0;
        try (final Session session = sessionFactory.openSession()){
            session.beginTransaction();
            insertId = (int) session.save(forumElement);
            session.getTransaction().commit();
        } catch (HibernateException he) {

        }
        return insertId;
    }

    @Override
    public void updateForumElement(AbstractForumElement forumElement) {
        try (final Session session = sessionFactory.openSession()){
            session.beginTransaction();
            session.update(forumElement);
            session.getTransaction().commit();
        } catch (HibernateException he) {

        }
    }

    @Override
    public void deleteForumElement(AbstractForumElement forumElement) {
        try (final Session session = sessionFactory.openSession()){
            session.beginTransaction();
            session.delete(forumElement);
            session.getTransaction().commit();
        } catch (HibernateException he) {

        }
    }

    @Override
    public ForumSection getForumSection(int id) throws NoSuchElementException {
        ForumSection section = null;
        try (final Session session = sessionFactory.openSession()){
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

}
