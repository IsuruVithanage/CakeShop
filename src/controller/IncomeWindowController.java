package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import controller.contollerUtil.OrderContollerUtil;
import controller.contollerUtil.SalaryControllerUtill;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import model.Order;
import model.WorkerSalary;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import util.DisplayTimeDate;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class IncomeWindowController {

    public Label lbTime;
    public Label lbDate;
    public Label lbMonthlyIncome;
    public Label lbYealyIncome;
    public Label lbYearProft;
    public BarChart incomebarchart;
    public JFXComboBox<String> cmbMonth;
    public JFXButton monthlyReport;
    public JFXComboBox<String> cmbYear;
    public JFXButton yealyReportBTN;
    ArrayList<Order> orders;
    ArrayList<WorkerSalary> salaries;
    double mounthlySalary = 0;
    double yearlySalary = 0;
    double mounthlyIncome = 0;
    double yearlyIncome = 0;
    List<String> monthlist = Arrays.asList("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December");

    LocalDate currentdate = LocalDate.now();
    Month currentmonth = currentdate.getMonth();
    int year = currentdate.getYear();

    {
        try {
            orders = new OrderContollerUtil().getAllOrder();
            salaries = new SalaryControllerUtill().getAllSalary();
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    public void initialize() {
        DisplayTimeDate.loadDateAndTime(lbDate, lbTime);
        setIncome();
        getAllsalaryTotal();
        setMonthsandYear();
        monthlyReport.setDisable(true);
        yealyReportBTN.setDisable(true);
        try {
            loadChart();
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        //Month select
        cmbMonth.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            monthlyReport.setDisable(false);
        });

        //Year select
        cmbYear.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            yealyReportBTN.setDisable(false);
        });

    }

    //Load income
    private void setIncome() {
        try {
            mounthlyIncome = new OrderContollerUtil().getcurrentMonthIncome(String.valueOf(currentmonth));
            yearlyIncome = new OrderContollerUtil().getyearlyincome(String.valueOf(year));
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        lbMonthlyIncome.setText("Rs " + mounthlyIncome);
        lbYealyIncome.setText("Rs " + yearlyIncome);
    }

    //Get Salary payament
    public void getAllsalaryTotal() {
        double expenses = 0;
        try {
            yearlySalary = new OrderContollerUtil().getyearlysalarypayment(String.valueOf(year));
            ArrayList<Order> orders = new OrderContollerUtil().getAllOrderofcurrentyear(String.valueOf(year));
            for (Order order : orders) {
                expenses += new OrderContollerUtil().getOrderExpense(order.getOrderID());
            }


        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        lbYearProft.setText("Rs " + (yearlyIncome - (yearlySalary + expenses)));
    }


    //load chart
    public void loadChart() throws SQLException, ClassNotFoundException {

        XYChart.Series set1 = new XYChart.Series<>();
        for (String month : monthlist) {
            set1.getData().add(new XYChart.Data(month, new OrderContollerUtil().getcurrentMonthIncome(month)));
        }
        incomebarchart.getData().addAll(set1);

    }

    //Load months
    public void setMonthsandYear() {
        cmbMonth.getItems().addAll(monthlist);
        cmbYear.getItems().add("2021");
    }

    //Get the yearly report
    public void getYearlyReport(MouseEvent mouseEvent) {
        try {
            ArrayList<Order> orders = new OrderContollerUtil().getAllOrderofcurrentyear(cmbYear.getSelectionModel().getSelectedItem());

            double sales = new OrderContollerUtil().getyearlyincome(cmbYear.getSelectionModel().getSelectedItem());

            double expenses = 0;
            for (Order order : orders) {
                expenses += new OrderContollerUtil().getOrderExpense(order.getOrderID());
            }

            double totalSalary = new OrderContollerUtil().getyearlysalarypayment(cmbYear.getSelectionModel().getSelectedItem());
            double profit = sales - (expenses + totalSalary);
            HashMap map = new HashMap<>();
            map.put("sales", "Rs " + sales);
            map.put("expenses", "Rs " + expenses);
            map.put("salary", "Rs " + totalSalary);
            map.put("profit", "Rs " + profit);

            JasperDesign design = JRXmlLoader.load(this.getClass().getResourceAsStream("/view/reports/YearlyIncome.jrxml"));
            JasperReport jasperReport = JasperCompileManager.compileReport(design);

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, map, new JRBeanArrayDataSource(orders.toArray()));
            JasperViewer.viewReport(jasperPrint, false);


        } catch (SQLException | ClassNotFoundException | JRException throwables) {
            throwables.printStackTrace();
        }
    }

    //Get the monthly report
    public void getMonthlyReport(MouseEvent mouseEvent) {
        try {
            ArrayList<Order> orders = new OrderContollerUtil().getAllOrderofcurrentmonth(cmbMonth.getSelectionModel().getSelectedItem());

            double sales = new OrderContollerUtil().getcurrentMonthIncome(cmbMonth.getSelectionModel().getSelectedItem());

            double expenses = 0;
            for (Order order : orders) {
                expenses += new OrderContollerUtil().getOrderExpense(order.getOrderID());
            }

            double totalSalary = new OrderContollerUtil().getmonthlysalarypayment(cmbMonth.getSelectionModel().getSelectedItem());
            double profit = sales - (expenses + totalSalary);
            HashMap map = new HashMap<>();
            map.put("sales", "Rs " + sales);
            map.put("expenses", "Rs " + expenses);
            map.put("salary", "Rs " + totalSalary);
            map.put("profit", "Rs " + profit);

            JasperDesign design = JRXmlLoader.load(this.getClass().getResourceAsStream("/view/reports/MonthlyIncomeReport.jrxml"));
            JasperReport jasperReport = JasperCompileManager.compileReport(design);

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, map, new JRBeanArrayDataSource(orders.toArray()));
            JasperViewer.viewReport(jasperPrint, false);

        } catch (SQLException | ClassNotFoundException | JRException throwables) {
            throwables.printStackTrace();
        }
    }
}
