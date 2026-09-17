package com.pulseapi.repo;

import com.pulseapi.connection.HibernateUtil;
import com.pulseapi.model.MonitoringResult;

import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class MonitoringResultCRUD {

    public void insertMonitoringResult(MonitoringResult result) {

        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            transaction = session.beginTransaction();

            session.persist(result);

            transaction.commit();

            System.out.println("Monitoring result inserted successfully.");
            System.out.println("Generated Result ID: " + result.getResultId());

        } catch (Exception e) {

            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }

            e.printStackTrace();
        }
    }

    public void getAllMonitoringResults() {

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            List<MonitoringResult> results =
                    session.createQuery(
                            "FROM MonitoringResult",
                            MonitoringResult.class
                    ).getResultList();

            if (results.isEmpty()) {

                System.out.println("No monitoring results found.");

            } else {

                System.out.println("All Monitoring Results:");

                for (MonitoringResult result : results) {
                    System.out.println(result);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}