package repositories;

import model.FinalOrder;
import model.Task;
import repositories.daoUtil.AbstractHibernateDAO;

import java.util.Date;

/**
 * Created by andreiiorga on 04/07/2017.
 */
public class FinalOrderRepository extends AbstractHibernateDAO<FinalOrder>{
    public FinalOrderRepository(){
        setClazz(FinalOrder.class);
    }

    @Override
    public void save(FinalOrder entity) {
        Date now = new Date();
        entity.setOnCreate(now);
        super.save(entity);
    }

}
