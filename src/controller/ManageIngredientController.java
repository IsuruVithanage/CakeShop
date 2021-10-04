package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import controller.contollerUtil.IngredientControllerUtil;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import model.Ingredient;
import util.DisplayTimeDate;
import view.tm.IngredientTM;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

public class ManageIngredientController extends IngredientControllerUtil {
    public Label lbTime;
    public Label lbDate;
    public Label lbIngreID;
    public TextField txtIngreName;
    public TextField txtQtyOnHand;
    public TextField txtIngreUnit;
    public TextField txtUnitPrice;
    public TableView<IngredientTM> tblIngredient;
    public TableColumn<IngredientTM, String> colID;
    public TableColumn<IngredientTM, String> colName;
    public TableColumn<IngredientTM, String> colUnit;
    public TableColumn<IngredientTM, String> colQty;
    public TableColumn<IngredientTM, String> colUnitPrice;
    public TableColumn<IngredientTM, String> colRemove;
    public JFXButton addBtn;
    public TextField txtNewQtyOnHand11;
    public TextField txtsearch;
    public JFXComboBox<String> cmbScale;
    public Label lbname;
    public Label lbcurrentqty;
    public Label lbunit;
    public JFXButton updatebtn;
    public Label lbprice;
    public Label lbboughtqty;
    ObservableList<IngredientTM> obList = FXCollections.observableArrayList();

