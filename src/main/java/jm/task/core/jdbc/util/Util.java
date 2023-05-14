package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public class Util {
    private static String URL = "jdbc:mysql://localhost:3306/mydbtest";
    private static String USER = "root";
    private static String PASSWORD = "elina2305";
    private static String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static SessionFactory sessionFactory = null;

    public static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
         //   System.out.println("OK");
        } catch (SQLException ex) {
            ex.printStackTrace();
         //   System.out.println("error SQLException ");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public static SessionFactory getSessionFactory() {
        SessionFactory sessionFactory = null;
        try {
                Configuration configuration = new Configuration();

                Properties settings = new Properties();

                settings.put(Environment.DRIVER, DRIVER);
                settings.put(Environment.URL, URL);
                settings.put(Environment.USER, USER);
                settings.put(Environment.PASS, PASSWORD);
                settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQLDialect");
                settings.put(Environment.SHOW_SQL, true);
                settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
                settings.put(Environment.HBM2DDL_AUTO, "");


                configuration.setProperties(settings);

                configuration.addAnnotatedClass(User.class);
                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();

                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
           //     System.out.println("есть соединение");

            } catch(Exception e){
                e.printStackTrace();
         //   System.out.println("ОШИБКА");
            }
            return sessionFactory;
        }
/*public static Session getHibernateSession(){
        return sessionFactory.openSession();
}*/
    }