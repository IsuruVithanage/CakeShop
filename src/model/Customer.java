package model;

public class Customer {
    private String custID;
    private String custName;
    private String custcontact;
    private String custAddress;

    public Customer() {
    }

    public Customer(String custID, String custName, String custcontact, String custAddress) {
        this.custID = custID;
        this.custName = custName;
        this.custcontact = custcontact;
        this.custAddress = custAddress;
    }

    public String getCustID() {
        return custID;
    }

    public void setCustID(String custID) {
        this.custID = custID;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getCustcontact() {
        return custcontact;
    }

    public void setCustcontact(String custcontact) {
        this.custcontact = custcontact;
    }

    public String getCustAddress() {
        return custAddress;
    }

    public void setCustAddress(String custAddress) {
        this.custAddress = custAddress;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "custID='" + custID + '\'' +
                ", custName='" + custName + '\'' +
                ", custcontact='" + custcontact + '\'' +
                ", custAddress='" + custAddress + '\'' +
                '}';
    }
}
