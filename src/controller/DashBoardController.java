package controller;

import controller.contollerUtil.IngredientControllerUtil;
import controller.contollerUtil.OrderContollerUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import model.CakeSells;
import model.Ingredient;
import model.Order;
import util.DisplayTimeDate;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;

public class DashBoardController {
    public Label lbTime;
    public Label lbDate;
    public BarChart ingreGraph;
    public Label lbDeliOrders;
    public Label lbWorkOrder;
    public Label ibIncome;
    public PieChart pieChart;
    ArrayList<Order> orders;
    ArrayList<Ingredient> ingredients;


    {
        try {
            orders = new OrderContollerUtil().getAllOrder();
            ingredients = new IngredientControllerUtil().getAllIngredient();
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    public void initialize() {
        DisplayTimeDate.loadDateAndTime(lbDate, lbTime);

        setdeliveredOrderCount();
        setWorkdOrderCount();
        setIncome();
        try {
            loadChart();
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

    }


    //set Delivered order count
    private void setdeliveredOrderCount() {
        int count = 0;
        for (Order order : orders) {
            if (order.getState().equals("Delivered")) {
                count++;
            }
        }
        lbDeliOrders.setText(String.format("%03d", count));

    }

    //set Working order count
    private void setWorkdOrderCount() {
        int count = 0;
        for (Order order : orders) {
            if (order.getState().equals("Not Delivered")) {
                count++;
            }
        }
        lbWorkOrder.setText(String.format("%03d", count));

    }

    //Load income
    private void setIncome() {
        LocalDate currentdate = LocalDate.now();
        Month currentmonth = currentdate.getMonth();

        double income = 0;
        try {
            income = new OrderContollerUtil().getcurrentMonthIncome(String.valueOf(currentmonth));
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        ibIncome.setText("Rs " + (income));

    }

    //Load charts
    private void loadChart() throws SQLException, ClassNotFoundException {
        XYChart.Series set1 = new XYChart.Series<>();
        for (Ingredient item : ingredients) {
            double qty = 0;
            if (item.getQtyOnHand() < 0) {
                set1.getData().add(new XYChart.Data(item.getIngreID(), qty));
            } else {
                qty = item.getQtyOnHand();
                set1.getData().add(new XYChart.Data(item.getIngreID(), qty));
            }
        }
        ingreGraph.getData().addAll(set1);

        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        ArrayList<CakeSells> cakeList = new OrderContollerUtil().getCakeSells();
        for (CakeSells item : cakeList) {
            pieChartData.add(new PieChart.Data(item.getCakeID(), item.getSells()));

        }
        pieChart.setData(pieChartData);

    }


}
