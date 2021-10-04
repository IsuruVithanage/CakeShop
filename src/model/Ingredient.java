package model;

public class Ingredient {
    private String ingreID;
    private String ingreName;
    private String ingreUnit;
    private double qtyOnHand;
    private double unitePrice;

    public Ingredient() {
    }

    public Ingredient(String ingreID, String ingreName, String ingreUnit, double qtyOnHand, double unitePrice) {
        this.ingreID = ingreID;
        this.ingreName = ingreName;
        this.ingreUnit = ingreUnit;
        this.qtyOnHand = qtyOnHand;
        this.unitePrice = unitePrice;
    }

    public String getIngreID() {
        return ingreID;
    }

    public void setIngreID(String ingreID) {
        this.ingreID = ingreID;
    }

    public String getIngreName() {
        return ingreName;
    }

    public void setIngreName(String ingreName) {
        this.ingreName = ingreName;
    }

    public String getIngreUnit() {
        return ingreUnit;
    }

    public void setIngreUnit(String ingreUnit) {
        this.ingreUnit = ingreUnit;
    }

    public double getQtyOnHand() {
        return qtyOnHand;
    }

    public void setQtyOnHand(double qtyOnHand) {
        this.qtyOnHand = qtyOnHand;
    }

    public double getUnitePrice() {
        return unitePrice;
    }

    public void setUnitePrice(double unitePrice) {
        this.unitePrice = unitePrice;
    }

    @Override
    public String toString() {
        return "Ingredient{" +
                "ingreID='" + ingreID + '\'' +
                ", ingreName='" + ingreName + '\'' +
                ", ingreUnit='" + ingreUnit + '\'' +
                ", qtyOnHand=" + qtyOnHand +
                ", unitePrice=" + unitePrice +
                '}';
    }
}
