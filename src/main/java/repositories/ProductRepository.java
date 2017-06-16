package repositories;

import model.Product;
import repositories.daoUtil.AbstractHibernateDAO;

/**
 * Created by andreiiorga on 15/06/2017.
 */
public class ProductRepository extends AbstractHibernateDAO<Product> {
    public ProductRepository() {
        setClazz(Product.class);
    }
}
