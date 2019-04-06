package database.service;

import database.model.DataSet;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class HibernateService {
    private final SessionFactory sessionFactory;

    public HibernateService(Class<? extends DataSet>...classes) {
        Configuration configuration = new Configuration();

        for (Class<? extends DataSet> aClass : classes) {
            configuration.addAnnotatedClass(aClass);
        }

        configuration.setProperty("hibernate.dialect","org.hibernate.dialect.PostgreSQL95Dialect");
        configuration.setProperty("hibernate.connection.driver_class","org.postgresql.Driver");
        configuration.setProperty("hibernate.connection.url","jdbc:postgresql://localhost:5432/dataSetBase");
        configuration.setProperty("hibernate.connection.username","postgres");
        configuration.setProperty("hibernate.connection.password","15041");
        configuration.setProperty("hibernate.show_sql","true");
        configuration.setProperty("hibernate.hbm2ddl.auto","create");
        configuration.setProperty("hibernate.connection.useSSL","false");
        configuration.setProperty("hibernate.enable_lazy_load_no_trans","true");

        sessionFactory = createSessionFactory(configuration);
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
