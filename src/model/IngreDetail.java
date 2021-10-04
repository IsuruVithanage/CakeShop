package model;

public class IngreDetail {
    private int orderIndex;
    private String ingreID;
    private String orderID;
    private String unit;
    private String qty;
    private double price;

    public IngreDetail() {
    }

    public IngreDetail(int orderIndex, String ingreID, String orderID, String unit, String qty, double price) {
        this.orderIndex = orderIndex;
        this.ingreID = ingreID;
        this.orderID = orderID;
        this.unit = unit;
        this.qty = qty;
        this.price = price;
    }

    public int getOrderIndex() {
        return orderIndex;
    }

    public void setOrderIndex(int orderIndex) {
        this.orderIndex = orderIndex;
    }

    public String getIngreID() {
        return ingreID;
    }

    public void setIngreID(String ingreID) {
        this.ingreID = ingreID;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "IngreDetail{" +
                "orderIndex=" + orderIndex +
                ", ingreID='" + ingreID + '\'' +
                ", orderID='" + orderID + '\'' +
                ", unit='" + unit + '\'' +
                ", qty=" + qty +
                ", price=" + price +
                '}';
    }
}
