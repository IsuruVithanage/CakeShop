package view.tm;

import javafx.scene.control.Button;

public class OrderDetailTM {
    private String orderID;
    private String custID;
    private String workerID;
    private String orderDate;
    private String orderTime;
    private String deliveryDate;
    private double totalprice;
    private Button btn;

    public OrderDetailTM() {
    }

    public OrderDetailTM(String orderID, String custID, String workerID, String orderDate, String orderTime, String deliveryDate, double totalprice, Button btn) {
        this.orderID = orderID;
        this.custID = custID;
        this.workerID = workerID;
        this.orderDate = orderDate;
        this.orderTime = orderTime;
        this.deliveryDate = deliveryDate;
        this.totalprice = totalprice;
        this.btn = btn;
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

    public double getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(double totalprice) {
        this.totalprice = totalprice;
    }

    public Button getBtn() {
        return btn;
    }

    public void setBtn(Button btn) {
        this.btn = btn;
    }

    @Override
    public String toString() {
        return "OrderDetailTM{" +
                "orderID='" + orderID + '\'' +
                ", custID='" + custID + '\'' +
                ", workerID='" + workerID + '\'' +
                ", orderDate='" + orderDate + '\'' +
                ", orderTime='" + orderTime + '\'' +
                ", deliveryDate='" + deliveryDate + '\'' +
                ", totalprice=" + totalprice +
                ", btn=" + btn +
                '}';
    }
}
