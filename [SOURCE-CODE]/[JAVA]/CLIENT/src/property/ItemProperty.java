package property;

import javafx.beans.property.*;
import utility.Extra;
import utility.Item;

import java.util.ArrayList;

/**
 * A class that represents an item property
 */
public class ItemProperty
{
  private IntegerProperty id;
  private StringProperty name;
  private StringProperty type;
  private DoubleProperty price;
  private StringProperty description;
  private ArrayList<ExtraProperty> extras;
  private Item item;

  /**
   * A seven arguments constructor.
   * for loop stores all the extras of an item into an array list.
   * @param item
   */
  public ItemProperty(Item item)
  {
    this.item = item;
    id = new SimpleIntegerProperty(item.getId());
    name = new SimpleStringProperty(item.getName());
    type = new SimpleStringProperty(item.getType());
    price = new SimpleDoubleProperty(item.getPrice());
    description = new SimpleStringProperty(item.getDescription());
    extras = new ArrayList<>();
    for (Extra extra : item.getExtras())
    {
      extras.add(new ExtraProperty(extra));
    }
  }

  /**
   * A overloaded constructor TODO Ask someone what is the difference between this two
   * @param id
   * @param name
   * @param type
   * @param price
   * @param description
   */
  public ItemProperty(IntegerProperty id, StringProperty name,
      StringProperty type, DoubleProperty price, StringProperty description)
  {
    this.id = id;
    this.name = name;
    this.type = type;
    this.price = price;
    this.description = description;
    this.item = new Item(id.get(), name.get(), type.get(), price.get(),
        description.get());
  }

  public IntegerProperty idProperty()
  {
    return id;
  }

  public StringProperty nameProperty()
  {
    return name;
  }

  public StringProperty typeProperty()
  {
    return type;
  }

  public DoubleProperty priceProperty()
  {
    return price;
  }

  public StringProperty descriptionProperty()
  {
    return description;
  }

  public ArrayList<ExtraProperty> getExtras()
  {
    return extras;
  }

  public Item getItem()
  {
    return item;
  }

}
