package controller;

import com.jfoenix.controls.JFXButton;
import controller.contollerUtil.CakeController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import model.Cake;
import util.DisplayTimeDate;
import view.tm.CakeTM;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

public class CakeWindowController extends CakeController {
    public Label lbTime;
    public Label lbDate;
    public AnchorPane contextPlaceOrder;
    public TextField txtSearchCake;
    public Label lbCakeID;
    public TextField txtCakeName;
    public TextField txtPrice;
    public TableView<CakeTM> tblCake;
    public TableColumn<CakeTM, String> colID;
    public TableColumn<CakeTM, String> colName;
    public TableColumn<CakeTM, String> colPrice;
    public TableColumn<CakeTM, String> colRemove;
    public TableColumn<CakeTM, String> colWeight;
    public TextField txtWeight;
    public JFXButton updateBTN;
    public JFXButton addBTN;
    public Label lbname;
    public Label lbprice;
    public Label lbweight;
    ObservableList<CakeTM> obList = FXCollections.observableArrayList();

    public void initialize() {
        colID.setCellValueFactory(new PropertyValueFactory<>("cakeID"));
        colName.setCellValueFactory(new PropertyValueFactory<>("cakeName"));
        colWeight.setCellValueFactory(new PropertyValueFactory<>("weight"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("cakePrice"));
        colRemove.setCellValueFactory(new PropertyValueFactory<>("remove"));

        DisplayTimeDate.loadDateAndTime(lbDate, lbTime);

        updateBTN.setDisable(true);

        try {
            loadCakeDetail(getAllCake());

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        setCakeID();

        txtCakeName.setOnAction(event -> {
            validate(KeyCode.ENTER, txtWeight, "^[A-z ]{3,20}$", txtCakeName, lbname);
        });

        txtWeight.setOnAction(event -> {
            validate(KeyCode.ENTER, txtPrice, "^[1-9][0-9]*$", txtWeight, lbweight);
        });

        txtPrice.setOnAction(event -> {
            validate(KeyCode.ENTER, txtPrice, "^[1-9][0-9]*([.][0-9]{2})?$", txtPrice, lbprice);
        });

        //Search bar
        txtSearchCake.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                search(newValue);
            }
        });

