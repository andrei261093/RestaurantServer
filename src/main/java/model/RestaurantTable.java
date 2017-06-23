package model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by andreiiorga on 19/06/2017.
 */

@Entity
@Table
public class RestaurantTable implements Serializable{

    @Id
    @GeneratedValue
    private int id;
    private String tableNo;
    private String tableZone;
    private String tablePassword;

    public RestaurantTable(String tableNo, String tableZone, String tablePassword) {
        this.tableNo = tableNo;
        this.tableZone = tableZone;
        this.tablePassword = tablePassword;
    }

    public RestaurantTable() {
    }

    public String getTableNo() {
        return tableNo;
    }

    public void setTableNo(String tableNo) {
        this.tableNo = tableNo;
    }

    public String getTableZone() {
        return tableZone;
    }

    public void setTableZone(String tableZone) {
        this.tableZone = tableZone;
    }

    public String getTablePassword() {
        return tablePassword;
    }

    public void setTablePassword(String tablePassword) {
        this.tablePassword = tablePassword;
    }
}