package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao{
    public void createUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {

            session.beginTransaction();
            String sql = "CREATE TABLE IF NOT EXISTS `pp_1_1_5`.`User` (\n" +
                    "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
                    "  `name` VARCHAR(45) NOT NULL,\n" +
                    "  `lastName` VARCHAR(45) NOT NULL,\n" +
                    "  `age` INT NOT NULL,\n" +
                    "  PRIMARY KEY (`id`));";

            session.createSQLQuery(sql).executeUpdate();
            session.getTransaction().commit();

        } catch (HibernateException e) {
            System.out.println("Не удалось создать таблицу.\n" + e.getMessage());
        }
    }

    public void dropUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {

            session.beginTransaction();
            session.createSQLQuery("DROP TABLE IF EXISTS User").executeUpdate();
            session.getTransaction().commit();

        } catch (HibernateException e) {
            System.out.println("Не удалось удалить таблицу.\n" + e.getMessage());
        }
    }

    public void saveUser(String name, String lastName, Byte age) {
        Transaction transaction = null;

        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            session.save(new User(name, lastName, age));

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }

        System.out.println("User " + name + " добавлен в базу данных.");
    }

    public void removeUserById(long id) {
        Transaction transaction = null;

        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            User user = session.load(User.class, id);
            session.delete(user);

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    public List<User> getAllUsers() {
        List<User> list;

        try (Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();

            list = session.createQuery("FROM User").list();
        }

        return list;
    }

    public void cleanUsersTable() {
        Transaction transaction = null;

        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            session.createQuery("DELETE FROM User").executeUpdate();

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }
}
