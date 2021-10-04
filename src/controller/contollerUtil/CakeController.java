package controller.contollerUtil;

import db.DbConnection;
import model.Cake;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CakeController {

    //Save Cake
    public boolean saveCake(Cake c) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("INSERT INTO Cake VALUES (?,?,?,?)");
        stm.setObject(1, c.getCakeID());
        stm.setObject(2, c.getCakeName());
        stm.setObject(3, c.getWeight());
        stm.setObject(4, c.getCakePrice());

        return stm.executeUpdate() > 0;
    }

    //Generate Ingredient Ids
    public String creatCakeID() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement("SELECT CakeID FROM Cake ORDER BY CakeID DESC LIMIT 1").executeQuery();
        if (rst.next()) {
            int tempId = Integer.parseInt(rst.getString(1).split("-")[1]);
            tempId = tempId + 1;
            if (tempId <= 9) {
                return "P-00" + tempId;
            } else if (tempId <= 99) {
                return "P-0" + tempId;
            } else {
                return "P-" + tempId;
            }
        } else {
            return "P-001";
        }
    }

    //Retrive data from the database
    private ResultSet getData(String query) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement(query);
        return stm.executeQuery();
    }

    //Get All Cake Details
    public ArrayList<Cake> getAllCake() throws SQLException, ClassNotFoundException {
        ResultSet rst=getData("SELECT * FROM Cake");
        ArrayList<Cake> cakeArrayList = new ArrayList<>();
        while (rst.next()) {
            cakeArrayList.add(new Cake(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getDouble(3),
                    rst.getDouble(4)

            ));
        }
        return cakeArrayList;
    }

    //Delete Ingredient
    public void deleteCake(String id) throws SQLException, ClassNotFoundException {
        DbConnection.getInstance().getConnection().prepareStatement("DELETE FROM Cake WHERE CakeID='" + id + "'").executeUpdate();
    }

    //Search Ingredient
    public ArrayList<Cake> getSearchCake(String name) throws SQLException, ClassNotFoundException {
        ResultSet rst = getData("SELECT * FROM Cake WHERE CakeName LIKE '%" + name + "%'");
        ArrayList<Cake> cakeArrayList = new ArrayList<>();
        while (rst.next()) {
            cakeArrayList.add(new Cake(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getDouble(3),
                    rst.getDouble(4)

            ));
        }
        return cakeArrayList;
    }
}
