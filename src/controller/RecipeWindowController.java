package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import controller.contollerUtil.CakeController;
import controller.contollerUtil.IngredientControllerUtil;
import controller.contollerUtil.RecipeControllerUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import model.Cake;
import model.Ingredient;
import model.Recipe;
import util.DisplayTimeDate;
import view.tm.RecipeTM;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

public class RecipeWindowController extends RecipeControllerUtil {
    public Label lbTime;
    public Label lbDate;
    public TextField txtUnitPrice;
    public TableView<RecipeTM> tblRecipe;
    public TableColumn<RecipeTM, String> colIngreID;
    public TableColumn<RecipeTM, String> colName;

    public TableColumn<RecipeTM, String> colPrice;
    public TableColumn<RecipeTM, String> colRemove;
    public JFXButton addBtn;
    public JFXComboBox<String> cmbIngreName;
    public JFXComboBox<String> cmbCakeID;
    public TextField txtUnit;
    public TextField txtqty;
    public JFXButton updateBtn;
    public TableColumn<RecipeTM, String> colUnit;
    public TableColumn<RecipeTM, String> colQty;
    public TextField txtID;
    public Label lbQTY;


    public void initialize() {
        colIngreID.setCellValueFactory(new PropertyValueFactory<>("ingreID"));
        colName.setCellValueFactory(new PropertyValueFactory<>("ingreName"));
        colUnit.setCellValueFactory(new PropertyValueFactory<>("unit"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colRemove.setCellValueFactory(new PropertyValueFactory<>("btn"));

        DisplayTimeDate.loadDateAndTime(lbDate, lbTime);
        try {
            loadCakeID();
            loadIngreID();
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        txtqty.setOnAction(event -> {
            validate(KeyCode.ENTER, txtqty, "^[1-9][0-9]*$", txtqty, lbQTY);
        });

        cmbIngreName.setDisable(true);
        addBtn.setDisable(true);

        //Select Ingredient
        cmbIngreName.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                setIngreData(newValue);
            } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
            }

        });

        //Select Ingredient
        cmbCakeID.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            cmbIngreName.setDisable(false);
            addBtn.setDisable(false);
            try {
                loadRecipe(getRecipe(newValue));
            } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
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

    //Add cake id in to comboBox
    public void loadCakeID() throws SQLException, ClassNotFoundException {
        ArrayList<Cake> cakeArrayList = new CakeController().getAllCake();
        ArrayList<String> ids = new ArrayList<>();

        for (Cake cake : cakeArrayList) {
            ids.add(cake.getCakeID());
        }

        cmbCakeID.getItems().addAll(ids);
    }

    //Add All ingredients Ids in to comboBox
    public void loadIngreID() throws SQLException, ClassNotFoundException {
        ArrayList<Ingredient> ingredients = new IngredientControllerUtil().getAllIngredient();
        ArrayList<String> name = new ArrayList<>();

        for (Ingredient ingredient : ingredients) {
            name.add(ingredient.getIngreName());
        }

        cmbIngreName.getItems().addAll(name);
    }

    //Set Ingredient data
    public void setIngreData(String name) throws SQLException, ClassNotFoundException {
        ArrayList<Ingredient> ingredients = new IngredientControllerUtil().getAllIngredient();
        for (Ingredient ingredient : ingredients) {
            if (ingredient.getIngreName().equals(name)) {
                txtID.setText(ingredient.getIngreID());
                txtUnit.setText(ingredient.getIngreUnit());
                txtUnitPrice.setText(String.valueOf(ingredient.getUnitePrice()));
            }
        }
    }

    //Add Item to the Recipe
    public void addRecipe(MouseEvent mouseEvent) {
        if (!txtqty.getText().trim().isEmpty() && !cmbIngreName.getSelectionModel().isEmpty() && !cmbIngreName.getSelectionModel().isEmpty()) {
            lbQTY.setStyle("-fx-text-fill: #ff7197");
            txtqty.setStyle("-fx-border-color:  #ff7197");

            Recipe recipe = new Recipe(
                    cmbCakeID.getSelectionModel().getSelectedItem(),
                    txtID.getText(),
                    cmbIngreName.getSelectionModel().getSelectedItem(),
                    txtUnit.getText(),
                    Double.parseDouble(txtUnitPrice.getText()),
                    Double.parseDouble(txtqty.getText())
            );

            saveIngreInRecipe(recipe);

        } else {
            lbQTY.setStyle("-fx-text-fill: red");
            txtqty.setStyle("-fx-border-color: red");
        }


    }

    //Save the item in recipe table in database
    private void saveIngreInRecipe(Recipe recipe) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK) {
            try {
                if (saveRecipe(recipe)) {
                    Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                    alert2.setTitle("Message");
                    alert2.setContentText("Saved..");
                    alert2.show();

                    tblRecipe.getItems().clear();

                    try {
                        loadRecipe(getRecipe(cmbCakeID.getSelectionModel().getSelectedItem()));
                    } catch (SQLException | ClassNotFoundException throwables) {
                        throwables.printStackTrace();
                    }

                } else {
                    new Alert(Alert.AlertType.WARNING, "Try Again..").show();
                }
            } catch (Exception e) {
                new Alert(Alert.AlertType.WARNING, "Duplicate Entry..").show();
            }
        }
    }

    //Load recipe in the table
    private void loadRecipe(ArrayList<Recipe> recipe) throws SQLException, ClassNotFoundException {
        ArrayList<Ingredient> ingredients = new IngredientControllerUtil().getAllIngredient();
        ObservableList<RecipeTM> obList = FXCollections.observableArrayList();

        for (Recipe r : recipe) {
            for (Ingredient ingredient : ingredients) {
                if (r.getIngreID().equals(ingredient.getIngreID())) {

                    String scale;
                    Button btn = new Button("Remove");
                    double unit = 1;

                    if (ingredient.getIngreUnit().matches("[0-9]*(g)$")) {
                        scale = "g";
                        unit = Double.parseDouble(ingredient.getIngreUnit().split("g")[0]);
                    } else if (ingredient.getIngreUnit().matches("[0-9]*(ml)$")) {
                        scale = "ml";
                        unit = Double.parseDouble(ingredient.getIngreUnit().split("m")[0]);
                    } else {
                        scale = "";
                        unit = Double.parseDouble(ingredient.getIngreUnit());
                    }

                    double price = (ingredient.getUnitePrice() / unit) * r.getQty();
                    RecipeTM tm = new RecipeTM(
                            r.getIngreID(),
                            ingredient.getIngreName(),
                            ingredient.getIngreUnit(),
                            r.getQty() + scale,
                            price,
                            btn
                    );
                    obList.add(tm);
                    removeIngre(btn, tm, obList);
                    break;
                }

            }
        }
        tblRecipe.setItems(obList);
    }

    //Remove a Ingredient from the Recipe
    public void removeIngre(Button btn, RecipeTM tm, ObservableList<RecipeTM> obList) {
        btn.setOnAction((e) -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Warning");
            alert.setContentText("Are you sure ?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                try {
                    deleteResIngre(tm.getIngreID(), cmbCakeID.getSelectionModel().getSelectedItem());
                } catch (SQLException | ClassNotFoundException throwables) {
                    throwables.printStackTrace();
                }
                obList.remove(tm);
                tblRecipe.refresh();
            }
        });
    }

}
