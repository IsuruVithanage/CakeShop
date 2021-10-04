package controller.contollerUtil;

import db.DbConnection;
import model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserUtil {

    //Save User
    public boolean saveUser(User u) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("INSERT INTO UserTable VALUES (?,?,?)");
        stm.setObject(1, u.getWorkerID());
        stm.setObject(2, u.getUsername());
        stm.setObject(3, u.getPassword());

        return stm.executeUpdate() > 0;
    }

    //Get All Users Details
    public ArrayList<User> getAllUsers() throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM UserTable");
        ResultSet rst = stm.executeQuery();
        ArrayList<User> users = new ArrayList<>();
        while (rst.next()) {
            users.add(new User(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3)
            ));
        }
        return users;
    }

    //Delete User
    public void deleteUser(String id) throws SQLException, ClassNotFoundException {
        DbConnection.getInstance().getConnection().prepareStatement("DELETE FROM UserTable WHERE WorkerID='" + id + "'").executeUpdate();
    }

    //Get Users Details
    public User getUsers(String username) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM UserTable WHERE UserName='" + username + "'");
        ResultSet rst = stm.executeQuery();
        User user = null;
        if (rst.next()) {
            user = new User(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3)
            );
        }
        return user;
    }


}
