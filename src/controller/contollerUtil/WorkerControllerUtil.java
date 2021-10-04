package controller.contollerUtil;

import db.DbConnection;
import model.Worker;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class WorkerControllerUtil {

    //Save Wokers
    public boolean saveWorkers(Worker w) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("INSERT INTO Worker VALUES (?,?,?,?,?,?,?)");
        stm.setObject(1, w.getWorkerID());
        stm.setObject(2, w.getWorkerName());
        stm.setObject(3, w.getContact());
        stm.setObject(4, w.getNIC());
        stm.setObject(5, w.getWorkerAddress());
        stm.setObject(6, w.getBankACNo());
        stm.setObject(7, w.getAvailability());

        return stm.executeUpdate() > 0;
    }

    //Generate Worker Ids
    public String creatWorkerID() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement("SELECT WorkerID FROM Worker ORDER BY WorkerID DESC LIMIT 1").executeQuery();
        if (rst.next()) {
            int tempId = Integer.parseInt(rst.getString(1).split("-")[1]);
            tempId = tempId + 1;
            if (tempId <= 9) {
                return "W-00" + tempId;
            } else if (tempId <= 99) {
                return "W-0" + tempId;
            } else {
                return "W-" + tempId;
            }
        } else {
            return "W-001";
        }
    }

    //Get All Workers Details
    public ArrayList<Worker> getAllWorkers() throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Worker");
        ResultSet rst = stm.executeQuery();
        ArrayList<Worker> workerArrayList = new ArrayList<>();
        while (rst.next()) {
            workerArrayList.add(new Worker(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getString(6),
                    rst.getString(7)

            ));
        }
        return workerArrayList;
    }

    //Delete Worker
    public void deleteWorker(String id) throws SQLException, ClassNotFoundException {
        DbConnection.getInstance().getConnection().prepareStatement("DELETE FROM Worker WHERE WorkerID='" + id + "'").executeUpdate();
    }

    //Get All Available Workers Details
    public ArrayList<Worker> getAllAvailableWorkers() throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Worker WHERE Availability='Yes' ");
        ResultSet rst = stm.executeQuery();
        ArrayList<Worker> workerArrayList = new ArrayList<>();
        while (rst.next()) {
            workerArrayList.add(new Worker(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getString(6),
                    rst.getString(7)

            ));
        }
        return workerArrayList;
    }

    //Update Worker availability
    public boolean updateAvailability(String workerID, String state) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("UPDATE Worker SET Availability='" + state + "' WHERE  WorkerID='" + workerID + "'");
        return stm.executeUpdate() > 0;
    }


}
