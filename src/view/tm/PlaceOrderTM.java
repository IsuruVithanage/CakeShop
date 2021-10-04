package view.tm;

import javafx.scene.control.Button;

public class PlaceOrderTM {
    private String cakeID;
    private String weight;
    private double price;
    private Button remove;

    public PlaceOrderTM() {
    }

    public PlaceOrderTM(String cakeID, String weight, double price, Button remove) {
        this.cakeID = cakeID;
        this.weight = weight;
        this.price = price;
        this.remove = remove;
    }

    public String getCakeID() {
        return cakeID;
    }

    public void setCakeID(String cakeID) {
        this.cakeID = cakeID;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Button getRemove() {
        return remove;
    }

    public void setRemove(Button remove) {
        this.remove = remove;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "PlaceOrderTM{" +
                "cakeID='" + cakeID + '\'' +
                ", weight='" + weight + '\'' +
                ", price=" + price +
                ", remove=" + remove +
                '}';
    }
}
