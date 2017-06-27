package model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by andreiiorga on 26/06/2017.
 */
@Entity
@Table
public class Waiter implements Serializable {
    @Id
    @GeneratedValue
    private int id;

    private String name;
    private int zoneID;
    private String password;
    private String username;
    private String token = "";

    public Waiter() {
    }

    public Waiter(String name, String username, int zoneID, String password) {
        this.name = name;
        this.zoneID = zoneID;
        this.password = password;
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getZoneID() {
        return zoneID;
    }

    public void setZoneID(int zoneID) {
        this.zoneID = zoneID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
