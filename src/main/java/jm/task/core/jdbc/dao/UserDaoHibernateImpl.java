package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.management.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static jm.task.core.jdbc.util.Util.getSessionFactory;

public class UserDaoHibernateImpl implements UserDao {
    public SessionFactory sessionFactory = getSessionFactory();

    // UserHibernateDaoImpl должен реализовывать интерефейс UserDao
    // В класс Util должна быть добавлена конфигурация для Hibernate ( рядом с JDBC), без использования xml.
    // Service на этот раз использует реализацию dao через Hibernate
    // Методы создания и удаления таблицы пользователей в классе UserHibernateDaoImpl должны быть реализованы с помощью SQL.
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            session.createSQLQuery("CREATE TABLE IF NOT EXISTS users (id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY, name VARCHAR(20) NOT NULL, last_name VARCHAR(20) NOT NULL, age INT NOT NULL)").executeUpdate();
            transaction.commit();
          //  System.out.println("createUsersTable good");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
           // System.out.println("createUsersTable error");
        }
    }

    @Override
    public void dropUsersTable() {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            session.createSQLQuery("DROP TABLE IF EXISTS users").executeUpdate();

            transaction.commit();
          //  System.out.println("dropUsersTable good");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
          //  System.out.println("dropUsersTable error");
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            session.persist(new User(name, lastName, age));

            transaction.commit();
           // System.out.println("saveUser good");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
          //  System.out.println("saveUser error");
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            User user = session.get(User.class, id);
            session.remove(user);

            transaction.commit();
          //  System.out.println("removeUserById good");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
           // System.out.println("removeUserById error");
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            users = session.createQuery("SELECT a From User a", User.class).getResultList();

            transaction.commit();
           // System.out.println("List<User> getAllUsers good");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
          //  System.out.println("getAllUsers error");
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        //  String sql = "DELETE From User";
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            session.createQuery("DELETE From User").executeUpdate();
            transaction.commit();
        //    System.out.println("cleanUsersTable good");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
          //  System.out.println("cleanUsersTable error");
        }
    }
}
