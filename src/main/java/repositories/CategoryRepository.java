package repositories;

import model.Category;
import repositories.daoUtil.AbstractHibernateDAO;

/**
 * Created by andreiiorga on 15/06/2017.
 */
public class CategoryRepository extends AbstractHibernateDAO<Category>{

    public CategoryRepository() {
        setClazz(Category.class);
    }


}
