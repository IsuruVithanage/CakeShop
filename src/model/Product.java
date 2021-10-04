package model;

import view.tm.RecipeTM;

import java.util.ArrayList;

public class Product {
    private int index;
    private String cakeID;
    private ArrayList<RecipeTM> ingredients;

    public Product() {
    }

    public Product(int index, String cakeID, ArrayList<RecipeTM> ingredients) {
        this.index = index;
        this.cakeID = cakeID;
        this.ingredients = ingredients;
    }

    public String getCakeID() {
        return cakeID;
    }

    public void setCakeID(String cakeID) {
        this.cakeID = cakeID;
    }

    public ArrayList<RecipeTM> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<RecipeTM> ingredients) {
        this.ingredients = ingredients;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    public String toString() {
        return "Product{" +
                "index=" + index +
                ", cakeID='" + cakeID + '\'' +
                ", ingredients=" + ingredients +
                '}';
    }
}
