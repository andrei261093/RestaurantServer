package repositories;

import model.RestaurantTable;
import org.hibernate.query.Query;
import repositories.daoUtil.AbstractHibernateDAO;

/**
 * Created by andreiiorga on 20/06/2017.
 */
public class RestaurantTableRepository extends AbstractHibernateDAO<RestaurantTable>{
    public RestaurantTableRepository() {
        setClazz(RestaurantTable.class);
    }


    public RestaurantTable getByName(String name) {
        Query query = getCurrentSession().createQuery("from RestaurantTable where tableNo=:name");
        query.setParameter("name", name);
        RestaurantTable restaurantTable = (RestaurantTable) query.uniqueResult();
        return restaurantTable;
    }
}
