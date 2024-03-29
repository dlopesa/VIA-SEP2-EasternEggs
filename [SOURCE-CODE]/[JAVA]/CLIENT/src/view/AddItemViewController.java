package view;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import viewmodel.AddItemViewModel;

public class AddItemViewController extends ViewController
{
  @FXML private TextField nameField;
  @FXML private ChoiceBox<String> typeChoiceBox;
  @FXML private TextField priceField;
  @FXML private TextArea descriptionArea;
  @FXML private Label errorLabel;
  private AddItemViewModel addItemViewModel;

  @Override protected void init()
  {
    addItemViewModel = getViewModelFactory().getAddItemViewModel();
    nameField.textProperty().bindBidirectional(addItemViewModel.nameProperty());
    priceField.textProperty()
        .bindBidirectional(addItemViewModel.priceProperty());
    descriptionArea.textProperty()
        .bindBidirectional(addItemViewModel.descriptionProperty());
    errorLabel.textProperty()
        .bindBidirectional(addItemViewModel.errorProperty());

    //Putting items inside the choice box
    typeChoiceBox.valueProperty()
        .bindBidirectional(addItemViewModel.chosenProperty());
    //Binding the chosen item with the view model
  }

  public void reset()
  {
    addItemViewModel.reset();
    typeChoiceBox.getItems().clear();
    typeChoiceBox.getItems().addAll(addItemViewModel.getTypes());
  }

  @FXML private void submitButton()
  {
    addItemViewModel.submit();
    getViewModelFactory().getDatabaseViewModel().reset();
  }

  @FXML private void backButton()
  {
    getViewHandler().openView("DatabaseView.fxml");
    addItemViewModel.reset();
  }
}
