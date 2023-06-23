package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.HibernateUtil;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;

import com.mysql.cj.protocol.ServerSessionStateController.SessionStateChange;

public class UserDaoHibernateImpl implements UserDao {
    
    private Transaction transaction = null;
    private Session session = null;

    private final String CREATEUSERTABLE = "CREATE TABLE IF NOT EXISTS `userdb`.`user` (\n" +
        "  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,\n" +
        "  `name` VARCHAR(45) NULL,\n" +
        "  `lastName` VARCHAR(45) NULL,\n" +
        "  `age` TINYINT(3) NULL,\n" +
        "  PRIMARY KEY (id));";
    private final String DROPTABLEUSER = "DROP TABLE IF EXISTS userdb.user";
    private final String TRUNCATETABLE = "TRUNCATE TABLE userdb.user";


    public UserDaoHibernateImpl() {
        session = HibernateUtil.getSessionFactory().openSession();
    }

    @Override
    public void createUsersTable() {
        try {
            transaction = session.beginTransaction();
            final NativeQuery query = session.createNativeQuery(CREATEUSERTABLE);
            query.executeUpdate();
            transaction.commit();

        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        try {
            transaction = session.beginTransaction();
            NativeQuery query = session.createNativeQuery(DROPTABLEUSER);
            query.executeUpdate();
            transaction.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        User user = new User(name, lastName, age);
        try {
            transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }

    // FLUSH ить еще нужно
    @Override
    public void removeUserById(long id) {
        try {
            transaction = session.beginTransaction();
            session.delete(session.get(User.class, id));
            session.flush();
            transaction.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> data = new ArrayList<>();
        try {
            transaction = session.beginTransaction();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<User> criteriaQuery = builder.createQuery(User.class);
            criteriaQuery.from(User.class);
            data = session.createQuery(criteriaQuery).getResultList();
            data.stream().forEach(System.out::println);
            transaction.commit();
        } catch (HibernateException e) {
            // TODO: handle exception
        }
    
        return data;
    }

    @Override
    public void cleanUsersTable() {
        try {
            transaction = session.beginTransaction();
            NativeQuery query = session.createNativeQuery(TRUNCATETABLE);
            query.executeUpdate();            
            transaction.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }
}
