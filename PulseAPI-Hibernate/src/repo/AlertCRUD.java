package com.pulseapi.repo;

import com.pulseapi.connection.HibernateUtil;
import com.pulseapi.model.Alert;

import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class AlertCRUD {

    // INSERT ALERT
    public void insertAlert(Alert alert) {

        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            transaction = session.beginTransaction();

            session.persist(alert);

            transaction.commit();

            System.out.println("Alert inserted successfully.");
            System.out.println("Generated Alert ID: " + alert.getAlertId());

        } catch (Exception e) {

            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }

            e.printStackTrace();
        }
    }

    // SELECT ALL ALERTS
    public void getAllAlerts() {

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            List<Alert> alerts =
                    session.createQuery("FROM Alert", Alert.class).getResultList();

            if (alerts.isEmpty()) {
                System.out.println("No alerts found.");
            } else {
                System.out.println("All Alerts:");

                for (Alert alert : alerts) {
                    System.out.println(alert);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}