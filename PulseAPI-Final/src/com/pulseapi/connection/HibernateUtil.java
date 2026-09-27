package com.pulseapi.connection;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.pulseapi.model.User;
import com.pulseapi.model.Api;
import com.pulseapi.model.Alert;
import com.pulseapi.model.MonitoringResult;

public class HibernateUtil {

    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {

        try {
            return new Configuration()
                    .configure("hibernate.cfg.xml")
                    .addAnnotatedClass(User.class)
                    .addAnnotatedClass(Api.class)
                    .addAnnotatedClass(Alert.class)
                    .addAnnotatedClass(MonitoringResult.class)
                    .buildSessionFactory();

        } catch (Throwable exception) {

            System.out.println("SessionFactory creation failed.");
            exception.printStackTrace();

            throw new ExceptionInInitializerError(exception);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void shutdown() {

        if (sessionFactory != null && !sessionFactory.isClosed()) {
            sessionFactory.close();
        }
    }
}