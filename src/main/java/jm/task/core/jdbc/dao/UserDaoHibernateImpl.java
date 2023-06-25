package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.HibernateUtil;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;

public class UserDaoHibernateImpl implements UserDao {

    private SessionFactory session;

    public UserDaoHibernateImpl() {
        session = HibernateUtil.getSessionFactory();
    }

    @Override
    public void createUsersTable() {
        String create_user_table = "CREATE TABLE IF NOT EXISTS `userdb`.`user` (\n" +
                "  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,\n" +
                "  `name` VARCHAR(45) NULL,\n" +
                "  `last_name` VARCHAR(45) NULL,\n" +
                "  `age` TINYINT(3) NULL,\n" +
                "  PRIMARY KEY (id));";
        Transaction transaction = null;

        try (Session session = this.session.openSession();) {
            transaction = session.beginTransaction();
            NativeQuery<User> query = session.createNativeQuery(create_user_table, User.class);
            query.executeUpdate();
            transaction.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        String drop_table_user = "DROP TABLE IF EXISTS userdb.user";
        Transaction transaction = null;
        try (Session session = this.session.openSession();) {
            transaction = session.beginTransaction();
            NativeQuery<User> query = session.createNativeQuery(drop_table_user, User.class);
            query.executeUpdate();
            transaction.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String last_name, byte age) {
        User user = new User(name, last_name, age);
        Transaction transaction = null;
        try (Session session = this.session.openSession();) {
            transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
            System.out.printf(" User c именем - %s добавлен в базу данных \n", name);
        } catch (HibernateException e) {
            transaction.rollback();
            e.printStackTrace();
        }
    }

    // FLUSH ить еще нужно
    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;
        try (Session session = this.session.openSession();) {
            transaction = session.beginTransaction();
            session.delete(session.get(User.class, id));
            session.flush();
            transaction.commit();
        } catch (HibernateException e) {
            transaction.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> data = new ArrayList<>();
        Transaction transaction = null;
        try (Session session = this.session.openSession();) {
            transaction = session.beginTransaction();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<User> criteriaQuery = builder.createQuery(User.class);
            criteriaQuery.from(User.class);
            data = session.createQuery(criteriaQuery).getResultList();
            data.stream().forEach(System.out::println);
            transaction.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return data;
    }

    @Override
    public void cleanUsersTable() {
        String truncate_table = "TRUNCATE TABLE userdb.user";
        Transaction transaction = null;
        try (Session session = this.session.openSession();) {
            transaction = session.beginTransaction();
            NativeQuery<User> query = session.createNativeQuery(truncate_table, User.class);
            query.executeUpdate();
            transaction.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }
}
