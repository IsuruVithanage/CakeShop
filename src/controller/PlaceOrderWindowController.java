package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import controller.contollerUtil.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import model.*;
import util.DisplayTimeDate;
import view.tm.OrderDetailTM;
import view.tm.PlaceOrderTM;
import view.tm.RecipeTM;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

public class PlaceOrderWindowController extends OrderContollerUtil {
    public AnchorPane contextPlaceOrder;
    public Label lbTime;
    public Label lbDate;
    public Label lbCustID;
    public TextField txtCustName;
    public TextField txtCustContact;
    public TextField txtCustAddress;
    public JFXComboBox<String> cmbCakeName;
    public JFXComboBox<String> cmbWeight;
    public TableView<RecipeTM> tblIngre;
    public TableColumn<RecipeTM, String> colIngreID;
    public TableColumn<RecipeTM, String> colUnit;
    public TableColumn<RecipeTM, String> colQTY;
    public TableColumn<RecipeTM, String> colPrice;
    public TableColumn<RecipeTM, String> colRemove;
    public JFXComboBox<String> cmbIngre;
    public JFXButton add;
    public TextField txtInit;
    public TextField txtQty;
    public TableView<PlaceOrderTM> tblOrder;
    public TableColumn<PlaceOrderTM, String> colCakeID;
    public TableColumn<PlaceOrderTM, String> colCakePrice;
    public TableColumn<PlaceOrderTM, String> colCakeRom;
    public Label lbTotal;
    public JFXComboBox<String> cmbWorker;
    public JFXDatePicker deliveryDate;
    public Label lbOrderID;
    public TextArea txtDesc;
    public TableColumn<PlaceOrderTM, String> colWeight;
    public JFXButton placeorderbtn;
    public JFXButton addtocartbtn;
    public JFXButton updatebtn;
    public JFXButton updateCartbtn;
    public Label lbName;
    public Label lbContact;
    public Label lnQTY;
    public Label lbDeliverDate;
    public Label lbDesc;
    double extraTotal = 0;
    ObservableList<RecipeTM> obList = FXCollections.observableArrayList();
    ObservableList<PlaceOrderTM> orderList = FXCollections.observableArrayList();
    ArrayList<Product> productObservableList = new ArrayList<>();
    ArrayList<Cake> cakeArrayList;
    ArrayList<Ingredient> ingredients;
    ArrayList<CakeDetail> cakes = new ArrayList<>();
    int index;//table select index
    int orderIndex = 0;//To select the order product

