package controller.contollerUtil;

import db.DbConnection;
import model.Recipe;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RecipeControllerUtil {

    //Save Recipe
    public boolean saveRecipe(Recipe r) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("INSERT INTO Recipe VALUES (?,?,?)");
        stm.setObject(1, r.getIngreID());
        stm.setObject(2, r.getCakeID());
        stm.setObject(3, r.getQty());

        return stm.executeUpdate() > 0;
    }


    //Get recipe Details
    public ArrayList<Recipe> getRecipe(String id) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Recipe WHERE CakeID='" + id + "'");
        ResultSet rst = stm.executeQuery();
        ArrayList<Recipe> recipes = new ArrayList<>();
        while (rst.next()) {
            recipes.add(new Recipe(
                    rst.getString(2),
                    rst.getString(1),
                    null,
                    null,
                    0,
                    rst.getDouble(3)
            ));
        }
        return recipes;
    }

    //Delete Recipe
    public void deleteResIngre(String ingreID, String cakeID) throws SQLException, ClassNotFoundException {
        DbConnection.getInstance().getConnection().prepareStatement("DELETE FROM Recipe WHERE IngredientID='" + ingreID + "' AND CakeID='" + cakeID + "'").executeUpdate();
    }

}
