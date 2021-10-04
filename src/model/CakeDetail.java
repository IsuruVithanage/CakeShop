package model;

public class CakeDetail {
    private String cakeID;
    private String orderID;
    private String weight;
    private String desc;
    private double price;

    public CakeDetail() {
    }

    public CakeDetail(String cakeID, String orderID, String weight, String desc, double price) {
        this.cakeID = cakeID;
        this.orderID = orderID;
        this.weight = weight;
        this.desc = desc;
        this.price = price;
    }

    public String getCakeID() {
        return cakeID;
    }

    public void setCakeID(String cakeID) {
        this.cakeID = cakeID;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "CakeDetail{" +
                "cakeID='" + cakeID + '\'' +
                ", orderID='" + orderID + '\'' +
                ", weight=" + weight +
                ", desc='" + desc + '\'' +
                ", price=" + price +
                '}';
    }
}
