package main.dao;

import main.models.Discipline;
import main.models.Role;
import main.utils.HibernateSessionFactoryUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class DBDisciplineDAO implements DisciplineDAO{

    @Override
    public Discipline findById ( int id ) {
        return HibernateSessionFactoryUtil.getSessionFactory().openSession().get(Discipline.class, id);
    }

    @Override
    public void save ( Discipline arg ) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.save(arg);
        transaction.commit();
        session.close();
    }

    @Override
    public void remove ( Discipline arg ) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.remove(arg);
        transaction.commit();
        session.close();
    }

    @Override
    public void update ( Discipline arg ) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.update(arg);
        transaction.commit();
        session.close();
    }

    @Override
    public List<Discipline> findAll ( ) {
        List<Discipline> disciplines = (List<Discipline>)  HibernateSessionFactoryUtil.getSessionFactory().openSession().createQuery("From Discipline").list();
        return disciplines;
    }
}
