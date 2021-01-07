package main.dao;

import main.models.Championship;
import main.models.Discipline;
import main.models.User;
import main.utils.HibernateSessionFactoryUtil;
import org.hibernate.HibernateException;
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
        var championships =(List<Championship>)HibernateSessionFactoryUtil.getSessionFactory().openSession().createQuery("From  Championship").list();
        return championships;
    }

    @Override
    public void addUser ( Championship championship, User user ) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            var champ = (Championship) session.get(Championship.class, championship.id);
            var userEntity = (User) session.get(User.class, user.getId());
            champ.addUser(userEntity);
            session.update(champ);
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

    @Override
    public void addDiscipline ( Championship championship, Discipline discipline) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            Championship champ = (Championship) session.get(Championship.class, championship.id);
            var disc = (Discipline) session.get(Discipline.class, discipline.getId());
            champ.addDiscipline(disc);
            session.update(champ);
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
