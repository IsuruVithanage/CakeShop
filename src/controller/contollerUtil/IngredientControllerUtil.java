package controller.contollerUtil;

import db.DbConnection;
import model.Ingredient;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class IngredientControllerUtil {

    //Save Ingredient
    public boolean saveIngredient(Ingredient i) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("INSERT INTO Ingredient VALUES (?,?,?,?,?)");
        stm.setObject(1, i.getIngreID());
        stm.setObject(2, i.getIngreName());
        stm.setObject(3, i.getIngreUnit());
        stm.setObject(4, i.getQtyOnHand());
        stm.setObject(5, i.getUnitePrice());

        return stm.executeUpdate() > 0;
    }

    //Generate Ingredient Ids
    public String creatIngreID() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement("SELECT IngredientID FROM Ingredient ORDER BY IngredientID DESC LIMIT 1").executeQuery();
        if (rst.next()) {
            int tempId = Integer.parseInt(rst.getString(1).split("-")[1]);
            tempId = tempId + 1;
            if (tempId <= 9) {
                return "I-00" + tempId;
            } else if (tempId <= 99) {
                return "I-0" + tempId;
            } else {
                return "I-" + tempId;
            }
        } else {
            return "I-001";
        }
    }

    //Get All Ingredient Details
    public ArrayList<Ingredient> getAllIngredient() throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Ingredient");
        ResultSet rst = stm.executeQuery();
        ArrayList<Ingredient> ingredients = new ArrayList<>();
        while (rst.next()) {
            ingredients.add(new Ingredient(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getDouble(4),
                    rst.getDouble(5)
            ));
        }
        return ingredients;
    }

    //Delete Ingredient
    public void deleteIngredient(String id) throws SQLException, ClassNotFoundException {
        DbConnection.getInstance().getConnection().prepareStatement("DELETE FROM Ingredient WHERE IngredientID='" + id + "'").executeUpdate();
    }

    //Search Ingredient
    public ArrayList<Ingredient> getSearchIngredient(String name) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Ingredient WHERE IngredientName LIKE '%" + name + "%'");
        ResultSet rst = stm.executeQuery();
        ArrayList<Ingredient> ingredients = new ArrayList<>();
        while (rst.next()) {
            ingredients.add(new Ingredient(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getDouble(4),
                    rst.getDouble(5)
            ));
        }
        return ingredients;
    }

    //Update item QTY on hand
    public boolean updateQTY(String ingreID, double qty) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("UPDATE Ingredient SET QtyOnHand=(QtyOnHand+" + qty + ")WHERE  IngredientID='" + ingreID + "'");
        return stm.executeUpdate() > 0;
    }

}
