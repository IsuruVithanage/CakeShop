package model;

public class CakeSells {
    private String cakeID;
    private int sells;

    public CakeSells() {
    }

    public CakeSells(String cakeID, int sells) {
        this.cakeID = cakeID;
        this.sells = sells;
    }

    public String getCakeID() {
        return cakeID;
    }

    public void setCakeID(String cakeID) {
        this.cakeID = cakeID;
    }

    public int getSells() {
        return sells;
    }

    public void setSells(int sells) {
        this.sells = sells;
    }

    @Override
    public String toString() {
        return "CakeSells{" +
                "cakeID='" + cakeID + '\'' +
                ", sells=" + sells +
                '}';
    }
}
