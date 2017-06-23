package model;

import javax.persistence.*;
import javax.persistence.Table;
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
    @Column(length = 600)
    private String longDescription;
    private String imageUrl;
    private int category;
    private int price;
    private int weight;

    public Product(String name, String shortDescription, String longDescription, String imageUrl, int category, int price, int weight) {
        this.name = name;
        this.shortDescription = shortDescription;
        this.longDescription = longDescription;
        this.imageUrl = imageUrl;
        this.category = category;
        this.price = price;
        this.weight = weight;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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
