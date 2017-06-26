package model;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andreiiorga on 24/06/2017.
 */
public class TableSession {
    List<JSONObject> orders;
    private String tableNo;
    private JSONObject recepit;


    public TableSession(String tableNo) {
        this.tableNo = tableNo;
        this.orders = new ArrayList<>();
    }

    public void addChunk(JSONObject order){
        orders.add(order);
    }

    public String getTableNo() {
        return tableNo;
    }

    public void setRecepit(JSONObject finalOrder) {
        this.recepit = finalOrder;
    }

    public List<JSONObject> getOrders() {
        return orders;
    }

    public void setOrders(List<JSONObject> orders) {
        this.orders = orders;
    }
}
