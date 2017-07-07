package model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by andreiiorga on 15/06/2017.
 */
@Entity
@Table
public class Category implements Serializable{
/*
    @Id
    @GeneratedValue
    private int id;
*/

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    private String name;
    private String imageUrl;

    public Category(String name, String imageUrl) {
        this.name = name;
        this.imageUrl = imageUrl;
    }

    public Category() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
