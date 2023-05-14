package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class UserDaoJDBCImpl implements UserDao {
    private static final Connection connection = Util.getConnection();

    public UserDaoJDBCImpl() {

    }

    //создать таблицу пользователей
    public void createUsersTable() {
        try (PreparedStatement preparedStatement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS users (id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, name VARCHAR(20) NOT NULL, lastname VARCHAR(20) NOT NULL, age INT NOT NULL)")) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //удалить таблицу пользователей
    public void dropUsersTable() {
        try (PreparedStatement preparedStatement = connection.prepareStatement("DROP TABLE IF EXISTS users")) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO users (name, lastname, age) VALUES (?, ?, ?)")) {

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //удалить пользователя по id
    public void removeUserById(long id) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM users WHERE id = " + id)) {
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //получить всех пользователей
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String query = "SELECT id, name, lastname, age FROM users";
        try (Statement st = Util.getConnection().createStatement();) {
            ResultSet resultSet = st.executeQuery(query);

            while (resultSet.next()) {
                User user = new User();
                //   user.setId(resultSet.getLong(1));
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastname"));
                user.setAge(resultSet.getByte("age"));
                users.add(user);
//return "ID" + id + "NAME" + name + "LASTNAME" + lastName + "AGE" + age;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    //очистить таблицу пользователей
    public void cleanUsersTable() {
        try (PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM users")) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}









