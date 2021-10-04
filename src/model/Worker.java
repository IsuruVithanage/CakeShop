package model;

public class Worker {
    private String workerID;
    private String workerName;
    private String contact;
    private String NIC;
    private String workerAddress;
    private String bankACNo;
    private String availability;

    public Worker() {
    }

    public Worker(String workerID, String workerName, String contact, String NIC, String workerAddress, String bankACNo, String availability) {
        this.workerID = workerID;
        this.workerName = workerName;
        this.contact = contact;
        this.NIC = NIC;
        this.workerAddress = workerAddress;
        this.bankACNo = bankACNo;
        this.availability = availability;
    }

    public String getWorkerID() {
        return workerID;
    }

    public void setWorkerID(String workerID) {
        this.workerID = workerID;
    }

    public String getWorkerName() {
        return workerName;
    }

    public void setWorkerName(String workerName) {
        this.workerName = workerName;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getNIC() {
        return NIC;
    }

    public void setNIC(String NIC) {
        this.NIC = NIC;
    }

    public String getWorkerAddress() {
        return workerAddress;
    }

    public void setWorkerAddress(String workerAddress) {
        this.workerAddress = workerAddress;
    }

    public String getBankACNo() {
        return bankACNo;
    }

    public void setBankACNo(String bankACNo) {
        this.bankACNo = bankACNo;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    @Override
    public String toString() {
        return "Worker{" +
                "workerID='" + workerID + '\'' +
                ", workerName='" + workerName + '\'' +
                ", contact='" + contact + '\'' +
                ", NIC='" + NIC + '\'' +
                ", workerAddress='" + workerAddress + '\'' +
                ", bankACNo='" + bankACNo + '\'' +
                ", availability='" + availability + '\'' +
                '}';
    }
}