        //Table select
        tblCake.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            updateBTN.setDisable(false);
            addBTN.setDisable(true);
            try {
                setCakeData(obList.get((Integer) newValue).getCakeID());
            } catch (Exception e) {
                //new Alert(Alert.AlertType.WARNING, "Error").show();
            }
        });
    }

    //Validate textfields
    public void validate(KeyCode keyCode, TextField txt, String regex, TextField txtNow, Label label) {
        if (keyCode == KeyCode.ENTER) {
            if (!txtNow.getText().matches(regex)) {
                label.setStyle("-fx-text-fill: red");
                txtNow.setStyle("-fx-border-color: red");
                txtNow.setText("");
            } else {
                label.setStyle("-fx-text-fill: #ff7197");
                txtNow.setStyle("-fx-border-color: #ff7197");
                txt.requestFocus();
            }
        }

    }

    //Add ingredient
    public void addCake(MouseEvent mouseEvent) {

        if (!txtCakeName.getText().trim().isEmpty() && !txtWeight.getText().trim().isEmpty() && !txtPrice.getText().trim().isEmpty()) {
            lbname.setStyle("-fx-text-fill: #ff7197");
            txtCakeName.setStyle("-fx-border-color:  #ff7197");
            lbweight.setStyle("-fx-text-fill: #ff7197");
            txtWeight.setStyle("-fx-border-color:  #ff7197");
            lbprice.setStyle("-fx-text-fill: #ff7197");
            txtPrice.setStyle("-fx-border-color:  #ff7197");
            Cake cake = new Cake(
                    lbCakeID.getText(),
                    txtCakeName.getText(),
                    Double.parseDouble(txtWeight.getText()),
                    Double.parseDouble(txtPrice.getText())
            );

            save(cake);

        } else if (txtCakeName.getText().trim().isEmpty()) {
            lbname.setStyle("-fx-text-fill: red");
            txtCakeName.setStyle("-fx-border-color: red");
        } else if (txtWeight.getText().trim().isEmpty()) {
            lbweight.setStyle("-fx-text-fill: red");
            txtWeight.setStyle("-fx-border-color: red");
        } else {
            lbprice.setStyle("-fx-text-fill: red");
            txtPrice.setStyle("-fx-border-color: red");
        }
    }

    //Save CAke In the Database
    public void save(Cake cake) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK) {
            try {
                if (saveCake(cake)) {
                    Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                    alert2.setTitle("Message");
                    alert2.setContentText("Saved..");
                    alert2.show();

                    tblCake.getItems().clear();

                    try {
                        loadCakeDetail(getAllCake());
                    } catch (SQLException | ClassNotFoundException throwables) {
                        throwables.printStackTrace();
                    }

                    setCakeID();

                } else {
                    new Alert(Alert.AlertType.WARNING, "Try Again..").show();
                }
            } catch (Exception e) {
                new Alert(Alert.AlertType.WARNING, "Duplicate Entry..").show();
            }
        }
    }

    //Set the Cake ID in the label
    private void setCakeID() {
        try {
            lbCakeID.setText(creatCakeID());
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

    }

    //Load date in to the table
    private void loadCakeDetail(ArrayList<Cake> cakes) {
        for (Cake cake : cakes) {
            Button btn = new Button("Remove");
            CakeTM tm = new CakeTM(
                    cake.getCakeID(),
                    cake.getCakeName(),
                    cake.getWeight(),
                    cake.getCakePrice(),
                    btn
            );
            obList.add(tm);
            removeCake(btn, tm);
        }
        tblCake.setItems(obList);
    }

    //Remove a Cakes from the table
    public void removeCake(Button btn, CakeTM tm) {
        btn.setOnAction((e) -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Warning");
            alert.setContentText("Are you sure ?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                try {
                    deleteCake(tm.getCakeID());
                } catch (SQLException | ClassNotFoundException throwables) {
                    throwables.printStackTrace();
                }
                obList.remove(tm);
                tblCake.refresh();
                setCakeID();
            }
        });
    }

    //Search and Display
    public void search(String name) {
        try {
            ArrayList<Cake> cakeArrayList = getSearchCake(name);
            tblCake.getItems().clear();
            loadCakeDetail(cakeArrayList);
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();

        }
    }

    //Update or restore Ingredients
    public void updateBtn(MouseEvent mouseEvent) {
        if (!txtCakeName.getText().trim().isEmpty() && !txtWeight.getText().trim().isEmpty() && !txtPrice.getText().trim().isEmpty()) {
            lbname.setStyle("-fx-text-fill: #ff7197");
            txtCakeName.setStyle("-fx-border-color:  #ff7197");
            lbweight.setStyle("-fx-text-fill: #ff7197");
            txtWeight.setStyle("-fx-border-color:  #ff7197");
            lbprice.setStyle("-fx-text-fill: #ff7197");
            txtPrice.setStyle("-fx-border-color:  #ff7197");
            Cake cake = new Cake(
                    lbCakeID.getText(),
                    txtCakeName.getText(),
                    Double.parseDouble(txtWeight.getText()),
                    Double.parseDouble(txtPrice.getText())
            );

            Button btn = new Button("Remove");
            CakeTM tm = new CakeTM(
                    lbCakeID.getText(),
                    txtCakeName.getText(),
                    Double.parseDouble(txtWeight.getText()),
                    Double.parseDouble(txtPrice.getText()),
                    btn
            );
            removeCake(btn, tm);

            int rownumber = isExists(tm, obList);
            if (rownumber == -1) {
                new Alert(Alert.AlertType.WARNING, "No record found..").show();
            } else {
                try {
                    deleteCake(tm.getCakeID());
                } catch (SQLException | ClassNotFoundException throwables) {
                    throwables.printStackTrace();
                }
                obList.remove(rownumber);
                save(cake);

            }
            clearField();
            setCakeID();
            addBTN.setDisable(false);
            updateBTN.setDisable(true);

        } else if (txtCakeName.getText().trim().isEmpty()) {
            lbname.setStyle("-fx-text-fill: red");
            txtCakeName.setStyle("-fx-border-color: red");
        } else if (txtWeight.getText().trim().isEmpty()) {
            lbweight.setStyle("-fx-text-fill: red");
            txtWeight.setStyle("-fx-border-color: red");
        } else {
            lbprice.setStyle("-fx-text-fill: red");
            txtPrice.setStyle("-fx-border-color: red");
        }

    }

    //Clear text fields
    private void clearField() {
        txtCakeName.clear();
        txtWeight.clear();
        txtPrice.clear();
    }

    //Check whether their is equal cake in th obList
    private int isExists(CakeTM tm, ObservableList<CakeTM> obList) {
        for (int i = 0; i < obList.size(); i++) {
            if (tm.getCakeID().equals(obList.get(i).getCakeID())) {
                return i;
            }
        }
        return -1;
    }

    //Load cake details in to text fields
    public void setCakeData(String id) throws SQLException, ClassNotFoundException {
        ArrayList<Cake> cakeArrayList = getAllCake();

        for (Cake cake : cakeArrayList) {
            if (id.equals(cake.getCakeID())) {
                lbCakeID.setText(cake.getCakeID());
                txtCakeName.setText(cake.getCakeName());
                txtWeight.setText(String.valueOf(cake.getWeight()));
                txtPrice.setText(String.valueOf(cake.getCakePrice()));
                break;
            }
        }
    }


}
