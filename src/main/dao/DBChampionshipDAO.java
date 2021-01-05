package main.dao;

import main.models.Championship;
import main.models.Discipline;
import main.utils.HibernateSessionFactoryUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class DBChampionshipDAO implements ChampionshipDAO{

    @Override
    public Championship findById ( int id ) {
        return HibernateSessionFactoryUtil.getSessionFactory().openSession().get(Championship.class, id);
    }

    @Override
    public void save ( Championship arg ) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.save(arg);
        transaction.commit();
        session.close();
    }

    @Override
    public void remove ( Championship arg ) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.remove(arg);
        transaction.commit();
        session.close();
    }

    @Override
    public void update ( Championship arg ) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.update(arg);
        transaction.commit();
        session.close();
    }

    @Override
    public List<Championship> findAll ( ) {
        List<Championship> championships = (List<Championship>)  HibernateSessionFactoryUtil.getSessionFactory().openSession().createQuery("From Championship").list();
        return championships;
    }
}
