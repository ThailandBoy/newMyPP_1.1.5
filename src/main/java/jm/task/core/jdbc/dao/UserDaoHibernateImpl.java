package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.HibernateUtil;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class UserDaoHibernateImpl implements UserDao {
    private Transaction transaction = null;
    public UserDaoHibernateImpl() {
    }

    @Override
    public void createUsersTable() {
        // try (Session session = HibernateUtil.getSessionFactory().openSession()) {
        //     //session.            
        // } catch (Exception e) {
        //     // TODO: handle exception
        // }
    }

    @Override
    public void dropUsersTable() {}

    @Override
    public void saveUser(String name, String lastName, byte age) {
        // try (Session session = HibernateUtil.getSessionFactory().openSession()) {
        //     // start a transaction
        //     transaction = session.beginTransaction();
        //     // save the user object
        //     User user = new User(name, lastName, age);
        //     session.save(user);
        //     // commit transaction
        //     transaction.commit();
        // } catch (HibernateException e) {
        //     e.printStackTrace();
        // }
    }

    @Override
    public void removeUserById(long id) {}

    @Override
    public List<User> getAllUsers() {
        // try (Session session = HibernateUtil.getSessionFactory().openSession()) {
        //     return session.createQuery("from User", User.class).list();           
        // } catch (Exception e) {
        //     // TODO: handle exception
        // }
        return null;
    }

    @Override
    public void cleanUsersTable() {}
}
