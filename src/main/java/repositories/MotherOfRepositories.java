package repositories;

import model.*;

/**
 * Created by andreiiorga on 15/06/2017.
 */
public class MotherOfRepositories {
    private CategoryRepository categoryRepository;
    private ProductRepository productRepository;
    private RestaurantTableRepository restaurantTableRepository;
    private WaiterRepository waiterRepository;
    private TaskRepository taskRepository;
    private ZoneRepository zoneRepository;
    private OrderRepository orderRepository;
    private FinalOrderRepository finalOrderRepository;

    public MotherOfRepositories() {
        categoryRepository = new CategoryRepository();
        productRepository = new ProductRepository();
        restaurantTableRepository = new RestaurantTableRepository();
        waiterRepository = new WaiterRepository();
        taskRepository = new TaskRepository();
        zoneRepository = new ZoneRepository();
        orderRepository = new OrderRepository();
        finalOrderRepository = new FinalOrderRepository();
    }

    public OrderRepository getOrderRepository() {
        return orderRepository;
    }

    public ZoneRepository getZoneRepository() {
        return zoneRepository;
    }

    public CategoryRepository getCategoryRepository() {
        return categoryRepository;
    }

    public ProductRepository getProductRepository() {
        return productRepository;
    }

    public RestaurantTableRepository getRestaurantTableRepository() {
        return restaurantTableRepository;
    }

    public WaiterRepository getWaiterRepository() {
        return waiterRepository;
    }

    public TaskRepository getTaskRepository() {
        return taskRepository;
    }

    public FinalOrderRepository getFinalOrderRepository() {
        return finalOrderRepository;
    }

    public void seed(){
        Category pizza = new Category("Pizza", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQqFcPFlg8v_QdOcVryz1xp2cmwaWkwbd7MYegAP1zSQHeCMHYa");
        Category friptura = new Category("Fripturi", "http://storage0.dms.mpinteractiv.ro/media/2/2/24986/10533179/14/miel-main.jpg?width=470");
        Category desert = new Category("Desert", "https://www.retetecalamama.ro/wp-content/uploads/2012/02/papanasi+cu+dulceata+si+smantana.jpg");
        Category vinuri = new Category("Vinuri", "http://marca-ro.ca/wp-content/uploads/2015/09/poza-vinuri.jpg");
        Category supe = new Category("Supe","http://ciorbesisupe.ro/wp-content/uploads/2016/03/Ciorba-de-perisoare.jpg");
        Category salate = new Category("Salate", "http://www.cevabun.ro/wp-content/uploads/2016/06/salata-pepene-feta.jpg");
        Category racoritoare = new Category("Racoritoare", "http://adevarul.ro/assets/adevarul.ro/MRImage/2015/10/28/5630bf98f5eaafab2c1bd096/646x404.jpg");



        categoryRepository.save(pizza);
        categoryRepository.save(friptura);
        categoryRepository.save(desert);
        categoryRepository.save(vinuri);
        categoryRepository.save(supe);
        categoryRepository.save(salate);
        categoryRepository.save(racoritoare);

        RestaurantTable table1 = new RestaurantTable("1","1","1234", 1);
        RestaurantTable table2 = new RestaurantTable("2","2","1234",2);

        restaurantTableRepository.save(table1);
        restaurantTableRepository.save(table2);

        Product capriciosa = new Product("Pizza Capriciosa", "32 cm","Ingrediente:\n" +
                "\n" +
                "Aluat:\n" +
                "\n" +
                "500 gr faina\n" +
                "125 ml apa\n" +
                "2,5 linguri ulei masline\n" +
                "1/2 lingurita zahar\n" +
                "1/2 lingurita sare\n" +
                "3,5 gr drojdie uscata\n" +
                "\n" +
                "Topping\n" +
                "\n" +
                "200 gr sunca presata\n" +
                "200 gr mozzarella\n" +
                "200 gr ciuperci \n" +
                "100 gr masline negre\n" +
                "\n" +
                "Sosul de rosii\n" +
                "\n" +
                "1 conserva rosii cuburi\n" +
                "oregano\n" +
                "sare de mare\n" +
                "piper", "http://www.retetekulinare.ro/wp-content/uploads/2011/11/pizza-capriciosa1.jpg", pizza.getId(), 17, 300, true);

        Product quatroStagioni = new Product("Quattro Stagioni", "32 cm","Ingrediente:\n" +
                "\n" +
                "Aluat:\n" +
                "\n" +
                "500 gr faina\n" +
                "125 ml apa\n" +
                "2,5 linguri ulei masline\n" +
                "1/2 lingurita zahar\n" +
                "1/2 lingurita sare\n" +
                "3,5 gr drojdie uscata\n" +
                "\n" +
                "Topping\n" +
                "\n" +
                "200 gr sunca presata\n" +
                "200 gr mozzarella\n" +
                "200 gr ciuperci \n" +
                "100 gr masline negre\n" +
                "\n" +
                "Sosul de rosii\n" +
                "\n" +
                "1 conserva rosii cuburi\n" +
                "oregano\n" +
                "sare de mare\n" +
                "piper", "http://www.pizzeria-largo.com/wp-content/uploads/2016/06/pizza-quatro-stagioni.jpg", pizza.getId(), 16, 320, true);

        Product porc = new Product("Friptura de porc", "cu cartofi prajiti","Ingrediente:\n" +
                "\n" +
                "Aluat:\n" +
                "\n" +
                "500 gr faina\n" +
                "125 ml apa\n" +
                "2,5 linguri ulei masline\n" +
                "1/2 lingurita zahar\n" +
                "1/2 lingurita sare\n" +
                "3,5 gr drojdie uscata\n" +
                "\n" +
                "Topping\n" +
                "\n" +
                "200 gr sunca presata\n" +
                "200 gr mozzarella\n" +
                "200 gr ciuperci \n" +
                "100 gr masline negre\n" +
                "\n" +
                "Sosul de rosii\n" +
                "\n" +
                "1 conserva rosii cuburi\n" +
                "oregano\n" +
                "sare de mare\n" +
                "piper", "http://assets.sport.ro/assets/foodstory/2013/12/13/image_galleries/3564/3-alternative-pentru-traditionala-friptura-de-porc_size1.jpg", friptura.getId(), 16, 320, true);



        Product cola = new Product("Coca Cola", "racoritoare", "Descriere lunga", "http://www.coca-cola.co.uk/content/dam/journey/gb/en/hidden/Newsroom/Coca-Cola-Zero-Sugar-launch-596x334.jpg", racoritoare.getId(), 7, 330, false);

        productRepository.save(capriciosa);
        productRepository.save(quatroStagioni);
        productRepository.save(porc);
        productRepository.save(cola);

        Zone zone1 = new Zone(1);
        zoneRepository.save(zone1);

        Waiter diana = new Waiter("Diana Stoian", "dsv", zone1.getZone(), "1234");


        waiterRepository.save(diana);

        FinalOrder finalOrder = new FinalOrder("json", 1,2, 6);
        orderRepository.save(finalOrder);
    }
}
