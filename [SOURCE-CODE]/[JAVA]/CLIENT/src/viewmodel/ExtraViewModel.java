package viewmodel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Model;
import property.ExtraProperty;
import property.ItemProperty;
import utility.Item;

import java.util.ArrayList;

public class ExtraViewModel
{

  private Model model;
  private CustomerHandler handler;
  private ObservableList<ExtraProperty> availableExtras;
  private ObservableList<ExtraProperty> addedExtras;
  private ItemProperty currentItem;
  private StringProperty name;
  private StringProperty error;

  public ExtraViewModel(Model model, CustomerHandler handler)
  {
    this.model = model;
    this.handler = handler;
    name=new SimpleStringProperty();
    error=new SimpleStringProperty();
    availableExtras = FXCollections.observableArrayList();
    addedExtras = FXCollections.observableArrayList();
  }

  public void reset()
  {
    error.set("");
    currentItem = handler.getItem();
    name=currentItem.nameProperty();
    setList(availableExtras, currentItem.typeProperty().get());
  }


  public StringProperty getNameProperty()
  {
    return name;
  }

  public StringProperty getErrorProperty()
  {
    return error;
  }

  public void setList(ObservableList<ExtraProperty> extraList, String type)
  {
    extraList.clear();
    for (int i = 0; i < model.getAllExtrasByType(type).size(); i++)
    {
      extraList.add(new ExtraProperty(model.getAllExtrasByType(type).get(i)));
    }
  }

  public ObservableList<ExtraProperty> getAvailableExtras()
  {
    return availableExtras;
  }

  public ObservableList<ExtraProperty> getAddedExtras()
  {
    return addedExtras;
  }

  public void addExtraToItem(ExtraProperty extra)
  {
    if(!addedExtras.contains(extra))
    {
      addedExtras.add(extra);
      if(!error.get().equals(""))
      {
        error.set("");
      }
    }
    else
    {
      error.set("Adding the same extra more than once is not allowed.");
    }
  }

  public void removeExtraFromItem(ExtraProperty extra)
  {
    addedExtras.remove(extra);
    if(!error.get().equals(""))
    {
      error.set("");
    }
  }

  public void addItemToOrder()
  {
    if (addedExtras.size() > 0)
    {
      for (int i = 0; i < addedExtras.size(); i++)
      {
        model.addExtraToItem(addedExtras.get(i).getExtra(),
            currentItem.getItem());
      }
    }
    model.addItemToOrder(currentItem.getItem());
    addedExtras.clear();
    availableExtras.clear();
  }
}
