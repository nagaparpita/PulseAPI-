package com.pulseapi.repo;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.pulseapi.connection.HibernateUtil;
import com.pulseapi.model.Api;

public class ApiCRUD {

    // INSERT
    public static void insertApi(Api api) {

        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            transaction = session.beginTransaction();

            session.persist(api);

            transaction.commit();

            System.out.println("API inserted successfully.");
            System.out.println("Generated API ID: " + api.getApiId());

        } catch (Exception exception) {

            if (transaction != null) {
                transaction.rollback();
            }

            exception.printStackTrace();
        }
    }

    // READ - Get API by ID
    public static void getApiById(long apiId) {

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            Api api = session.find(Api.class, apiId);

            if (api != null) {
                System.out.println("API found:");
                System.out.println(api);
            } else {
                System.out.println("API not found.");
            }

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    // READ - Get all APIs
    public static void getAllApis() {

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            List<Api> apis = session
                    .createQuery("FROM Api", Api.class)
                    .getResultList();

            if (apis.isEmpty()) {
                System.out.println("No APIs found.");
            } else {
                System.out.println("All APIs:");

                for (Api api : apis) {
                    System.out.println(api);
                }
            }

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    // UPDATE
    public static void updateApi(
            long apiId,
            long userId,
            String apiName,
            String apiUrl,
            String httpMethod,
            int monitoringInterval,
            String status) {

        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            transaction = session.beginTransaction();

            Api api = session.find(Api.class, apiId);

            if (api != null) {

                api.setUserId(userId);
                api.setApiName(apiName);
                api.setApiUrl(apiUrl);
                api.setHttpMethod(httpMethod);
                api.setMonitoringInterval(monitoringInterval);
                api.setStatus(status);

                session.merge(api);

                transaction.commit();

                System.out.println("API updated successfully.");

            } else {

                System.out.println("API not found.");
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
    public static void deleteApi(long apiId) {

        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            transaction = session.beginTransaction();

            Api api = session.find(Api.class, apiId);

            if (api != null) {

                session.remove(api);

                transaction.commit();

                System.out.println("API deleted successfully.");

            } else {

                System.out.println("API not found.");
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