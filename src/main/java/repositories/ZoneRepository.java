package repositories;

import model.Zone;
import repositories.daoUtil.AbstractHibernateDAO;

/**
 * Created by andreiiorga on 29/06/2017.
 */
public class ZoneRepository extends AbstractHibernateDAO<Zone>{
    public ZoneRepository() {
        setClazz(Zone.class);
    }
}
