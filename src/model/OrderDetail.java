package model;

public class OrderDetail {
    private String custName;
    private String custAddress;
    private String  custContact;
    private String orderID;
    private String total;

    public OrderDetail() {
    }

    public OrderDetail(String custName, String custAddress, String custContact, String orderID, String total) {
        this.custName = custName;
        this.custAddress = custAddress;
        this.custContact = custContact;
        this.orderID = orderID;
        this.total = total;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getCustAddress() {
        return custAddress;
    }

    public void setCustAddress(String custAddress) {
        this.custAddress = custAddress;
    }

    public String getCustContact() {
        return custContact;
    }

    public void setCustContact(String custContact) {
        this.custContact = custContact;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "OrderDetail{" +
                "custName='" + custName + '\'' +
                ", custAddress='" + custAddress + '\'' +
                ", custContact='" + custContact + '\'' +
                ", orderID='" + orderID + '\'' +
                ", total='" + total + '\'' +
                '}';
    }
}
