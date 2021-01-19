package main.dao;

import main.models.Championship;
import main.models.Discipline;
import main.models.Result;
import main.models.User;
import main.utils.HibernateSessionFactoryUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class DBResultDAO implements ResultDAO {
    @Override
    public Result findById ( int id ) {
        return HibernateSessionFactoryUtil.getSessionFactory().openSession().get(Result.class, id);
    }

    @Override
    public void save ( Result arg ) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            var id = (int)session.save(new Result(null, arg.getScore(), arg.getModules()));
            var result = session.get(Result.class, id);

            var user = session.get(User.class, arg.getUser().getId());

            result.setUser(user);
            session.update(result);

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
    public void remove ( Result arg ) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.remove(arg);
        transaction.commit();
        session.close();
    }

    @Override
    public void update ( Result arg ) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.update(arg);
        transaction.commit();
        session.close();
    }

    @Override
    public List<Result> findAll ( ) {
        List<Result> results =
                (List<Result>)  HibernateSessionFactoryUtil.getSessionFactory().openSession().createQuery("From Result").list();
        return results;
    }
}
