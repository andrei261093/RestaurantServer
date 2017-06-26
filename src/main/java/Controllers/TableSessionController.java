package Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.TableSession;
import org.json.JSONObject;

import java.io.IOException;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by andreiiorga on 25/06/2017.
 */
public class TableSessionController {


    @FXML
    private ListView<TableSession> tableSessionsList;
    @FXML
    private ListView<JSONObject> orderChuncksList;

    private List<TableSession> tables;

    private int count;

    @FXML
    public void initialize() {
        tables = new ArrayList<>();
        ObservableList<TableSession> myObservableList = FXCollections.observableList(tables);
        tableSessionsList.setItems(myObservableList);

        tableSessionsList.setCellFactory(new Callback<ListView<TableSession>, ListCell<TableSession>>() {
            @Override
            public ListCell<TableSession> call(ListView<TableSession> p) {

                ListCell<TableSession> cell = new ListCell<TableSession>() {

                    @Override
                    protected void updateItem(TableSession t, boolean bln) {
                        super.updateItem(t, bln);
                        if (t != null) {
                            setText("Table No: " + t.getTableNo());
                        }
                    }

                };

                return cell;
            }
        });

        orderChuncksList.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent click) {

                if (click.getClickCount() == 2) {
                    JSONObject chunk = orderChuncksList.getSelectionModel().getSelectedItem();
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/chunckView.fxml"));
                        Stage primaryStage = new Stage();
                        Parent root2 = loader.load();
                        primaryStage.setTitle("Licenta Andrei Iorga 2017");
                        primaryStage.setScene(new Scene(root2));
                        ChunkViewController chunkViewController = loader.getController();
                        chunkViewController.setChunk(chunk);
                        primaryStage.show();

                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }

    public void setTableSessions(List<TableSession> tables) {
        this.tables = tables;
        ObservableList<TableSession> myObservableList = FXCollections.observableList(tables);
        tableSessionsList.setItems(myObservableList);

        tableSessionsList.setCellFactory(new Callback<ListView<TableSession>, ListCell<TableSession>>() {
            @Override
            public ListCell<TableSession> call(ListView<TableSession> p) {

                ListCell<TableSession> cell = new ListCell<TableSession>() {

                    @Override
                    protected void updateItem(TableSession t, boolean bln) {
                        super.updateItem(t, bln);
                        if (t != null) {
                            setText("Table No: " + t.getTableNo());
                        }
                    }

                };

                return cell;
            }
        });

    }

    public void tableSessionsOnMouseClicked(MouseEvent mouseEvent) {
        TableSession tableSession = tableSessionsList.getSelectionModel().getSelectedItem();
        count = 0;
        ObservableList<JSONObject> myObservableList = FXCollections.observableList(tableSession.getOrders());
        orderChuncksList.setItems(myObservableList);

        orderChuncksList.setCellFactory(new Callback<ListView<JSONObject>, ListCell<JSONObject>>() {
            @Override
            public ListCell<JSONObject> call(ListView<JSONObject> p) {

                ListCell<JSONObject> cell = new ListCell<JSONObject>() {

                    @Override
                    protected void updateItem(JSONObject t, boolean bln) {
                        super.updateItem(t, bln);
                        if (t != null) {
                            setText("Chunk No: " + count + " Price: " + t.getInt("totalPrice"));
                            count++;
                        }
                    }

                };

                return cell;
            }
        });
    }

    public void refreshLists(ActionEvent actionEvent) {
        refreshTableSessionsList();
        refreshOrderChuncksList();
    }

    public void refreshTableSessionsList() {
        ObservableList<TableSession> myObservableList = FXCollections.observableList(tables);
        tableSessionsList.setItems(myObservableList);

        tableSessionsList.setCellFactory(new Callback<ListView<TableSession>, ListCell<TableSession>>() {
            @Override
            public ListCell<TableSession> call(ListView<TableSession> p) {

                ListCell<TableSession> cell = new ListCell<TableSession>() {

                    @Override
                    protected void updateItem(TableSession t, boolean bln) {
                        super.updateItem(t, bln);
                        if (t != null) {
                            setText("Table No: " + t.getTableNo());
                        }
                    }

                };

                return cell;
            }
        });
    }

    public void refreshOrderChuncksList() {
        TableSession tableSession = tableSessionsList.getSelectionModel().getSelectedItem();

        if (tableSession != null) {

            ObservableList<JSONObject> myObservableList = FXCollections.observableList(tableSession.getOrders());
            orderChuncksList.setItems(myObservableList);

            orderChuncksList.setCellFactory(new Callback<ListView<JSONObject>, ListCell<JSONObject>>() {
                @Override
                public ListCell<JSONObject> call(ListView<JSONObject> p) {
                    count = 1;
                    ListCell<JSONObject> cell = new ListCell<JSONObject>() {

                        @Override
                        protected void updateItem(JSONObject t, boolean bln) {
                            super.updateItem(t, bln);
                            if (t != null) {
                                setText("Chunk No: " + count + " Price: " + t.getInt("totalPrice"));
                                count++;
                            }
                        }

                    };

                    return cell;
                }
            });
        }else{
            orderChuncksList.getItems().clear();
        }
    }

    public void showChunkDetails(MouseEvent mouseEvent) {

    }
}
