package view.tm;

import javafx.scene.control.Button;

public class IngredientTM {
    private String ingreID;
    private String ingreName;
    private String unit;
    private String qtyOnHand;
    private double unitePrice;
    private Button removebtn;

    public IngredientTM() {
    }

    public IngredientTM(String ingreID, String ingreName, String unit, String qtyOnHand, double unitePrice, Button removebtn) {
        this.ingreID = ingreID;
        this.ingreName = ingreName;
        this.unit = unit;
        this.qtyOnHand = qtyOnHand;
        this.unitePrice = unitePrice;
        this.removebtn = removebtn;
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

    public Button getRemovebtn() {
        return removebtn;
    }

    public void setRemovebtn(Button removebtn) {
        this.removebtn = removebtn;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    @Override
    public String toString() {
        return "IngredientTM{" +
                "ingreID='" + ingreID + '\'' +
                ", ingreName='" + ingreName + '\'' +
                ", unit='" + unit + '\'' +
                ", qtyOnHand='" + qtyOnHand + '\'' +
                ", unitePrice=" + unitePrice +
                ", removebtn=" + removebtn +
                '}';
    }
}
