package viewmodel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Model;
import property.ItemProperty;
import utility.Item;

import java.rmi.RemoteException;
import java.util.ArrayList;

public class DatabaseViewModel
{
  private ObservableList<ItemProperty> allItems;
  private Model model;

  public DatabaseViewModel(Model model)
  {
    this.model = model;
    allItems = FXCollections.observableArrayList();
  }

  public void reset()
  {
    allItems.clear();
    try
    {

      for (Item item : model.getAllExistingItems().getAllItems()) {
        allItems.add(new ItemProperty(item));
      }
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
  }

  public ObservableList<ItemProperty> getAllItems()
  {
   return allItems;
  }

  public void removeItem(ItemProperty item)
  {
    try
    {
      model.removeItemFromProductList(item.getItem());
    }
    catch (IllegalAccessException e)
    {
      e.printStackTrace();
    }
  }
}
