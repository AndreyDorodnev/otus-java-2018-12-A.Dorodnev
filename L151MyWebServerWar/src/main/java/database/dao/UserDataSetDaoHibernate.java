package database.dao;

import database.model.DataSet;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class UserDataSetDaoHibernate implements DataSetDao{

    private Session session;

    public UserDataSetDaoHibernate(Session session) {
        this.session = session;
    }

    @Override
    public <T extends DataSet> void save(T user) {
        session.save(user);
    }

    @Override
    public <T extends DataSet> T read(long id, Class<T> clazz) {
//        return session.load(clazz,id);
        return session.get(clazz,id);
    }

    @Override
    public <T extends DataSet> void delete(T user) {
        Object instance = session.load(user.getClass(),user.getId());
        if(instance!=null){
            session.delete(instance);
        }
    }

    @Override
    public <T extends DataSet> void deleteById(long id, Class<T> clazz) {
        Object instance = session.load(clazz,id);
        if(instance!=null){
            session.delete(instance);
        }
    }

    @Override
    public <T extends DataSet> List<T> readAll(Class<T> clazz) {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<T> criteria = builder.createQuery(clazz);
        criteria.from(clazz);
        return session.createQuery(criteria).list();
    }

        public <T extends DataSet> T readByName(String name, Class<T> clazz) {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<T> criteria = builder.createQuery(clazz);
        Root<T> from = criteria.from(clazz);
        criteria.where(builder.equal(from.get("name"),name));
        org.hibernate.query.Query<T> query = session.createQuery(criteria);
        return query.uniqueResult();
    }


}

