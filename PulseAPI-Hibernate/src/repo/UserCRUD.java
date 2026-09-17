package com.pulseapi.repo;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.pulseapi.connection.HibernateUtil;
import com.pulseapi.model.User;

public class UserCRUD {

    // INSERT
    public static void insertUser(User user) {

        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            transaction = session.beginTransaction();

            session.persist(user);

            transaction.commit();

            System.out.println("User inserted successfully.");
            System.out.println("Generated User ID: " + user.getUserId());

        } catch (Exception exception) {

            if (transaction != null) {
                transaction.rollback();
            }

            exception.printStackTrace();
        }
    }

    // READ - Get user by ID
    public static void getUserById(long userId) {

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            User user = session.find(User.class, userId);

            if (user != null) {
                System.out.println("User found:");
                System.out.println(user);
            } else {
                System.out.println("User not found.");
            }

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    // READ - Get all users
    public static void getAllUsers() {

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            List<User> users = session
                    .createQuery("FROM User", User.class)
                    .getResultList();

            if (users.isEmpty()) {
                System.out.println("No users found.");
            } else {
                System.out.println("All Users:");

                for (User user : users) {
                    System.out.println(user);
                }
            }

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    // UPDATE
    public static void updateUser(
            long userId,
            String newName,
            String newEmail,
            String newPassword) {

        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            transaction = session.beginTransaction();

            User user = session.find(User.class, userId);

            if (user != null) {

                user.setName(newName);
                user.setEmail(newEmail);
                user.setPassword(newPassword);

                session.merge(user);

                transaction.commit();

                System.out.println("User updated successfully.");

            } else {

                System.out.println("User not found.");
                transaction.rollback();
            }

        } catch (Exception exception) {

            if (transaction != null) {
                transaction.rollback();
            }

            exception.printStackTrace();
        }
    }

    // DELETE
    public static void deleteUser(long userId) {

        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            transaction = session.beginTransaction();

            User user = session.find(User.class, userId);

            if (user != null) {

                session.remove(user);

                transaction.commit();

                System.out.println("User deleted successfully.");

            } else {

                System.out.println("User not found.");
                transaction.rollback();
            }

        } catch (Exception exception) {

            if (transaction != null) {
                transaction.rollback();
            }

            exception.printStackTrace();
        }
    }
}