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
    // CHECK - Check whether an active alert already exists for an API
    public boolean hasActiveAlert(long apiId) {

        try (Session session =
                     HibernateUtil.getSessionFactory().openSession()) {

            Alert alert =
                    session.createQuery(
                                    "FROM Alert " +
                                            "WHERE apiId = :apiId " +
                                            "AND alertStatus = :status",
                                    Alert.class
                            )
                            .setParameter("apiId", apiId)
                            .setParameter("status", "ACTIVE")
                            .setMaxResults(1)
                            .uniqueResult();

            return alert != null;

        } catch (Exception e) {

            e.printStackTrace();

            return false;
        }
    }

    // GET - Get alerts for a specific user
    public List<Alert> getAlertsByUserId(long userId) {

        try (Session session =
                     HibernateUtil.getSessionFactory().openSession()) {

            List<Alert> alerts =
                    session.createQuery(
                                    "FROM Alert al " +
                                            "WHERE al.apiId IN " +
                                            "(SELECT a.apiId FROM Api a WHERE a.userId = :userId) " +
                                            "ORDER BY al.sentAt DESC",
                                    Alert.class
                            )
                            .setParameter("userId", userId)
                            .getResultList();

            return alerts;

        } catch (Exception e) {

            e.printStackTrace();

            return List.of();
        }
    }

    // UPDATE - Resolve active alert when API becomes UP
    public void resolveActiveAlert(long apiId) {

        Transaction transaction = null;

        try (Session session =
                     HibernateUtil.getSessionFactory().openSession()) {

            transaction = session.beginTransaction();

            List<Alert> alerts =
                    session.createQuery(
                                    "FROM Alert " +
                                            "WHERE apiId = :apiId " +
                                            "AND alertStatus = :status",
                                    Alert.class
                            )
                            .setParameter("apiId", apiId)
                            .setParameter("status", "ACTIVE")
                            .getResultList();

            for (Alert alert : alerts) {

                alert.setAlertStatus("RESOLVED");

                session.merge(alert);
            }

            transaction.commit();

            if (!alerts.isEmpty()) {

                System.out.println(
                        "Alert resolved for API ID: " +
                                apiId
                );
            }

        } catch (Exception e) {

            if (transaction != null &&
                    transaction.isActive()) {

                transaction.rollback();
            }

            e.printStackTrace();
        }
    }
}