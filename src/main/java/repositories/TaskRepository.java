package repositories;

import model.Task;
import model.Waiter;
import org.hibernate.query.Query;
import repositories.daoUtil.AbstractHibernateDAO;

import java.util.Date;
import java.util.List;

/**
 * Created by andreiiorga on 26/06/2017.
 */
public class TaskRepository extends AbstractHibernateDAO<Task>{
    public TaskRepository() {
        setClazz(Task.class);
    }

    public List<Task> getByZone(String zone) {
        Query query = getCurrentSession().createQuery("from Task where zoneID=:zone");
        query.setParameter("zone", Integer.parseInt(zone));
        List<Task> tasks = ( List<Task>) query.list();
        return tasks;
    }

    @Override
    public void save(Task entity) {
        Date now = new Date();
        entity.setOnCreate(now);
        entity.setOnUpdate(now);
        super.save(entity);
    }

    @Override
    public void update(Task entity) {
        Date now = new Date();
        entity.setOnUpdate(now);
        super.update(entity);
    }
}
