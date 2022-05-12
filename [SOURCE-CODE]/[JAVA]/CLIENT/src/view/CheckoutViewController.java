package view;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import property.ItemProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import utility.Item;
import viewmodel.CheckoutViewModel;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class CheckoutViewController extends ViewController implements
    PropertyChangeListener
{
  @FXML private TableView itemTable;
  @FXML private Label totalPriceLabel;
  @FXML private Label errorLabel;
  private CheckoutViewModel checkoutViewModel;

  @Override protected void init()
  {
    this.checkoutViewModel=getViewModelFactory().getCheckoutViewModel();
    checkoutViewModel.addListener(this);
    totalPriceLabel.textProperty().bind(checkoutViewModel.getTotalPrice());
    errorLabel.textProperty().bind(checkoutViewModel.getError());
    orderTable();
  }

  private void orderTable()
  {
    TableColumn idColTemp = (TableColumn) itemTable.getColumns().get(0);
    TableColumn nameColTemp = (TableColumn) itemTable.getColumns().get(1);
    TableColumn typeColTemp = (TableColumn) itemTable.getColumns().get(2);
    TableColumn priceColTemp = (TableColumn) itemTable.getColumns().get(3);
    idColTemp.setCellValueFactory(
        new PropertyValueFactory<ItemProperty, IntegerProperty>("id"));
    //Change the things to properties
    nameColTemp.setCellValueFactory(
        new PropertyValueFactory<ItemProperty, StringProperty>("name"));
    typeColTemp.setCellValueFactory(
        new PropertyValueFactory<ItemProperty, StringProperty>("type"));
    priceColTemp.setCellValueFactory(
        new PropertyValueFactory<ItemProperty, DoubleProperty>("price"));

    itemTable.setItems(checkoutViewModel.getItemsInOrder());
  }

  @FXML private void removeFromOrderButton()
  {
    //Creating itemProperty,
    ItemProperty item = (ItemProperty) itemTable.getSelectionModel().getSelectedItem();
    checkoutViewModel.removeFromOrder(item);
    orderTable();
  }

  @FXML private void payButton()
  {
    //getViewHandler().openView(pay);
  }

  @FXML private void backButton()
  {
    getViewHandler().openView("CustomerView.fxml");
  }

  @FXML private void quitButton()
  {
    getViewHandler().openView("StartView.fxml");
  }

  @Override public void propertyChange(PropertyChangeEvent evt)
  {

  }
}
