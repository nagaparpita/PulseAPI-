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
    public static boolean deleteApi(long apiId) {

        Transaction transaction = null;

        try (Session session =
                     HibernateUtil.getSessionFactory().openSession()) {

            // Start database transaction
            transaction = session.beginTransaction();

            // Find API using API ID
            Api api = session.find(Api.class, apiId);

            if (api == null) {

                System.out.println("API not found.");

                transaction.rollback();

                return false;
            }

            /*
             * Delete monitoring results first.
             * This is required because monitoring_results
             * has a foreign key connected to the API.
             */
            session.createMutationQuery(
                            "DELETE FROM MonitoringResult WHERE apiId = :apiId"
                    )
                    .setParameter("apiId", apiId)
                    .executeUpdate();

            /*
             * Delete alerts related to this API.
             * This keeps the database relationship clean.
             */
            session.createMutationQuery(
                            "DELETE FROM Alert WHERE apiId = :apiId"
                    )
                    .setParameter("apiId", apiId)
                    .executeUpdate();

            /*
             * Now delete the API.
             */
            session.remove(api);

            // Save all changes
            transaction.commit();

            System.out.println("API deleted successfully.");

            return true;

        } catch (Exception exception) {

            /*
             * Rollback if any database error occurs.
             */
            if (transaction != null &&
                    transaction.isActive()) {

                transaction.rollback();
            }

            System.out.println("API deletion failed.");
            exception.printStackTrace();

            return false;
        }
    }

    // READ - Get APIs by User ID
    public static List<Api> getApisByUserId(long userId) {

        try (Session session =
                     HibernateUtil.getSessionFactory().openSession()) {

            List<Api> apis =
                    session.createQuery(
                                    "FROM Api WHERE userId = :userId",
                                    Api.class
                            )
                            .setParameter(
                                    "userId",
                                    userId
                            )
                            .getResultList();

            return apis;

        } catch (Exception exception) {

            exception.printStackTrace();

            return List.of();
        }
    }
    // READ - Get all APIs for monitoring
    public static List<Api> getAllApisForMonitoring() {

        try (Session session =
                     HibernateUtil.getSessionFactory().openSession()) {

            List<Api> apis =
                    session.createQuery(
                                    "FROM Api",
                                    Api.class
                            )
                            .getResultList();

            return apis;

        } catch (Exception exception) {

            exception.printStackTrace();

            return List.of();
        }
    }

    // UPDATE - Update API monitoring status in database
    public static void updateApiStatus(long apiId, String status) {

        Transaction transaction = null;

        try (Session session =
                     HibernateUtil.getSessionFactory().openSession()) {

            // Start database transaction
            transaction = session.beginTransaction();

            // Find API using API ID
            Api api = session.find(Api.class, apiId);

            if (api != null) {

                // Change status from UNKNOWN to UP or DOWN
                api.setStatus(status);

                // Save updated API
                session.merge(api);

                // Commit changes to MySQL
                transaction.commit();

                System.out.println(
                        "API status updated: " +
                                api.getApiName() +
                                " -> " +
                                status
                );

            } else {

                System.out.println("API not found.");

                transaction.rollback();
            }

        } catch (Exception exception) {

            // Rollback if any database error occurs
            if (transaction != null &&
                    transaction.isActive()) {

                transaction.rollback();
            }

            exception.printStackTrace();
        }
    }
}