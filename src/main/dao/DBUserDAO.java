package main.dao;

import main.models.*;
import main.utils.HibernateSessionFactoryUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Enumeration;
import java.util.List;

public class DBUserDAO implements UserDAO{

    public DBUserDAO(){

    }

    @Override
    public User findById ( int id ) {
        /*var session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        var user = session.get(User.class, id);
        session.close();
        return user;*/
        return HibernateSessionFactoryUtil.getSessionFactory().openSession().get(User.class, id);
    }

    @Override
    public void save ( User user ) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.save(user);
        transaction.commit();
        session.close();
    }

    @Override
    public void remove ( User user ) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.remove(user);
        transaction.commit();
        session.close();
    }

    @Override
    public void update ( User user ) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.update(user);
        transaction.commit();
        session.close();
    }

    @Override
    public List<User> findAll ( )
    {
        List<User> users = (List<User>)  HibernateSessionFactoryUtil.getSessionFactory().openSession().createQuery("From User").list();
        return users;
    }

    public void updateLogin(int id, String login){
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            User user = (User) session.get(User.class, id);
            user.setLogin( login );
            session.update(user);
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

    public User findByLogin(String login){
        var query = HibernateSessionFactoryUtil.getSessionFactory().openSession().createQuery("FROM User as u WHERE u.login=:param");
        query.setParameter("param", login);
        return (User) query.list().get(0);
    }

    public void updatePassword(int id, String password){
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            User user = (User) session.get(User.class, id);
            user.setPassword( password );
            session.update(user);
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
    public List<User> findByName(String firstName, String lastName){
        var query = HibernateSessionFactoryUtil.getSessionFactory().openSession().createQuery("FROM User as u WHERE u.firstName=:first and u.lastName=:last");
        query.setParameter("first", firstName);
        query.setParameter("last", lastName);
        return (List<User>) query.list();
    }

    @Override
    public void updateDiscipline( int id, Discipline discipline ){
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            User user = (User) session.get(User.class, id);
            Discipline disc = null;
            try{
                disc = session.get(Discipline.class, discipline.getId());
            } catch (Exception ex){

            }
            user.setDiscipline(disc);
            session.update(user);
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
    public void updateChampionship ( int id , Championship championship ) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            User user = (User) session.get(User.class, id);
            var champ = session.get(Championship.class, championship.id);
            user.setChampionship(champ);
            session.update(user);
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
    public void updateRegion ( int id , Region region ) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            User user = (User) session.get(User.class, id);
            var reg = session.get(Region.class, region.getId());
            user.setRegion(reg);
            session.update(user);
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
    public void updateRole ( int id , Role role ) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            User user = (User) session.get(User.class, id);
            var rl = session.get(Role.class, role.getId());
            user.setRole(rl);
            session.update(user);
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
