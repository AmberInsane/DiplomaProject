package com.tms.stankevich.util;

import com.tms.stankevich.domain.movie.Hall;
import com.tms.stankevich.domain.movie.Movie;
import com.tms.stankevich.domain.movie.Seance;
import com.tms.stankevich.domain.movie.Ticket;
import com.tms.stankevich.domain.user.Authority;
import com.tms.stankevich.domain.user.User;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.HashMap;
import java.util.Map;

public class HibernateUtil {
    private static StandardServiceRegistry registry;
    private static SessionFactory sessionFactory;

    final static Logger logger = Logger.getLogger(HibernateUtil.class);

    public static SessionFactory getSessionFactory() {
        logger.info("try get session Factory");
        if (sessionFactory == null) {
            try {
                StandardServiceRegistryBuilder registryBuilder =
                        new StandardServiceRegistryBuilder();

                Map<String, String> settings = new HashMap<String, String>();
                settings.put("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
                settings.put("hibernate.connection.url", "jdbc:mysql://localhost:3306/movie_friend_schema?useSSL=false&serverTimezone=UTC");
                settings.put("hibernate.connection.username", "root");
                settings.put("hibernate.connection.password", "-Root123321-");
                settings.put("hibernate.show_sql", "true"); //показывать sql
                settings.put("hibernate.hbm2ddl.auto", "update"); //update - создавать новые таблицы

                registryBuilder.applySettings(settings);

                registry = registryBuilder.build();

                MetadataSources sources = new MetadataSources(registry)
                        .addAnnotatedClass(User.class)
                        .addAnnotatedClass(Authority.class)
                        .addAnnotatedClass(Hall.class)
                        .addAnnotatedClass(Movie.class)
                        .addAnnotatedClass(Seance.class)
                        .addAnnotatedClass(Ticket.class);

                Metadata metadata = sources.getMetadataBuilder().build();

                sessionFactory = metadata.getSessionFactoryBuilder().build();
            } catch (Exception e) {
                System.out.println("SessionFactory creation failed");
                if (registry != null) {
                    StandardServiceRegistryBuilder.destroy(registry);
                }
            }
        }
        return sessionFactory;
    }

    public static void shutdown() {
        if (registry != null) {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}
