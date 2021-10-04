package model;

public class Recipe {
    private String cakeID;
    private String ingreID;
    private String ingreName;
    private String unit;
    private double unitPrice;
    private double qty;

    public Recipe() {
    }

    public Recipe(String cakeID, String ingreID, String ingreName, String unit, double unitPrice, double qty) {
        this.cakeID = cakeID;
        this.ingreID = ingreID;
        this.ingreName = ingreName;
        this.unit = unit;
        this.unitPrice = unitPrice;
        this.qty = qty;
    }

    public String getCakeID() {
        return cakeID;
    }

    public void setCakeID(String cakeID) {
        this.cakeID = cakeID;
    }

    public String getIngreID() {
        return ingreID;
    }

    public void setIngreID(String ingreID) {
        this.ingreID = ingreID;
    }

    public double getQty() {
        return qty;
    }

    public void setQty(double qty) {
        this.qty = qty;
    }

    public String getIngreName() {
        return ingreName;
    }

    public void setIngreName(String ingreName) {
        this.ingreName = ingreName;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "cakeID='" + cakeID + '\'' +
                ", ingreID='" + ingreID + '\'' +
                ", ingreName='" + ingreName + '\'' +
                ", unit=" + unit +
                ", unitPrice=" + unitPrice +
                ", qty=" + qty +
                '}';
    }
}
