package model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by andreiiorga on 29/06/2017.
 */
@Entity
@Table
public class Zone implements Serializable{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    private int zone;

    public Zone(int zone) {
        this.zone = zone;
    }

    public int getZone() {
        return zone;
    }

    public void setZone(int zone) {
        this.zone = zone;
    }
}
