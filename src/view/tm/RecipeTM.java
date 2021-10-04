package view.tm;

import javafx.scene.control.Button;

public class RecipeTM {
    private String ingreID;
    private String ingreName;
    private String unit;
    private String qty;
    private double price;
    private Button btn;

    public RecipeTM() {
    }

    public RecipeTM(String ingreID, String ingreName, String unit, String qty, double price, Button btn) {
        this.ingreID = ingreID;
        this.ingreName = ingreName;
        this.unit = unit;
        this.qty = qty;
        this.price = price;
        this.btn = btn;
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

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Button getBtn() {
        return btn;
    }

    public void setBtn(Button btn) {
        this.btn = btn;
    }
}
