package controller.contollerUtil;

import db.DbConnection;
import model.*;
import view.tm.RecipeTM;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderContollerUtil {

    //Save data in order Table
    public boolean placeOrder(Order order, ArrayList<Product> products) {
        Connection con = null;
        try {
            con = DbConnection.getInstance().getConnection();
            con.setAutoCommit(false);
            PreparedStatement stm = con.prepareStatement("INSERT INTO `Order` VALUES(?,?,?,?,?,?,?,?)");
            stm.setObject(1, order.getOrderID());
            stm.setObject(2, order.getCustID());
            stm.setObject(3, order.getWorkerID());
            stm.setObject(4, order.getOrderDate());
            stm.setObject(5, order.getDeliveryDate());
            stm.setObject(6, order.getOrderTime());
            stm.setObject(7, order.getState());
            stm.setObject(8, order.getTotalPrice());

            if (stm.executeUpdate() > 0) {

                if (saveOrderDetail(order.getOrderID(), order.getCakeList(), products)) {
                    con.commit();
                    return true;

                } else {
                    con.rollback();
                    return false;
                }

            } else {
                con.rollback();
                return false;
            }

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        } finally {
            try {
                con.setAutoCommit(true);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return false;
    }

    //Save Data in OrderDetail table
    private boolean saveOrderDetail(String orderId, ArrayList<CakeDetail> cakeDetails, ArrayList<Product> products) throws SQLException, ClassNotFoundException {
        for (CakeDetail temp : cakeDetails
        ) {
            PreparedStatement stm = DbConnection.getInstance().
                    getConnection().
                    prepareStatement("INSERT INTO OrderDetail VALUES(?,?,?,?,?)");
            stm.setObject(1, temp.getCakeID());
            stm.setObject(2, orderId);
            stm.setObject(3, temp.getDesc());
            stm.setObject(4, temp.getWeight());
            stm.setObject(5, temp.getPrice());
            if (stm.executeUpdate() > 0) {
                if (!saveIngreDetail(products, orderId)) {
                    return false;
                }

            }else {
                return false;
            }
        }

        return true;
    }

    //Save Data in IngreDetail table
    private boolean saveIngreDetail(ArrayList<Product> products, String orderId) throws SQLException, ClassNotFoundException {
        for (Product temp : products) {
            for (RecipeTM ingredient : temp.getIngredients()) {
                PreparedStatement stm = DbConnection.getInstance().
                        getConnection().
                        prepareStatement("INSERT INTO IngreDetail VALUES(?,?,?,?,?,?)");
                stm.setObject(1, temp.getIndex());
                stm.setObject(2, ingredient.getIngreID());
                stm.setObject(3, orderId);
                stm.setObject(4, ingredient.getUnit());
                stm.setObject(5, ingredient.getQty());
                stm.setObject(6, ingredient.getPrice());
                if (stm.executeUpdate() <= 0) {
                    return false;
                }
            }

        }
        return true;
    }

    //Generate Order Ids
    public String createOrderID() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement("SELECT OrderID FROM `Order` ORDER BY OrderID DESC LIMIT 1").executeQuery();
        if (rst.next()) {
            int tempId = Integer.parseInt(rst.getString(1).split("-")[1]);
            tempId = tempId + 1;
            if (tempId <= 9) {
                return "O-00" + tempId;
            } else if (tempId <= 99) {
                return "O-0" + tempId;
            } else {
                return "O-" + tempId;
            }
        } else {
            return "O-001";
        }
    }

    //Get All Order Details
    public ArrayList<Order> getAllOrder() throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM `Order`");
        ResultSet rst = stm.executeQuery();
        ArrayList<Order> orderList = new ArrayList<>();
        while (rst.next()) {
            orderList.add(new Order(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(6),
                    rst.getString(5),
                    rst.getDouble(8),
                    rst.getString(7),
                    null

            ));
        }
        return orderList;
    }

    //Update Ingredients QTY on hand
    public boolean updateQTY(String ingredientID, double qty) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("UPDATE Ingredient SET QtyOnHand=(QtyOnHand-" + qty + ")WHERE  IngredientID='" + ingredientID + "'");
        return stm.executeUpdate() > 0;
    }

    //Update Order deilivery stat
    public boolean updateDeliver(String orderID) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("UPDATE `Order` SET State='Delivered' WHERE  OrderID='" + orderID + "'");
        return stm.executeUpdate() > 0;
    }

    //Get Cake sells
    public ArrayList<CakeSells> getCakeSells() throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT CakeID,count(CakeID) from OrderDetail group by CakeID;");
        ResultSet rst = stm.executeQuery();
        ArrayList<CakeSells> cakeSellsArrayList = new ArrayList<>();
        while (rst.next()) {
            cakeSellsArrayList.add(new CakeSells(
                    rst.getString(1),
                    rst.getInt(2)

            ));
        }
        return cakeSellsArrayList;

    }

    //Delete Ingredient
    public void deleteOrder(String id) throws SQLException, ClassNotFoundException {
        DbConnection.getInstance().getConnection().prepareStatement("DELETE FROM `Order` WHERE OrderID='" + id + "'").executeUpdate();
    }

    //Get order products
    public ArrayList<CakeDetail> getOrderProducts(String id) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * from OrderDetail WHERE OrderID='" + id + "';");
        ResultSet rst = stm.executeQuery();
        ArrayList<CakeDetail> cakeSellsArrayList = new ArrayList<>();
        while (rst.next()) {
            cakeSellsArrayList.add(new CakeDetail(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(4),
                    rst.getString(3),
                    rst.getDouble(5)


            ));
        }
        return cakeSellsArrayList;

    }

    //Get order products
    public ArrayList<IngreDetail> getOrderIngre(String id) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * from IngreDetail WHERE OrderID='" + id + "';");
        ResultSet rst = stm.executeQuery();
        ArrayList<IngreDetail> productArrayList = new ArrayList<>();
        while (rst.next()) {
            productArrayList.add(new IngreDetail(
                    rst.getInt(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getDouble(6)

            ));
        }
        return productArrayList;

    }

    //Search Order
    public ArrayList<Order> getSearchOrder(String name) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM `Order` WHERE OrderID LIKE '%" + name + "%'");
        ResultSet rst = stm.executeQuery();
        ArrayList<Order> orders = new ArrayList<>();
        while (rst.next()) {
            orders.add(new Order(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(6),
                    rst.getString(5),
                    rst.getDouble(8),
                    rst.getString(7),
                    null
            ));
        }
        return orders;
    }

    //Get all order detail
    public OrderDetail getAllOrderDetail(String id) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement(" SELECT * FROM customer c INNER JOIN `Order` o ON c.CustID=o.CustID WHERE o.OrderID IN('" + id + "')");
        ResultSet rst = stm.executeQuery();
        OrderDetail order = null;
        while (rst.next()) {
            order = new OrderDetail(
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getString(12)
            );
        }
        return order;
    }

    //Get monthly income
    public double getcurrentMonthIncome(String month) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT SUM(TotalPrice) AS 'Monthly Income' From `Order` WHERE monthname(DeliveryDate)='" + month + "' AND State='Delivered'");
        ResultSet rst = stm.executeQuery();
        double income = 0;
        if (rst.next()) {
            income = rst.getDouble(1);
        }

        return income;

    }

    //Get yearly income
    public double getyearlyincome(String year) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT SUM(TotalPrice) From `Order` WHERE year(DeliveryDate)='" + year + "' AND State='Delivered'");
        ResultSet rst = stm.executeQuery();
        double income = 0;
        if (rst.next()) {
            income = rst.getDouble(1);
        }

        return income;

    }

    //Get yearly salary payments
    public double getyearlysalarypayment(String year) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT SUM(Salary) From Salary WHERE year(PayedDate)='" + year + "' AND State='Payed'");
        ResultSet rst = stm.executeQuery();
        double payement = 0;
        if (rst.next()) {
            payement = rst.getDouble(1);
        }

        return payement;

    }

    //Get All Order Details by month
    public ArrayList<Order> getAllOrderofcurrentmonth(String month) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM `Order` WHERE monthname(DeliveryDate)='" + month + "' AND State='Delivered'");
        ResultSet rst = stm.executeQuery();
        ArrayList<Order> orderList = new ArrayList<>();
        while (rst.next()) {
            orderList.add(new Order(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(6),
                    rst.getString(5),
                    rst.getDouble(8),
                    rst.getString(7),
                    null

            ));
        }
        return orderList;
    }

    //Get monthly salary payments
    public double getmonthlysalarypayment(String month) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT SUM(Salary) From Salary WHERE monthname(PayedDate)='" + month + "' AND State='Payed'");
        ResultSet rst = stm.executeQuery();
        double payement = 0;
        if (rst.next()) {
            payement = rst.getDouble(1);
        }

        return payement;

    }


    //Get order expense
    public double getOrderExpense(String id) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT SUM(Price) FROM IngreDetail WHERE OrderID='" + id + "'");
        ResultSet rst = stm.executeQuery();
        double total = 0;
        if (rst.next()) {
            total = rst.getDouble(1);
        }
        return total;

    }


    //Get All Order Details by year
    public ArrayList<Order> getAllOrderofcurrentyear(String year) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM `Order` WHERE year(DeliveryDate)='" + year + "' AND State='Delivered'");
        ResultSet rst = stm.executeQuery();
        ArrayList<Order> orderList = new ArrayList<>();
        while (rst.next()) {
            orderList.add(new Order(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(6),
                    rst.getString(5),
                    rst.getDouble(8),
                    rst.getString(7),
                    null

            ));
        }
        return orderList;
    }


}
