package service;


public class HibernateConfigHelper {

    private HibernateConfigHelper(){

    }

    private static HibernateConfigBuilderImpl configBuilder = new HibernateConfigBuilderImpl();

    public static HibernateConfigBuilderImpl builder(){
        return configBuilder;
    }

}
