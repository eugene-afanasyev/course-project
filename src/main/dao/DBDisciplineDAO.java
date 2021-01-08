package main.dao;

import main.models.Discipline;
import main.models.Role;
import main.models.User;
import main.utils.HibernateSessionFactoryUtil;
import org.hibernate.HibernateException;
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

    public Discipline findByCode(String code){
        var query = HibernateSessionFactoryUtil.getSessionFactory()
                .openSession().createQuery("FROM Discipline as d WHERE d.disciplineCode=:param");
        query.setParameter("param", code);
        if (query.list().isEmpty()) {
            return null;
        }
        return (Discipline) query.list().get(0);
    }

    public Discipline findByName(String name){
        var session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        var query = session.createQuery("From Discipline WHERE name=:param");
        query.setParameter("param", name);
        if (query.list().isEmpty()) {
            return null;
        }
        var discipline =(Discipline) query.list().get(0);
        session.close();
        return discipline;
    }

    public void changeRuName(int id, String ruName){
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            Discipline discipline = (Discipline) session.get(Discipline.class, id);
            discipline.setRuName( ruName);
            session.update(discipline);
            tx.commit();
        }catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            };
            e.printStackTrace();
        }finally {
            session.close();
        }
    }
    public void changeDescription(int id, String description){
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            Discipline discipline = (Discipline) session.get(Discipline.class, id);
            discipline.setDescription( description);
            session.update(discipline);
            tx.commit();
        }catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            };
            e.printStackTrace();
        }finally {
            session.close();
        }
    }
}
