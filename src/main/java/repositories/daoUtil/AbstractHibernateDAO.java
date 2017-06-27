package repositories.daoUtil;

import org.hibernate.Session;

import java.io.Serializable;
import java.util.List;

/**
 * Created by andreiiorga on 15/06/2017.
 */
public abstract class AbstractHibernateDAO<T extends Serializable> {
    private Class<T> clazz;
    private Session session = HibernateUtil.getSessionFactory().openSession();

    public void setClazz(final Class<T> clazzToSet) {
        clazz = clazzToSet;
    }


    public T findOne(final int id) {
        return (T) session.get(clazz, id);
    }


    public List<T> findAll() {
        return session.createQuery("from " + clazz.getName()).list();
    }


    public void save(final T entity) {
        session.beginTransaction();
        session.persist(entity);
        session.getTransaction().commit();
    }


    public void update(final T entity) {
        session.beginTransaction();
        session.update(entity);
        session.getTransaction().commit();
    }


    public void delete(final T entity) {
        session.beginTransaction();
        session.delete(entity);
        session.getTransaction().commit();
    }


    public void deleteById(final int id) {
        final T entity = findOne(id);
        session.beginTransaction();
        delete(entity);
        session.getTransaction().commit();
    }

    protected final Session getCurrentSession() {
        return session;
    }
}