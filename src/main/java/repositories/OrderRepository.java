package repositories;

import model.FinalOrder;
import repositories.daoUtil.AbstractHibernateDAO;

/**
 * Created by andreiiorga on 29/06/2017.
 */
public class OrderRepository extends AbstractHibernateDAO<FinalOrder> {
    public OrderRepository() {
        setClazz(FinalOrder.class);
    }
}
