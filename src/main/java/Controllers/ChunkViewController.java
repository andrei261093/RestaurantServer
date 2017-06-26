package Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Product;
import org.json.JSONArray;
import org.json.JSONObject;


/**
 * Created by andreiiorga on 25/06/2017.
 */
public class ChunkViewController {
    JSONObject chunk;
    @FXML
    private TableColumn<ProductTemp, String> productId;

    @FXML
    private Label tableNoLbl;

    @FXML
    private TableView<ProductTemp> productTable;

    @FXML
    private TableColumn<ProductTemp, String> productName;

    @FXML
    private TableColumn<ProductTemp, String> productDescription;

    @FXML
    private TableColumn<ProductTemp, String> productPrice;

    @FXML
    public void initialize() {
        productId.setCellValueFactory(new PropertyValueFactory<ProductTemp, String>("id"));
        productName.setCellValueFactory(new PropertyValueFactory<ProductTemp, String>("name"));
        productDescription.setCellValueFactory(new PropertyValueFactory<ProductTemp, String>("description"));
        productPrice.setCellValueFactory(new PropertyValueFactory<ProductTemp, String>("price"));
    }

    public void setChunk(JSONObject chunk) {
        this.chunk = chunk;
        int count = 1;
        ObservableList<ProductTemp> data = FXCollections.observableArrayList();
        JSONArray products = chunk.getJSONArray("products");
        for (int i = 0; i < products.length(); i++) {
            JSONObject product = products.getJSONObject(i);
            ProductTemp productTemp = new ProductTemp(count + "", product.getString("name"), product.getString("shortDescription"), product.getInt("price") + "");
            data.add(productTemp);
            count++;
        }
        productTable.setItems(data);
        tableNoLbl.setText(chunk.getInt("tableNo") + "");
    }

    public class ProductTemp {
        private String id;
        private String name;
        private String description;
        private String price;

        public ProductTemp(String id, String name, String description, String price) {
            this.id = id;
            this.name = name;
            this.description = description;
            this.price = price;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }
    }
}
