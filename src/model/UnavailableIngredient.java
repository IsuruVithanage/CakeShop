package model;

public class UnavailableIngredient {
    private String ingreID;
    private String ingreName;
    private String unit;
    private String qtyOnHand;
    private double unitePrice;

    public UnavailableIngredient() {
    }

    public UnavailableIngredient(String ingreID, String ingreName, String unit, String qtyOnHand, double unitePrice) {
        this.setIngreID(ingreID);
        this.setIngreName(ingreName);
        this.setUnit(unit);
        this.setQtyOnHand(qtyOnHand);
        this.setUnitePrice(unitePrice);
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

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getQtyOnHand() {
        return qtyOnHand;
    }

    public void setQtyOnHand(String qtyOnHand) {
        this.qtyOnHand = qtyOnHand;
    }

    public double getUnitePrice() {
        return unitePrice;
    }

    public void setUnitePrice(double unitePrice) {
        this.unitePrice = unitePrice;
    }


}
