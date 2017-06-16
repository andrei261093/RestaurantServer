package model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by andreiiorga on 15/06/2017.
 */
@Entity
@Table
public class Product implements Serializable{
    @Id
    @GeneratedValue
    private int id;

    private String name;
    private String shortDescription;
    private String longDescription;
    private int category;
    private int price;
    private int weight;

    public Product(String name, String shortDescription, String longDescription, int category, int price, int weight) {
        this.name = name;
        this.shortDescription = shortDescription;
        this.longDescription = longDescription;
        this.category = category;
        this.price = price;
        this.weight = weight;
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

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}