    {
        try {
            cakeArrayList = new CakeController().getAllCake();
            ingredients = new IngredientControllerUtil().getAllIngredient();
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    public void initialize() {
        colIngreID.setCellValueFactory(new PropertyValueFactory<>("ingreID"));
        colUnit.setCellValueFactory(new PropertyValueFactory<>("unit"));
        colQTY.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colRemove.setCellValueFactory(new PropertyValueFactory<>("btn"));
        colCakeID.setCellValueFactory(new PropertyValueFactory<>("cakeID"));
        colWeight.setCellValueFactory(new PropertyValueFactory<>("weight"));
        colCakePrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colCakeRom.setCellValueFactory(new PropertyValueFactory<>("remove"));


        cmbWeight.setDisable(true);
        cmbIngre.setDisable(true);
        updateCartbtn.setDisable(true);
        updatebtn.setDisable(true);
        DisplayTimeDate.loadDateAndTime(lbDate, lbTime);


        setCustID();
        setOrderID();

        loadWeight();

        txtCustName.setOnAction(event -> {
            validate(KeyCode.ENTER, txtCustContact, "^[A-z ]{3,20}$", txtCustName, lbName);
        });

        txtCustContact.setOnAction(event -> {
            validate(KeyCode.ENTER, txtCustAddress, "^[0-9]{10,}$", txtCustContact, lbContact);
        });

        txtQty.setOnAction(event -> {
            validate(KeyCode.ENTER, txtQty, "^[1-9][0-9]*$", txtQty, lnQTY);
        });

        loadCake();
        loadIngre();

        try {
            loadWorker();
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        //Select Weight
        cmbWeight.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            double weight = Double.parseDouble(newValue.split("k")[0]);
            cmbIngre.setDisable(false);
            try {
                loadRecipe(weight);
            } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
            }

        });

        //Select Ingredient
        cmbIngre.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            setIngreData(newValue);

        });

        //Select Cake
        cmbCakeName.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            cmbWeight.setDisable(false);
        });

        //select product
        tblOrder.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            try {
                index = (Integer) newValue;
                loadProduct(index);
            } catch (Exception e) {

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

    //set Customer ID
    private void setCustID() {
        try {
            lbCustID.setText(new CustomerController().createCust());
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    //Save Customer
    public void addCustomer() {
        Customer customer = new Customer(
                lbCustID.getText(),
                txtCustName.getText(),
                txtCustContact.getText(),
                txtCustAddress.getText()
        );

        try {
            if (new CustomerController().saveCustomer(customer)) {
                /*new Alert(Alert.AlertType.CONFIRMATION, "Saved..").show(); */
            } else {
                new Alert(Alert.AlertType.WARNING, "Try Again..").show();
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    //Add ingredients to table
    public void addIngre(MouseEvent mouseEvent) {
        if (txtQty.getText().trim().isEmpty()) {
            lnQTY.setStyle("-fx-text-fill: red");
            txtQty.setStyle("-fx-border-color: red");
        } else {
            lnQTY.setStyle("-fx-text-fill: #ff7197");
            txtQty.setStyle("-fx-border-color: #ff7197");
            for (Ingredient ingredient : ingredients) {
                if (cmbIngre.getSelectionModel().getSelectedItem().equals(ingredient.getIngreName())) {

                    String scale;
                    Button btn = new Button("Remove");
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

                    double price = Math.round(((ingredient.getUnitePrice() / unit) * Double.parseDouble(txtQty.getText())) * 100.0) / 100.0;
                    RecipeTM tm = new RecipeTM(
                            ingredient.getIngreID(),
                            ingredient.getIngreName(),
                            ingredient.getIngreUnit(),
                            Double.parseDouble(txtQty.getText()) + scale,
                            price,
                            btn
                    );
                    extraTotal += price;
                    removeIngre(btn, tm, obList);

                    int rowNumber = isIngreExsists(tm, obList);

                    if (rowNumber==-1){
                        obList.add(tm);
                    }else {
                        RecipeTM temp=obList.get(rowNumber);
                        double qty = Double.parseDouble(temp.getQty().split(scale)[0]);
                        RecipeTM newtm=new RecipeTM(
                                temp.getIngreID(),
                                temp.getIngreName(),
                                temp.getUnit(),
                                (qty+Double.parseDouble(txtQty.getText())) + scale,
                                temp.getPrice()+price,
                                temp.getBtn()

                        );

                        obList.remove(rowNumber);
                        obList.add(newtm);
                    }
                    break;
                }

            }
            tblIngre.setItems(obList);
            tblIngre.refresh();
            txtQty.clear();
        }

    }

    private int isIngreExsists(RecipeTM tm,ObservableList<RecipeTM> obList){
        for (int i = 0; i < obList.size(); i++) {
            if (tm.getIngreID().equals(obList.get(i).getIngreID())) {
                return i;
            }
        }
        return -1;
    }

    //Load All Cakes in to comboBox
    public void loadCake() {
        ArrayList<String> cakeName = new ArrayList<>();

        for (Cake cake : cakeArrayList) {
            cakeName.add(cake.getCakeName());
        }

        cmbCakeName.getItems().addAll(cakeName);
    }

    //Load All Ingredients in to comboBox
    public void loadIngre() {
        ArrayList<String> ingreName = new ArrayList<>();

        for (Ingredient ingredient : ingredients) {
            ingreName.add(ingredient.getIngreName());
        }

        cmbIngre.getItems().addAll(ingreName);

    }

    //Load All Workers in to comboBox
    public void loadWorker() throws SQLException, ClassNotFoundException {
        ArrayList<Worker> workerArrayList = new WorkerControllerUtil().getAllAvailableWorkers();
        ArrayList<String> workerID = new ArrayList<>();

        for (Worker worker : workerArrayList) {
            workerID.add(worker.getWorkerID());
        }

        cmbWorker.getItems().addAll(workerID);

    }

    //Set Ingredients data in to the table
    public void setIngreData(String name) {
        for (Ingredient ingredient : ingredients) {
            if (name.equals(ingredient.getIngreName())) {
                txtInit.setText(ingredient.getIngreUnit());
            }
        }
    }

    //Load Weight
    public void loadWeight() {
        List<String> weightList = Arrays.asList("1kg", "1.5kg", "2kg", "2.5kg", "3kg");
        cmbWeight.getItems().addAll(weightList);
    }

    //Load Recipe
    public void loadRecipe(double weight) throws SQLException, ClassNotFoundException {
        obList.clear();
        String cakeid = getCakeID(cmbCakeName.getSelectionModel().getSelectedItem());

        ArrayList<Recipe> recipes = getRecipe(cakeid);

        for (Recipe recipe : recipes) {
            for (Ingredient ingredient : ingredients) {
                if (recipe.getIngreID().equals(ingredient.getIngreID())) {
                    Button btn = new Button("Remove");
                    double unit = 1;

                    String scale;
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

                    double qty = recipe.getQty() * weight;

                    double price = Math.round(((ingredient.getUnitePrice() / unit) * qty) * 100.0) / 100.0;
                    RecipeTM tm = new RecipeTM(
                            recipe.getIngreID(),
                            ingredient.getIngreName(),
                            ingredient.getIngreUnit(),
                            qty + scale,
                            price,
                            btn
                    );
                    obList.add(tm);
                    removeIngre(btn, tm, obList);
                    break;
                }

            }
        }
        tblIngre.setItems(obList);
        unavailable();
    }

    //Get cake Id using cake name
    private String getCakeID(String name) {
        String cakeid = null;

        for (Cake cake : cakeArrayList) {
            if (cake.getCakeName().equals(cmbCakeName.getSelectionModel().getSelectedItem())) {
                cakeid = cake.getCakeID();
                break;
            }
        }
        return cakeid;
    }

    //Remove a Ingredient from the Table
    public void removeIngre(Button btn, RecipeTM tm, ObservableList<RecipeTM> obList) {
        btn.setOnAction((e) -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Warning");
            alert.setContentText("Are you sure ?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                extraTotal+=(-tm.getPrice());
                obList.remove(tm);
                tblIngre.refresh();
            }
        });
    }

    //Remove a product from the Table
    public void removeProduct(Button btn, PlaceOrderTM tm, ObservableList<PlaceOrderTM> obList) {
        btn.setOnAction((e) -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Warning");
            alert.setContentText("Are you sure ?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                obList.remove(tm);

                for (CakeDetail cake : cakes) {
                    if (cake.getCakeID().equals(tm.getCakeID()) && cake.getWeight().equals(tm.getWeight())) {
                        cakes.remove(cake);
                        break;
                    }
                }

                for (Product product : productObservableList) {
                    if (product.getIndex() == orderIndex) {
                        productObservableList.remove(product);
                        break;
                    }
                }
                orderIndex -= 1;

                tblOrder.refresh();
                calculateCost(obList, lbTotal);
            }
        });
    }

    //Get Cake Price
    public Double getPrice(String name) {
        for (Cake cake : cakeArrayList) {
            if (name.equals(cake.getCakeName())) {
                return cake.getCakePrice();
            }
        }
        return 0.0;
    }

    //Add to cart
    public void addToCart(MouseEvent mouseEvent) {

        if (!txtDesc.getText().trim().isEmpty() && !cmbCakeName.getSelectionModel().isEmpty() && !cmbWeight.getSelectionModel().isEmpty()) {
            lbDesc.setStyle("-fx-text-fill: #ff7197");
            txtDesc.setStyle("-fx-border-color:  #ff7197");
            cmbCakeName.setStyle("-fx-text-fill: white");
            cmbWeight.setStyle("-fx-border-color: white");
            orderIndex++;
            addproductIngre(obList, getCakeID(cmbCakeName.getSelectionModel().getSelectedItem()));
            double weight = Double.parseDouble(cmbWeight.getSelectionModel().getSelectedItem().split("k")[0]);
            Button btn = new Button("Remove");
            double price = (getPrice(cmbCakeName.getSelectionModel().getSelectedItem()) * weight) + extraTotal;
            PlaceOrderTM order = new PlaceOrderTM(
                    getCakeID(cmbCakeName.getSelectionModel().getSelectedItem()),
                    cmbWeight.getSelectionModel().getSelectedItem(),
                    price,
                    btn
            );
            orderList.add(order);
            removeProduct(btn, order, orderList);
            lbTotal.setText(String.valueOf(Math.round((Double.parseDouble(lbTotal.getText()) + price) * 100.0) / 100.0));
            tblOrder.setItems(orderList);

            tblIngre.getItems().clear();

            int proIndex = -1;

            for (int i = 0; i < cakes.size(); i++) {
                if (cakes.get(i).getCakeID().equals(order.getCakeID()) && cakes.get(i).getWeight().equals(order.getWeight()) && txtDesc.getText().equals(cakes.get(i).getDesc())) {
                    proIndex = i;
                    break;
                }
            }

            if (proIndex != -1) {
                cakes.add(proIndex, new CakeDetail(getCakeID(cmbCakeName.getSelectionModel().getSelectedItem()), lbOrderID.getText(), cmbWeight.getSelectionModel().getSelectedItem(), txtDesc.getText(), price));
            } else {
                cakes.add(new CakeDetail(getCakeID(cmbCakeName.getSelectionModel().getSelectedItem()), lbOrderID.getText(), cmbWeight.getSelectionModel().getSelectedItem(), txtDesc.getText(), price));
            }

            extraTotal = 0;
            txtDesc.clear();
        } else if (txtDesc.getText().trim().isEmpty()) {
            lbDesc.setStyle("-fx-text-fill: red");
            txtDesc.setStyle("-fx-border-color: red");
        } else if (cmbCakeName.getSelectionModel().isEmpty()) {
            cmbCakeName.setStyle("-fx-border-color: red");
        } else if (cmbWeight.getSelectionModel().isEmpty()) {
            cmbWeight.setStyle("-fx-border-color: red");
        } else {
            txtDesc.setStyle("-fx-border-color: red");
            cmbCakeName.setStyle("-fx-border-color: red");
            cmbWeight.setStyle("-fx-border-color: red");
        }
    }

    //add product ingre
    private void addproductIngre(ObservableList<RecipeTM> obList, String cakeID) {
        ArrayList<RecipeTM> list = new ArrayList<>();

        for (RecipeTM recipeTM : obList) {
            list.add(recipeTM);
        }
        productObservableList.add(new Product(orderIndex, cakeID, list));
    }

    //Place the order
    public void placeOrder(MouseEvent mouseEvent) {
        if (deliveryDate.getValue() != null && !txtCustName.getText().trim().isEmpty() && !txtCustContact.getText().trim().isEmpty() && !txtCustAddress.getText().trim().isEmpty() && !orderList.isEmpty()) {
            addCustomer();
            txtCustName.setStyle("-fx-border-color: #ff7197");
            txtCustContact.setStyle("-fx-border-color: #ff7197");
            txtCustAddress.setStyle("-fx-border-color: #ff7197");
            deliveryDate.setStyle("-fx-border-color: white");

            Order order = new Order(
                    lbOrderID.getText(),
                    lbCustID.getText(),
                    cmbWorker.getSelectionModel().getSelectedItem(),
                    lbDate.getText(),
                    lbTime.getText(),
                    deliveryDate.getValue().toString(),
                    Double.parseDouble(lbTotal.getText()),
                    "Not Delivered",
                    cakes
            );


            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == ButtonType.OK) {
                try {
                    if (placeOrder(order, productObservableList)) {
                        Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                        alert2.setTitle("Message");
                        alert2.setContentText("Saved..");
                        alert2.show();

                        setOrderID();

                        setQTY();

                        new WorkerControllerUtil().updateAvailability(cmbWorker.getSelectionModel().getSelectedItem(), "No");

                    } else {
                        new Alert(Alert.AlertType.WARNING, "Try Again..").show();
                    }
                } catch (Exception e) {
                    new Alert(Alert.AlertType.WARNING, "Duplicate Entry..").show();
                }
            }

        } else if (txtCustName.getText().trim().isEmpty()) {
            txtCustName.setStyle("-fx-border-color: red");
        } else if (txtCustContact.getText().trim().isEmpty()) {
            txtCustContact.setStyle("-fx-border-color: red");
        } else if (txtCustAddress.getText().trim().isEmpty()) {
            txtCustAddress.setStyle("-fx-border-color: red");
        } else if (orderList.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Order list is empty..").show();
        } else {
            deliveryDate.setStyle("-fx-border-color: red");
        }

    }

    //Update the ingredient QTY
    public void setQTY() throws SQLException, ClassNotFoundException {
        ArrayList<Recipe> recipes = getRecipe(getCakeID(cmbCakeName.getSelectionModel().getSelectedItem()));
        for (Recipe recipe : recipes) {
            try {
                updateQTY(recipe.getIngreID(), recipe.getQty());
            } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    //set Order ID
    private void setOrderID() {
        try {
            lbOrderID.setText(createOrderID());
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    //Get the recipe
    private ArrayList<Recipe> getRecipe(String cakeID) {
        try {
            return new RecipeControllerUtil().getRecipe(cakeID);
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    //Unavailable item alert
    private void unavailable() {
        ArrayList<Recipe> recipes = getRecipe(getCakeID(cmbCakeName.getSelectionModel().getSelectedItem()));

        for (Recipe recipe : recipes) {
            for (Ingredient ingredient : ingredients) {
                if (recipe.getIngreID().equals(ingredient.getIngreID()) && recipe.getQty() > ingredient.getQtyOnHand()) {
                    new Alert(Alert.AlertType.WARNING, "There are some insufficient Ingredients to complete this Order").show();
                    return;
                }
            }
        }
    }

    //Get Cake Name
    public String getCakeName(String id) {
        for (Cake cake : cakeArrayList) {
            if (cake.getCakeID().equals(id)) {
                return cake.getCakeName();
            }
        }
        return null;
    }

    //Load data in to fields
    public void loadToUpdate(OrderDetailTM tm, ArrayList<CakeDetail> productList, ArrayList<IngreDetail> ingreDetails) throws SQLException, ClassNotFoundException {
        Customer customer = new CustomerController().getAllCustomer(tm.getCustID());
        lbCustID.setText(customer.getCustID());
        txtCustName.setText(customer.getCustName());
        txtCustContact.setText(customer.getCustcontact());
        txtCustAddress.setText(customer.getCustAddress());
        deliveryDate.setValue(LocalDate.parse(tm.getDeliveryDate()));

        updateCartbtn.setDisable(false);
        updatebtn.setDisable(false);
        placeorderbtn.setDisable(true);

        orderIndex = productList.size();

        LinkedHashMap<Integer, ArrayList<RecipeTM>> list = new LinkedHashMap<>();
        for (int i = 0; i < productList.size(); i++) {
            ArrayList<RecipeTM> l = new ArrayList<>();
            for (IngreDetail ingreDetail : ingreDetails) {
                if (ingreDetail.getOrderIndex() == i + 1) {
                    Button btn = new Button("Remove");
                    RecipeTM tn = new RecipeTM(
                            ingreDetail.getIngreID(),
                            null,
                            ingreDetail.getUnit(),
                            ingreDetail.getQty(),
                            ingreDetail.getPrice(),
                            btn
                    );
                    l.add(tn);
                    removeIngre(btn, tn, obList);
                }
            }
            list.put(i, l);
        }


        for (CakeDetail cakeDetail : productList) {
            Button btn = new Button("Remove");
            PlaceOrderTM order = new PlaceOrderTM(
                    cakeDetail.getCakeID(),
                    cakeDetail.getWeight(),
                    cakeDetail.getPrice(),
                    btn
            );

            orderList.add(order);
            removeProduct(btn, order, orderList);
        }

        for (int i = 0; i < list.size(); i++) {
            ArrayList<RecipeTM> r = list.get(i);
            productObservableList.add(new Product(i + 1, orderList.get(i).getCakeID(), r));
        }

        for (CakeDetail cakeDetail : productList) {
            cakes.add(new CakeDetail(cakeDetail.getCakeID(), cakeDetail.getOrderID(), cakeDetail.getWeight(), cakeDetail.getDesc(), cakeDetail.getPrice()));
        }


        calculateCost(orderList, lbTotal);
        lbOrderID.setText(tm.getOrderID());
        tblOrder.setItems(orderList);

    }

    public void loadProduct(int i) throws SQLException, ClassNotFoundException {
        tblIngre.getItems().clear();

        cmbCakeName.setValue(getCakeName(orderList.get(i).getCakeID()));
        cmbWeight.setValue(orderList.get(i).getWeight());
        txtDesc.setText(cakes.get(i).getDesc());

        for (Product product : productObservableList) {
            if (product.getIndex() == i + 1) {
                obList.clear();
                for (RecipeTM ingredient : product.getIngredients()) {
                    obList.add(ingredient);
                }
                break;
            }
        }
        tblIngre.setItems(obList);
    }


    public void updateOrder(MouseEvent mouseEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Warning");
        alert.setContentText("Are you sure ?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {

            Order order = new Order(
                    lbOrderID.getText(),
                    lbCustID.getText(),
                    cmbWorker.getSelectionModel().getSelectedItem(),
                    lbDate.getText(),
                    lbTime.getText(),
                    deliveryDate.getValue().toString(),
                    Double.parseDouble(lbTotal.getText()),
                    "Not Delivered",
                    cakes
            );

            try {
                deleteOrder(order.getOrderID());
                placeOrder(order, productObservableList);
            } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
            }
        }


    }

    //Calculate the Total
    public void calculateCost(ObservableList<PlaceOrderTM> obList, Label lbTotal) {
        double total = 0;
        for (PlaceOrderTM tm : obList
        ) {
            total += tm.getPrice();
        }
        lbTotal.setText(String.valueOf(total));
    }

    public void updateCart(MouseEvent mouseEvent) {
        orderIndex = index + 1;

        ArrayList<RecipeTM> list = new ArrayList<>();

        for (RecipeTM recipeTM : obList) {
            list.add(recipeTM);
        }
        productObservableList.set(index, new Product(orderIndex, getCakeID(cmbCakeName.getSelectionModel().getSelectedItem()), list));

        double weight = Double.parseDouble(cmbWeight.getSelectionModel().getSelectedItem().split("k")[0]);

        Button btn = new Button("Remove");
        PlaceOrderTM tm = new PlaceOrderTM(
                getCakeID(cmbCakeName.getSelectionModel().getSelectedItem()),
                cmbWeight.getSelectionModel().getSelectedItem(),
                getPrice(cmbCakeName.getSelectionModel().getSelectedItem()) * weight + extraTotal,
                btn

        );
        removeProduct(btn, tm, orderList);
        try {
            cakes.set(index, new CakeDetail(tm.getCakeID(), lbOrderID.getText(), tm.getWeight(), txtDesc.getText(), tm.getPrice()));
            System.out.println(index);
            orderList.set(index, tm);
            tblOrder.setItems(orderList);
            calculateCost(orderList, lbTotal);
            orderIndex = 0;
        } catch (Exception e) {

        }
    }
}
