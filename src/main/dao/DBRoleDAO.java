package main.dao;

import main.models.Role;
import main.models.User;
import main.utils.HibernateSessionFactoryUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class DBRoleDAO implements RoleDAO{

    @Override
    public Role findById ( int id ) {
        return HibernateSessionFactoryUtil.getSessionFactory().openSession().get(Role.class, id);
    }

    @Override
    public void save ( Role arg ) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.save(arg);
        transaction.commit();
        session.close();
    }

    @Override
    public void remove ( Role arg ) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.remove(arg);
        transaction.commit();
        session.close();
    }

    @Override
    public void update ( Role arg ) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.update(arg);
        transaction.commit();
        session.close();
    }

    @Override
    public List<Role> findAll ( ) {
        List<Role> roles = (List<Role>)  HibernateSessionFactoryUtil.getSessionFactory().openSession().createQuery("From Role").list();
        return roles;
    }

    public Role findByName(String name){
        Role role = null;
        try {
            var query = HibernateSessionFactoryUtil.getSessionFactory().openSession().createQuery("FROM Role as r WHERE r.name=:param");
            query.setParameter("param", name);
            var result = query.list();
        }catch (Exception ex){
            var message = ex.getMessage();
        }
        return role;
    }
}
