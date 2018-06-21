package com.phonik.simpleforum.dao;

import com.phonik.simpleforum.elements.*;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
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
            Hibernate.initialize(root.getSectionsList());
            Hibernate.initialize(root.getPostsList());
            Hibernate.initialize(root.getElementPath());
        } catch (NoResultException ex) {
            System.out.println("No result found");
        }
        return root;
    }

    @Override
    public long addForumElement(AbstractForumElement forumElement) {
        Session session = sessionFactory.getCurrentSession();
        System.out.println(forumElement.getAuthor().getUserName());
        return (long) session.save(forumElement);
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
    public ForumSection getForumSection(long id) throws NoSuchElementException {
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
            Hibernate.initialize(section.getEditor());

        } catch (HibernateException he) {

        }
        return section;
    }

    @Override
    public ForumPost getForumPost(long id) {
        ForumPost post = null;
        try (final Session session = sessionFactory.openSession()) {
            String hql = "FROM ForumPost FP WHERE FP.id = :post_id";
            Query query = session.createQuery(hql).setParameter("post_id", id);
            Optional<Object> singleResult = Optional.ofNullable(query.getSingleResult());
            post = (ForumPost) singleResult.orElseThrow(() -> new NoSuchElementException("No element with given id found :: " + id));
            Hibernate.initialize(post.getParents());
            Hibernate.initialize(post.getPostReplysList());
        } catch (HibernateException he) {

        }
        return post;
    }

    @Override
    public ForumReply getForumReply(long id) {
        ForumReply reply = null;
        try (final Session session = sessionFactory.openSession()) {
            String hql = "SELECT parentPost FROM ForumReply FR WHERE FR.id = :reply_id";
            Query query = session.createQuery(hql).setParameter("reply_id", id);
            Optional<Object> singleResult = Optional.ofNullable(query.getSingleResult());
            reply = (ForumReply) singleResult.orElseThrow(() -> new NoSuchElementException("No element with given id found :: " + id));
            Hibernate.initialize(reply.getParents());
        } catch (HibernateException he) {

        }
        return reply;
    }

}
