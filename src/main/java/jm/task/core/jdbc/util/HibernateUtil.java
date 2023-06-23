package jm.task.core.jdbc.util;

import java.util.Properties;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import jm.task.core.jdbc.model.User;

public class HibernateUtil {
    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        try {
            Configuration configuration = new Configuration();

            // Hibernate settings equivalent to hibernate.cfg.xml's properties
            Properties properties = new Properties();
            properties.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
            properties.put(Environment.URL, "jdbc:mysql://localhost:3306/userdb");
            properties.put(Environment.USER, "root");
            properties.put(Environment.PASS, "root");
            properties.put(Environment.DIALECT, "org.hibernate.dialect.MySQL5Dialect");

            properties.put(Environment.SHOW_SQL, "true");
            properties.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
            // HBM2DDL_AUTO - validate; create-only; drop; update; create; create-drop; none;
            // validate - validate the schema, makes no changes to the database;
            // create-only - database creation will be generated;
            // drop - database dropping will be generated;
            // update - update the schema;
            // create - creates the schema, destroying previous data;
            // create-drop - drop the schema when the SessionFactory is closed explicitly (явно, недвусмысленно),
            //               typically when the application is stopped.
            // none - does nothing with the schema, makes no changes to the database
            properties.put(Environment.HBM2DDL_AUTO, "none");

            configuration.setProperties(properties);
            configuration.addAnnotatedClass(User.class);

            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties()).build();

            sessionFactory = configuration.buildSessionFactory(serviceRegistry);

        } catch (HibernateException e) {
            e.printStackTrace();
        }
        
        return sessionFactory;
    }

}
