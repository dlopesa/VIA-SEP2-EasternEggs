package viewmodel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Model;
import property.ItemProperty;
import java.util.ArrayList;

public class CustomerViewModel
{
  private Model model;
  private StringProperty error;
  private ArrayList<ItemProperty> items;//TODO change to ObservableList
  private CustomerHandler handler;

  public CustomerViewModel(Model model, CustomerHandler handler)
  {
    this.model = model;
    this.handler=handler;
    error = new SimpleStringProperty("");
    items = new ArrayList<>();
    reset();
  }

  public void reset()
  {
    items.clear();
    try
    {
      for (int i = 0; i < model.getAllExistingItems().getAllItems().size(); i++)
      {
        items.add(new ItemProperty(model.getAllExistingItems().getAllItems()
            .get(i)));
      }
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
  }

  public void addToOrder(ItemProperty item)
  {
    model.addItemToOrder(item.getItem());
  }

  public ObservableList<ItemProperty> getItemsByType(String type)
  {
    ObservableList<ItemProperty> itemsTypeList = FXCollections.observableArrayList();
    for(int i=0; i<items.size(); i++)
    {
      if(type.equals(items.get(i).typeProperty().get()))
      {
        itemsTypeList.add(items.get(i));
      }
    }
    return itemsTypeList;
  }

  public StringProperty getErrorProperty()
  {
    return error;
  }


  public void quit()
  {
    model.quitAndCancelOrder();
  }

  public void setItem(ItemProperty item)
  {
    handler.setItem(item);
  }

}