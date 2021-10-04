package controller;

import com.jfoenix.controls.JFXButton;
import controller.contollerUtil.IngredientControllerUtil;
import controller.contollerUtil.UnavailableUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import model.Ingredient;
import model.UnavailableIngredient;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import util.DisplayTimeDate;
import view.tm.IngredientTM;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class UnAvailableIngredientsController extends UnavailableUtil {

    public Label lbTime;
    public Label lbDate;
    public TableView<IngredientTM> tblUnavailable;
    public TableColumn<IngredientTM, String> colIngreID;
    public TableColumn<IngredientTM, String> colName;
    public TableColumn<IngredientTM, String> colUnit;
    public TableColumn<IngredientTM, String> colqty;
    public TableColumn<IngredientTM, String> colPrice;
    public JFXButton btnGetList;
    ObservableList<IngredientTM> obList = FXCollections.observableArrayList();


    public void initialize() {
        DisplayTimeDate.loadDateAndTime(lbDate, lbTime);
        try {
            loadIngre();
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        colIngreID.setCellValueFactory(new PropertyValueFactory<>("ingreID"));
        colName.setCellValueFactory(new PropertyValueFactory<>("ingreName"));
        colUnit.setCellValueFactory(new PropertyValueFactory<>("unit"));
        colqty.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("unitePrice"));
    }

    //Load Ingredients
    public void loadIngre() throws SQLException, ClassNotFoundException {
        ArrayList<Ingredient> ingredients = new IngredientControllerUtil().getAllIngredient();

        for (Ingredient ingredient : ingredients) {

            if (ingredient.getQtyOnHand() <= 0) {
                double unit = 0;

                String scale;
                if (ingredient.getIngreUnit().matches("[0-9]*(g)$")) {
                    scale = "g";
                    unit = Double.parseDouble(ingredient.getIngreUnit().split("g")[0]);
                } else if (ingredient.getIngreUnit().matches("[0-9]*(ml)$")) {
                    scale = "ml";
                    unit = Double.parseDouble(ingredient.getIngreUnit().split("ml")[0]);
                } else {
                    scale = "";
                    unit = Double.parseDouble(ingredient.getIngreUnit().split("g")[0]);
                }

                double qty = Math.abs(ingredient.getQtyOnHand()) + unit;
                double price = (ingredient.getUnitePrice() / unit) * qty;

                IngredientTM tm = new IngredientTM(
                        ingredient.getIngreID(),
                        ingredient.getIngreName(),
                        ingredient.getIngreUnit(),
                        qty + scale,
                        Math.round(price * 100.0) / 100.0,
                        null

                );

                obList.add(tm);
            }
        }

        tblUnavailable.setItems(obList);


    }

    public void getUnavalablelist(MouseEvent mouseEvent) {
        try {
            JasperDesign design = JRXmlLoader.load(this.getClass().getResourceAsStream("/view/reports/UnavailableList.jrxml"));
            JasperReport jasperReport = JasperCompileManager.compileReport(design);
            ArrayList<UnavailableIngredient> list = new ArrayList<>();

            HashMap map = new HashMap();
            double total = 0;

            for (IngredientTM ingredientTM : obList) {
                list.add(new UnavailableIngredient(
                        ingredientTM.getIngreID(),
                        ingredientTM.getIngreName(),
                        ingredientTM.getUnit(),
                        ingredientTM.getQtyOnHand(),
                        ingredientTM.getUnitePrice()
                ));

                total += ingredientTM.getUnitePrice();
            }

            map.put("total", "Rs " + total);

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, map, new JRBeanArrayDataSource(list.toArray()));
            JasperViewer.viewReport(jasperPrint, false);
        } catch (JRException e) {
            e.printStackTrace();
        }


    }
}