    public void initialize() {

        colID.setCellValueFactory(new PropertyValueFactory<>("ingreID"));
        colName.setCellValueFactory(new PropertyValueFactory<>("ingreName"));
        colUnit.setCellValueFactory(new PropertyValueFactory<>("ingreUnit"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));
        colUnit.setCellValueFactory(new PropertyValueFactory<>("unit"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitePrice"));
        colRemove.setCellValueFactory(new PropertyValueFactory<>("removebtn"));

        DisplayTimeDate.loadDateAndTime(lbDate, lbTime);
        setIgredientID();
        updatebtn.setDisable(true);

        txtIngreName.setOnAction(event -> {
            validate(KeyCode.ENTER, txtIngreUnit, "^[A-z ]{3,20}$", txtIngreName, lbname);
        });

        txtIngreUnit.setOnAction(event -> {
            validate(KeyCode.ENTER, txtQtyOnHand, "^[1-9][0-9]*[a-z]{0,3}$", txtIngreUnit, lbunit);
        });

        txtQtyOnHand.setOnAction(event -> {
            validate(KeyCode.ENTER, txtQtyOnHand, "^[1-9][0-9]*$", txtQtyOnHand, lbcurrentqty);
        });

        txtUnitPrice.setOnAction(event -> {
            validate(KeyCode.ENTER, txtUnitPrice, "^[1-9][0-9]*([.][0-9]{2})?$", txtUnitPrice, lbprice);
        });

        txtNewQtyOnHand11.setDisable(true);

        try {
            loadIngredientDetail(getAllIngredient());
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        //Search bar
        txtsearch.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                search(newValue);
            }
        });

        //Table select
        tblIngredient.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            txtQtyOnHand.setDisable(true);
            addBtn.setDisable(true);
            txtNewQtyOnHand11.setDisable(false);
            updatebtn.setDisable(false);

            txtNewQtyOnHand11.setOnAction(event -> {
                validate(KeyCode.ENTER, txtUnitPrice, "^[1-9][0-9]*$", txtNewQtyOnHand11, lbboughtqty);
            });

            try {
                setIngreData(obList.get((Integer) newValue).getIngreID());
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

    //Set the ingredient ID
    private void setIgredientID() {
        try {
            lbIngreID.setText(creatIngreID());
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    //Add ingredient
    public void addIngredient(MouseEvent mouseEvent) {
        if (!txtIngreName.getText().trim().isEmpty() && !txtIngreUnit.getText().trim().isEmpty() && !txtQtyOnHand.getText().trim().isEmpty() && !txtUnitPrice.getText().trim().isEmpty()) {
            lbname.setStyle("-fx-text-fill: #ff7197");
            txtIngreName.setStyle("-fx-border-color:  #ff7197");
            Ingredient ingredient = new Ingredient(
                    lbIngreID.getText(),
                    txtIngreName.getText(),
                    txtIngreUnit.getText(),
                    Double.parseDouble(txtQtyOnHand.getText()),
                    Double.parseDouble(txtUnitPrice.getText())
            );

            saveIngre(ingredient);

        } else if (txtIngreName.getText().trim().isEmpty()) {
            lbname.setStyle("-fx-text-fill: red");
            txtIngreName.setStyle("-fx-border-color: red");
        } else if (txtIngreUnit.getText().trim().isEmpty()) {
            lbunit.setStyle("-fx-text-fill: red");
            txtIngreUnit.setStyle("-fx-border-color: red");
        } else if (txtQtyOnHand.getText().trim().isEmpty()) {
            lbcurrentqty.setStyle("-fx-text-fill: red");
            txtQtyOnHand.setStyle("-fx-border-color: red");
        } else {
            lbprice.setStyle("-fx-text-fill: red");
            txtUnitPrice.setStyle("-fx-border-color: red");
        }
    }

    //Load All ingredient details in to table
    public void loadIngredientDetail(ArrayList<Ingredient> ingredients) {

        for (Ingredient ingredient : ingredients) {
            String scale;
            double unit = 1;
            if (ingredient.getIngreUnit().contains("g")) {
                scale = "g";
                unit = Double.parseDouble(ingredient.getIngreUnit().split("g")[0]);
            } else if (ingredient.getIngreUnit().contains("ml")) {
                scale = "ml";
                unit = Double.parseDouble(ingredient.getIngreUnit().split("m")[0]);
            } else {
                scale = "";
                unit = Double.parseDouble(ingredient.getIngreUnit());
            }


            Button btn = new Button("Remove");
            IngredientTM tm = new IngredientTM(
                    ingredient.getIngreID(),
                    ingredient.getIngreName(),
                    ingredient.getIngreUnit(),
                    ingredient.getQtyOnHand() + scale,
                    ingredient.getUnitePrice(),
                    btn
            );
            obList.add(tm);
            removeIngre(btn, tm);
        }
        tblIngredient.setItems(obList);
    }

    //Update or restore Ingredients
    public void updateBtn(MouseEvent mouseEvent) {
        double qtyOnHand = Double.parseDouble(txtQtyOnHand.getText());
        if (!txtNewQtyOnHand11.getText().trim().isEmpty()) {
            qtyOnHand += Double.parseDouble(txtNewQtyOnHand11.getText());
        }

        String scale;
        if (txtIngreUnit.getText().contains("g")) {
            scale = "g";
        } else if (txtIngreUnit.getText().contains("ml")) {
            scale = "ml";
        } else {
            scale = "";
        }

        Ingredient ingredient = new Ingredient(
                lbIngreID.getText(),
                txtIngreName.getText(),
                txtIngreUnit.getText(),
                qtyOnHand,
                Double.parseDouble(txtUnitPrice.getText())
        );

        Button btn = new Button("Remove");
        IngredientTM tm = new IngredientTM(
                lbIngreID.getText(),
                txtIngreName.getText(),
                txtIngreUnit.getText(),
                qtyOnHand + scale,
                Double.parseDouble(txtUnitPrice.getText()),
                btn
        );
        removeIngre(btn, tm);

        int rownumber = isExists(tm, obList);
        if (rownumber == -1) {
            new Alert(Alert.AlertType.WARNING, "No record found..").show();
        } else {
            try {
                updateQTY(lbIngreID.getText(), Double.parseDouble(txtNewQtyOnHand11.getText()));
            } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
            }
        }
        obList.clear();

        try {
            loadIngredientDetail(getAllIngredient());
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        clearField();
        setIgredientID();
        txtQtyOnHand.setDisable(false);
        addBtn.setDisable(false);

    }

    //set Ingredient Data
    public void setIngreData(String id) throws SQLException, ClassNotFoundException {
        ArrayList<Ingredient> ingredients = getAllIngredient();

        for (Ingredient ingredient : ingredients) {
            if (id.equals(ingredient.getIngreID())) {
                lbIngreID.setText(ingredient.getIngreID());
                txtIngreName.setText(ingredient.getIngreName());
                txtIngreUnit.setText(ingredient.getIngreUnit());
                txtQtyOnHand.setText(String.valueOf(ingredient.getQtyOnHand()));
                txtUnitPrice.setText(String.valueOf(ingredient.getUnitePrice()));
                break;
            }
        }

    }

    //Check whether their is equal ingredient in th obList
    public int isExists(IngredientTM tm, ObservableList<IngredientTM> obList) {
        for (int i = 0; i < obList.size(); i++) {
            if (tm.getIngreID().equals(obList.get(i).getIngreID())) {
                return i;
            }
        }
        return -1;
    }

    //Save Ingredient In the Database
    public void saveIngre(Ingredient ingredient) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK) {
            try {
                if (saveIngredient(ingredient)) {
                    Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                    alert2.setTitle("Message");
                    alert2.setContentText("Saved..");
                    alert2.show();

                    tblIngredient.getItems().clear();

                    try {
                        loadIngredientDetail(getAllIngredient());
                    } catch (SQLException | ClassNotFoundException throwables) {
                        throwables.printStackTrace();
                    }

                    setIgredientID();

                } else {
                    new Alert(Alert.AlertType.WARNING, "Try Again..").show();
                }
            } catch (Exception e) {
                new Alert(Alert.AlertType.WARNING, "Duplicate Entry..").show();
            }
        }
    }

    //Remove a Ingredient from the table
    public void removeIngre(Button btn, IngredientTM tm) {
        btn.setOnAction((e) -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Warning");
            alert.setContentText("Are you sure ?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                try {
                    deleteIngredient(tm.getIngreID());
                } catch (SQLException | ClassNotFoundException throwables) {
                    throwables.printStackTrace();
                }
                obList.remove(tm);
                tblIngredient.refresh();
                setIgredientID();
            }
        });
    }

    //Search and Display
    public void search(String name) {
        try {
            ArrayList<Ingredient> ingreList = getSearchIngredient(name);
            tblIngredient.getItems().clear();
            loadIngredientDetail(ingreList);
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();

        }
    }

    //Clear Text fields
    public void clearField() {
        txtIngreName.clear();
        txtIngreUnit.clear();
        txtQtyOnHand.clear();
        txtNewQtyOnHand11.clear();
        txtUnitPrice.clear();
    }

}
