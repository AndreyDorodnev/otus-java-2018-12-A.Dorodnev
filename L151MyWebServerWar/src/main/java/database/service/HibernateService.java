package database.service;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class HibernateService {
    private final SessionFactory sessionFactory;

    public HibernateService(Class...classes){
        Configuration configuration = getConfig(classes);
        sessionFactory = createSessionFactory(configuration);
    }

    public Configuration getConfig(Class[] classes){
        return HibernateConfigHelper.builder().classes(classes)
                .dialect("org.hibernate.dialect.PostgreSQL95Dialect")
                .driver("org.postgresql.Driver")
                .database("jdbc:postgresql://localhost:5432/dataSetBase")
                .user("postgres")
                .password("15041")
                .showSql(true)
                .hbm2ddl("create")
                .useSSL(false)
                .lazyLoad(true)
                .build();
    }

    private static SessionFactory createSessionFactory(Configuration configuration){
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
        builder.applySettings(configuration.getProperties());
        ServiceRegistry serviceRegistry = builder.build();
        return configuration.buildSessionFactory(serviceRegistry);
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
