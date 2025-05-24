package com.radovan.play.modules;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.radovan.play.utils.HibernateUtil;
import org.hibernate.SessionFactory;

import jakarta.inject.Singleton;

public class HibernateModule extends AbstractModule {
    @Override
    protected void configure() {
        // Prazna konfiguracija, koristimo @Provides
    }

    @Provides
    @Singleton
    public SessionFactory provideSessionFactory() {
        try {
            System.out.println("Initializing Hibernate SessionFactory...");
            HibernateUtil hibernateUtil = new HibernateUtil();
            return hibernateUtil.getSessionFactory();
        } catch (Exception e) {
            System.err.println("Error creating SessionFactory: " + e.getMessage());
            throw new RuntimeException("Failed to initialize Hibernate SessionFactory", e);
        }
    }
}
