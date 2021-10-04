package controller.contollerUtil;

import db.DbConnection;
import model.WorkerSalary;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SalaryControllerUtill {

    //Get workers worked days
    public int getWorkDays(String workerID, String month) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM `Order` WHERE monthname(OrderDate)='" + month + "' AND WorkerID='" + workerID + "'");
        ResultSet rst = stm.executeQuery();
        int count = 0;
        while (rst.next()) {
            count++;
        }
        return count;
    }

    //Save Salary
    public boolean saveSalary(WorkerSalary w) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("INSERT INTO Salary VALUES (?,?,?,?,?,?)");
        stm.setObject(1, w.getDate());
        stm.setObject(2, w.getWorkerID());
        stm.setObject(3, w.getWorkPerMonth());
        stm.setObject(4, w.getSalaryPerDay());
        stm.setObject(5, w.getSalary());
        stm.setObject(6, w.getState());


        return stm.executeUpdate() > 0;
    }

    //Get All Salary Details
    public ArrayList<WorkerSalary> getAllSalary() throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Salary");
        ResultSet rst = stm.executeQuery();
        ArrayList<WorkerSalary> workerSalaries = new ArrayList<>();
        while (rst.next()) {
            workerSalaries.add(new WorkerSalary(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getInt(3),
                    rst.getDouble(4),
                    rst.getDouble(5),
                    rst.getString(6)
            ));
        }
        return workerSalaries;
    }


    //Delete Salary
    public void deleteSalary(String id) throws SQLException, ClassNotFoundException {
        DbConnection.getInstance().getConnection().prepareStatement("DELETE FROM Salary WHERE WorkerID='" + id + "'").executeUpdate();
    }


    //Pay
    public boolean paySalary(String workerID, String date) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("UPDATE Salary SET PayedDate='" + date + "',State='Payed' WHERE  WorkerID='" + workerID + "'");
        return stm.executeUpdate() > 0;
    }


}
