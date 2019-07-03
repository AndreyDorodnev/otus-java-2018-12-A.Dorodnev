package database.service;

import org.hibernate.cfg.Configuration;

import java.util.Properties;

public class HibernateConfigBuilderImpl implements HibernateConfigBuilder {

    private Class[] classes;
    private Properties properties;

    public HibernateConfigBuilderImpl() {
        properties = new Properties();
    }

    @Override
    public Configuration build() {
        Configuration configuration = new Configuration();
        for (Class aClass : classes) {
            configuration.addAnnotatedClass(aClass);
        }
        configuration.setProperties(properties);
        return configuration;
    }

    @Override
    public HibernateConfigBuilder classes(Class[] classes) {
        this.classes = classes;
        return this;
    }

    @Override
    public HibernateConfigBuilder driver(String driver) {
        properties.put("hibernate.connection.driver_class",driver);
        return this;
    }

    @Override
    public HibernateConfigBuilder dialect(String dialect) {
        properties.put("hibernate.dialect",dialect);
        return this;
    }

    @Override
    public HibernateConfigBuilder user(String name) {
        properties.put("hibernate.connection.username",name);
        return this;
    }

    @Override
    public HibernateConfigBuilder password(String password) {
        properties.put("hibernate.connection.password",password);
        return this;
    }

    @Override
    public HibernateConfigBuilder database(String url) {
        properties.put("hibernate.connection.url",url);
        return this;
    }

    @Override
    public HibernateConfigBuilder showSql(boolean show) {
        properties.put("hibernate.show_sql", String.valueOf(show));
        return this;
    }

    @Override
    public HibernateConfigBuilder hbm2ddl(String state) {
        properties.put("hibernate.hbm2ddl.auto",state);
        return this;
    }

    @Override
    public HibernateConfigBuilder lazyLoad(boolean state) {
        properties.put("hibernate.enable_lazy_load_no_trans",String.valueOf(state));
        return this;
    }

    @Override
    public HibernateConfigBuilder useSSL(boolean state) {
        properties.put("hibernate.connection.useSSL",String.valueOf(state));
        return this;
    }
}
