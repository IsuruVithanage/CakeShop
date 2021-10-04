package controller.contollerUtil;

import db.DbConnection;
import model.Customer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerController {

    //Save Customer Data in Customer Table
    public boolean saveCustomer(Customer c) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("INSERT INTO Customer VALUES (?,?,?,?)");
        stm.setObject(1, c.getCustID());
        stm.setObject(2, c.getCustName());
        stm.setObject(3, c.getCustcontact());
        stm.setObject(4, c.getCustAddress());

        return stm.executeUpdate() > 0;
    }


    //Generate CustIDs
    public String createCust() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement("SELECT CustID FROM Customer ORDER BY CustID DESC LIMIT 1").executeQuery();
        if (rst.next()) {
            int tempId = Integer.parseInt(rst.getString(1).split("-")[1]);
            tempId = tempId + 1;
            if (tempId <= 9) {
                return "C-00" + tempId;
            } else if (tempId <= 99) {
                return "C-0" + tempId;
            } else {
                return "C-" + tempId;
            }
        } else {
            return "C-001";
        }
    }

    //Get All Customer Details
    public Customer getAllCustomer(String id) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Customer WHERE CustID='" + id + "'");
        ResultSet rst = stm.executeQuery();
        Customer customer = null;
        while (rst.next()) {
            customer = new Customer(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4)

            );
        }
        return customer;
    }


}
