package main.dao;

import main.models.Region;
import main.models.Role;
import main.utils.HibernateSessionFactoryUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class DBRegionDAO implements RegionDAO{

    @Override
    public Region findById ( int id ) {
        return HibernateSessionFactoryUtil.getSessionFactory().openSession().get(Region.class, id);
    }

    @Override
    public void save ( Region arg ) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.save(arg);
        transaction.commit();
        session.close();
    }

    @Override
    public void remove ( Region arg ) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.remove(arg);
        transaction.commit();
        session.close();
    }

    @Override
    public void update ( Region arg ) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.update(arg);
        transaction.commit();
        session.close();
    }

    @Override
    public List<Region> findAll ( ) {
        return (List<Region>)  HibernateSessionFactoryUtil.getSessionFactory().openSession().createQuery("From Region").list();
    }
}
