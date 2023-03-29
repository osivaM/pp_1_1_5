package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {}

    public void createUsersTable() {
        try (Connection connection = Util.getConnection()) {
            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate("CREATE TABLE IF NOT EXISTS `pp_1_1_5`.`User` (\n" +
                        "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
                        "  `name` VARCHAR(45) NOT NULL,\n" +
                        "  `lastName` VARCHAR(45) NOT NULL,\n" +
                        "  `age` VARCHAR(45) NOT NULL,\n" +
                        "  PRIMARY KEY (`id`));");
            }
        } catch (SQLException e) {
            System.out.println("Не удалось создать таблицу.\n" + e.getMessage());
        }
    }

    public void dropUsersTable() {
        try (Connection connection = Util.getConnection()) {
            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate("DROP TABLE IF EXISTS User");
            }
        } catch (SQLException e) {
            System.out.println("Не удалось удалить таблицу.\n" + e.getMessage());
        }
    }

    public void saveUser(String name, String lastName, Byte age) {
        try (Connection connection = Util.getConnection()) {
            try (Statement statement = connection.createStatement()) {
                connection.setAutoCommit(false);
                statement.executeUpdate("INSERT INTO User SET name = '" + name + "', lastName = '" + lastName + "', age = " + age);
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
            }
        } catch (SQLException e1) {
            System.out.println("Не удалось добавить новую запись.\n" + e1.getMessage());
        }

        System.out.println("User " + name + " добавлен в базу данных.");
    }

    public void removeUserById(long id) {
        try (Connection connection = Util.getConnection()) {
            try (Statement statement = connection.createStatement()) {
                connection.setAutoCommit(false);
                statement.executeUpdate("DELETE FROM User WHERE id = " + id);
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
            }
        } catch (SQLException e1) {
            System.out.println("Не удалось удалить запись.\n" + e1.getMessage());
        }
    }

    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        try (Connection connection = Util.getConnection()) {
            try (Statement statement = connection.createStatement()) {
                connection.setAutoCommit(false);
                ResultSet resultSet = statement.executeQuery("SELECT * FROM User");
                connection.commit();

                while (resultSet.next()) {
                    User user = new User();

                    user.setId(resultSet.getLong("id"));
                    user.setName(resultSet.getString("name"));
                    user.setLastName(resultSet.getString("lastName"));
                    user.setAge((byte) resultSet.getInt("age"));

                    list.add(user);
                }
            } catch (SQLException e) {
                connection.rollback();
            }
        } catch (SQLException e) {
            System.out.println("Не удалось получить все записи.\n" + e.getMessage());
        }

        return list;
    }

    public void cleanUsersTable() {
        try (Connection connection = Util.getConnection()) {
            try (Statement statement = connection.createStatement()) {
                connection.setAutoCommit(false);
                statement.executeUpdate("DELETE FROM User");
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
            }
        } catch (SQLException e1) {
            System.out.println("Не удалось очистить таблицу.\n" + e1.getMessage());
        }
    }
}
