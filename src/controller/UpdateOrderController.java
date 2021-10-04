package controller;

import controller.contollerUtil.CakeController;
import controller.contollerUtil.OrderContollerUtil;
import controller.contollerUtil.WorkerControllerUtil;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import model.*;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import util.DisplayTimeDate;
import view.tm.OrderDetailTM;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

public class UpdateOrderController extends OrderContollerUtil {
    public Label lbTime;
    public Label lbDate;
    public TableView<OrderDetailTM> tblOrder;
    public TableColumn<OrderDetailTM, String> colOrderID;
    public TableColumn<OrderDetailTM, String> colCustID;
    public TableColumn<OrderDetailTM, String> colWorkerID;
    public TableColumn<OrderDetailTM, String> colCakeID;
    public TableColumn<OrderDetailTM, String> colWeight;
    public TableColumn<OrderDetailTM, String> colDesign;
    public TableColumn<OrderDetailTM, String> colOrderDate;
    public TableColumn<OrderDetailTM, String> colDelever;
    public TableColumn<OrderDetailTM, String> colPrice;
    public TableColumn<OrderDetailTM, String> colCancel;
    public AnchorPane contextUpdateOrder;
    public TextField txtsearch;
    ObservableList<OrderDetailTM> obList = FXCollections.observableArrayList();
    int index;

    public void initialize() {
        DisplayTimeDate.loadDateAndTime(lbDate, lbTime);

        colOrderID.setCellValueFactory(new PropertyValueFactory<>("orderID"));
        colCustID.setCellValueFactory(new PropertyValueFactory<>("custID"));
        colWorkerID.setCellValueFactory(new PropertyValueFactory<>("workerID"));
        colOrderDate.setCellValueFactory(new PropertyValueFactory<>("orderDate"));
        colDelever.setCellValueFactory(new PropertyValueFactory<>("deliveryDate"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("totalprice"));
        colCancel.setCellValueFactory(new PropertyValueFactory<>("btn"));


        try {
            loadOrderDetail(getAllOrder());
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        tblOrder.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            index = (Integer) newValue;
        });

        //Search bar
        txtsearch.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                search(newValue);
            }
        });

    }

    //Search and Display
    public void search(String name) {
        try {
            ArrayList<Order> orders = getSearchOrder(name);
            tblOrder.getItems().clear();
            loadOrderDetail(orders);
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();

        }
    }

    //Load Order details
    public void loadOrderDetail(ArrayList<Order> orders) throws SQLException, ClassNotFoundException {
        String workerID;

        for (Order order : orders) {
            if (order.getWorkerID() == null) {
                workerID = "Empty";
            } else {
                workerID = order.getWorkerID();
            }

            if (order.getState().equals("Not Delivered")) {
                Button btn = new Button("Cancel");
                OrderDetailTM tm = new OrderDetailTM(
                        order.getOrderID(),
                        order.getCustID(),
                        workerID,
                        order.getOrderDate(),
                        order.getOrderTime(),
                        order.getDeliveryDate(),
                        order.getTotalPrice(),
                        btn

                );
                cancelOrder(btn, tm);

                obList.add(tm);
            }
        }
        tblOrder.setItems(obList);
    }

    //Get Cake Name
    public String getCakeName(String id) {
        ArrayList<Cake> cakeArrayList = null;
        try {
            cakeArrayList = new CakeController().getAllCake();
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        for (Cake cake : cakeArrayList) {
            if (cake.getCakeID().equals(id)) {
                return cake.getCakeName();
            }
        }
        return null;
    }

    //set delevery
    public void deleveryonaction(MouseEvent mouseEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delivering the Order");
        alert.setContentText("Are you sure you want to deliver this order ?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {

            try {
                updateDeliver(obList.get(index).getOrderID());
                new WorkerControllerUtil().updateAvailability(obList.get(index).getWorkerID(), "Yes");
            } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
            }


            try {
                OrderDetail order = getAllOrderDetail(obList.get(index).getOrderID());
                ArrayList<CakeDetail> product = getOrderProducts(obList.get(index).getOrderID());

                JasperDesign design = JRXmlLoader.load(this.getClass().getResourceAsStream("/view/reports/OrderDetail.jrxml"));
                JasperReport jasperReport = JasperCompileManager.compileReport(design);
                ArrayList<ProductDetail> list = new ArrayList<>();

                for (CakeDetail cakeDetail : product) {
                    list.add(new ProductDetail(
                            getCakeName(cakeDetail.getCakeID()),
                            cakeDetail.getWeight(),
                            cakeDetail.getPrice()
                    ));
                }


                HashMap map = new HashMap();

                map.put("total", "Rs " + order.getTotal());
                map.put("custName", order.getCustName());
                map.put("custContact", order.getCustContact());
                map.put("custAddress", order.getCustAddress());
                map.put("orderID", order.getOrderID());

                JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, map, new JRBeanArrayDataSource(list.toArray()));
                JasperViewer.viewReport(jasperPrint, false);


                obList.remove(index);
                tblOrder.refresh();
            } catch (SQLException | ClassNotFoundException | JRException throwables) {
                throwables.printStackTrace();
            }


        }
    }

    //Update Order
    public void updateOrder() throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Update");
        alert.setContentText("Are you sure you want to update this ?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/PlaceOrderWindow.fxml"));
            Parent parent = loader.load();
            PlaceOrderWindowController controller = loader.getController();
            try {
                controller.loadToUpdate(obList.get(index), getOrderProducts(obList.get(index).getOrderID()), getOrderIngre(obList.get(index).getOrderID()));
            } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
            }
            contextUpdateOrder.getChildren().clear();
            contextUpdateOrder.getChildren().add(parent);
        }
    }

    //Cancel order
    private void cancelOrder(Button btn, OrderDetailTM tm) {
        btn.setOnAction((e) -> {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setContentText("Are you sure you want to cancel this order ?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                try {
                    deleteOrder(tm.getOrderID());
                } catch (SQLException | ClassNotFoundException throwables) {
                    throwables.printStackTrace();
                }
                obList.remove(tm);
                tblOrder.refresh();
            }
        });
    }
}
