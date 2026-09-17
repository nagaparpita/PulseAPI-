package com.pulseapi.main;

import com.pulseapi.connection.HibernateUtil;

public class TestConnection {

    public static void main(String[] args) {

        try {
            HibernateUtil.getSessionFactory();

            System.out.println("Hibernate Connection Successful");

        } catch (Exception exception) {

            System.out.println("Hibernate Connection Failed");
            exception.printStackTrace();

        } finally {

            HibernateUtil.getSessionFactory().close();
        }
    }
}