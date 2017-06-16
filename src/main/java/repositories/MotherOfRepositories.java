package repositories;

/**
 * Created by andreiiorga on 15/06/2017.
 */
public class MotherOfRepositories {
    private CategoryRepository categoryRepository;
    private ProductRepository productRepository;

    public MotherOfRepositories() {
        categoryRepository = new CategoryRepository();
        productRepository = new ProductRepository();
    }

    public CategoryRepository getCategoryRepository() {
        return categoryRepository;
    }

    public ProductRepository getProductRepository() {
        return productRepository;
    }
}
