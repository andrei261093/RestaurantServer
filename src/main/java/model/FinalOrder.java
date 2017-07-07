package model;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by andreiiorga on 24/06/2017.
 */

@Entity
@Table
public class FinalOrder implements Serializable{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    @Column(length = 1500)
    private String orderJson;
    private int tableId;
    private int waiterId;
    private int price;
    private Date onCreate;

    public FinalOrder(String orderJson, int tableId, int waiterId, int price) {
        this.orderJson = orderJson;
        this.tableId = tableId;
        this.waiterId = waiterId;
        this.price = price;
    }

    public String getOrderJson() {
        return orderJson;
    }

    public void setOrderJson(String orderJson) {
        this.orderJson = orderJson;
    }

    public int getTableId() {
        return tableId;
    }

    public void setTableId(int tableId) {
        this.tableId = tableId;
    }

    public int getWaiterId() {
        return waiterId;
    }

    public void setWaiterId(int waiterId) {
        this.waiterId = waiterId;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Date getOnCreate() {
        return onCreate;
    }

    public void setOnCreate(Date onCreate) {
        this.onCreate = onCreate;
    }
}
