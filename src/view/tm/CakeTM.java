package view.tm;

import javafx.scene.control.Button;

public class CakeTM {
    private String cakeID;
    private String cakeName;
    private double weight;
    private double cakePrice;
    private Button remove;

    public CakeTM() {
    }

    public CakeTM(String cakeID, String cakeName, double weight, double cakePrice, Button remove) {
        this.cakeID = cakeID;
        this.cakeName = cakeName;
        this.cakePrice = cakePrice;
        this.weight = weight;
        this.remove = remove;
    }

    public String getCakeID() {
        return cakeID;
    }

    public void setCakeID(String cakeID) {
        this.cakeID = cakeID;
    }

    public String getCakeName() {
        return cakeName;
    }

    public void setCakeName(String cakeName) {
        this.cakeName = cakeName;
    }

    public double getCakePrice() {
        return cakePrice;
    }

    public void setCakePrice(double cakePrice) {
        this.cakePrice = cakePrice;
    }

    public Button getRemove() {
        return remove;
    }

    public void setRemove(Button remove) {
        this.remove = remove;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "CakeTM{" +
                "cakeID='" + cakeID + '\'' +
                ", cakeName='" + cakeName + '\'' +
                ", weight=" + weight +
                ", cakePrice=" + cakePrice +
                ", remove=" + remove +
                '}';
    }
}
