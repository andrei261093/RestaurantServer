package repositories;

import model.Product;
import model.RestaurantTable;
import model.Waiter;
import org.hibernate.query.Query;
import repositories.daoUtil.AbstractHibernateDAO;

/**
 * Created by andreiiorga on 26/06/2017.
 */
public class WaiterRepository extends AbstractHibernateDAO<Waiter>{
    public WaiterRepository() {
        setClazz(Waiter.class);
    }

    public Waiter getByName(String username) {
        Query query = getCurrentSession().createQuery("from Waiter where username=:username");
        query.setParameter("username", username);
        Waiter waiter = (Waiter) query.uniqueResult();
        getCurrentSession().refresh(waiter);
        return waiter;
    }

    public Waiter getByZone(String zone) {
        Query query = getCurrentSession().createQuery("from Waiter where zoneID=:zone");
        query.setParameter("zone", Integer.parseInt(zone));
        Waiter waiter = (Waiter) query.uniqueResult();
        return waiter;
    }

}
