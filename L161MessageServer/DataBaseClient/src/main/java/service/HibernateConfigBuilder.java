package service;

import org.hibernate.cfg.Configuration;

public interface HibernateConfigBuilder {

    Configuration build();

    HibernateConfigBuilder classes(Class[] classes);
    HibernateConfigBuilder driver(String driver);
    HibernateConfigBuilder dialect(String dialect);
    HibernateConfigBuilder user(String name);
    HibernateConfigBuilder password(String password);
    HibernateConfigBuilder database(String url);
    HibernateConfigBuilder showSql(boolean show);
    HibernateConfigBuilder hbm2ddl(String state);
    HibernateConfigBuilder lazyLoad(boolean state);
    HibernateConfigBuilder useSSL(boolean state);


}
