package controller.contollerUtil;

import db.DbConnection;
import model.UnavailableIngredient;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UnavailableUtil {

    //Save Unavailable Ingredient
    public boolean saveunavailableIngredient(UnavailableIngredient i) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("INSERT INTO UnavailableList VALUES (?,?,?,?)");
        stm.setObject(1, i.getIngreID());
        stm.setObject(2, i.getIngreName());
        stm.setObject(3, i.getQtyOnHand());
        stm.setObject(4, i.getUnitePrice());

        return stm.executeUpdate() > 0;
    }
}
