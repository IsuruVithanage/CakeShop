package model;

public class Cake {
    private String cakeID;
    private String cakeName;
    private double weight;
    private double cakePrice;

    public Cake() {
    }

    public Cake(String cakeID, String cakeName, double weight, double cakePrice) {
        this.cakeID = cakeID;
        this.cakeName = cakeName;
        this.weight = weight;
        this.cakePrice = cakePrice;
    }

    public Cake(String cakeID, String cakeName, double cakePrice) {
        this.cakeID = cakeID;
        this.cakeName = cakeName;
        this.cakePrice = cakePrice;
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

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "Cake{" +
                "cakeID='" + cakeID + '\'' +
                ", cakeName='" + cakeName + '\'' +
                ", weight=" + weight +
                ", cakePrice=" + cakePrice +
                '}';
    }
}
