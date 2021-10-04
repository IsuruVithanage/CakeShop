package model;

import java.util.ArrayList;

public class Order {
    private String orderID;
    private String custID;
    private String workerID;
    private String orderDate;
    private String orderTime;
    private String deliveryDate;
    private double totalPrice;
    private String state;
    private ArrayList<CakeDetail> cakeList;

    public Order() {
    }

    public Order(String orderID, String custID, String workerID, String orderDate, String orderTime, String deliveryDate, double totalPrice, String state, ArrayList<CakeDetail> cakeList) {
        this.orderID = orderID;
        this.custID = custID;
        this.workerID = workerID;
        this.orderDate = orderDate;
        this.orderTime = orderTime;
        this.deliveryDate = deliveryDate;
        this.totalPrice = totalPrice;
        this.state = state;
        this.cakeList = cakeList;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getCustID() {
        return custID;
    }

    public void setCustID(String custID) {
        this.custID = custID;
    }

    public String getWorkerID() {
        return workerID;
    }

    public void setWorkerID(String workerID) {
        this.workerID = workerID;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public ArrayList<CakeDetail> getCakeList() {
        return cakeList;
    }

    public void setCakeList(ArrayList<CakeDetail> cakeList) {
        this.cakeList = cakeList;
    }
}
