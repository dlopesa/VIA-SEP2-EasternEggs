package view;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import property.ItemProperty;
import viewmodel.CustomerViewModel;

import java.util.Optional;
import java.rmi.RemoteException;
import java.sql.SQLException;

public class CustomerViewController extends ViewController
{
  @FXML private TableView<ItemProperty> itemTableCoffee;
  @FXML private TableView<ItemProperty> itemTableTea;
  @FXML private TableView<ItemProperty> itemTableSnack;
  @FXML private TableView<ItemProperty> itemTableSmoothie;
  @FXML private Label errorLabel;
  @FXML private TabPane tabPane;
  private CustomerViewModel customerViewModel;

  @Override protected void init()
  {
    this.customerViewModel = getViewModelFactory().getCustomerViewModel();
    tabPane.setTabMinWidth(130);
    tabPane.setTabMinHeight(22);
    //Alignment of tabs
    errorLabel.textProperty().bind(customerViewModel.getErrorProperty());
  }

  public void reset()
  {
    customerViewModel.reset();
    setTable(itemTableCoffee, "coffee");
    setTable(itemTableSnack, "snack");
    setTable(itemTableTea, "tea");
    setTable(itemTableSmoothie, "smoothie");
  }

  private void setTable(TableView table, String type)
  {
    TableColumn idColTemp = (TableColumn) table.getColumns().get(0);
    TableColumn nameColTemp = (TableColumn) table.getColumns().get(1);
    TableColumn typeColTemp = (TableColumn) table.getColumns().get(2);
    TableColumn priceColTemp = (TableColumn) table.getColumns().get(3);
    idColTemp.setCellValueFactory(
        new PropertyValueFactory<ItemProperty, IntegerProperty>("id"));
    nameColTemp.setCellValueFactory(
        new PropertyValueFactory<ItemProperty, StringProperty>("name"));
    typeColTemp.setCellValueFactory(
        new PropertyValueFactory<ItemProperty, StringProperty>("type"));
    priceColTemp.setCellValueFactory(
        new PropertyValueFactory<ItemProperty, DoubleProperty>("price"));

    table.setItems(customerViewModel.getItemsByType(type));
  }

  private ItemProperty getItemProperty()
  {
    int indexOfTab = tabPane.getSelectionModel().getSelectedIndex();
    ItemProperty item = null;
    switch (indexOfTab)
    {
      case 0:
        item = itemTableCoffee.getSelectionModel().getSelectedItem();
        break;
      case 1:
        item = itemTableTea.getSelectionModel().getSelectedItem();
        break;
      case 2:
        item = itemTableSnack.getSelectionModel().getSelectedItem();
        break;
      case 3:
        item = itemTableSmoothie.getSelectionModel().getSelectedItem();
        break;
    }
    return item;
  }

  private boolean confirmation(String name)
  {
    ButtonType accept = new ButtonType("Yes");
    ButtonType decline = new ButtonType("No, add without extras");
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "", accept, decline);
    alert.setTitle("Addition of extras");
    alert.setHeaderText(
        "Would you like to add any extras to your " + name + " ?");
    Optional<ButtonType> result = alert.showAndWait();

    return (result.isPresent()) && (result.get() == accept);
  }

  @FXML private void addToOrderButton()
  {

    ItemProperty item = getItemProperty();
    if (confirmation(item.getItem().getName()))
    {
      customerViewModel.setItem(item);
      getViewHandler().openView("ExtraView.fxml");
    }
    else
    {
      Alert alert = new Alert(Alert.AlertType.INFORMATION);
      alert.setTitle("Adding item...");
      alert.setHeaderText(
          item.getItem().getName() + " is added to your order.");
      alert.show();
      customerViewModel.addToOrder(item);
    }
  }

  @FXML private void descriptionButton() throws SQLException, RemoteException
  {
    ItemProperty item = getItemProperty();
    customerViewModel.setItem(item);
    getViewHandler().openView("DescriptionView.fxml");
  }

  @FXML private void checkoutButton() throws SQLException, RemoteException
  {
    getViewHandler().openView("CheckoutView.fxml");
  }

  @FXML private void quitButton() throws SQLException, RemoteException
  {
    customerViewModel.quit();
    getViewHandler().openView("StartView.fxml");
  }
}