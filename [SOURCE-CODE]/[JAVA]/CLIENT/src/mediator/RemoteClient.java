package mediator;

import utility.Extra;
import utility.Item;
import utility.ItemList;
import utility.Order;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;

public class RemoteClient implements RemoteCafeServer
{
  private RemoteCafeServer server;

  public RemoteClient() throws MalformedURLException, NotBoundException, RemoteException
  {
    server = (RemoteCafeServer) Naming.lookup("rmi://localhost:1099/Cafe");
    //UnicastRemoteObject.exportObject(this,0);
    //This will be needed when we will be doing the RMI Observer
  }

  public ItemList getAllItems() throws RemoteException, SQLException
  {
    return server.getAllItems();
  }

  @Override public ItemList getItemsByType(String type)
      throws RemoteException, SQLException
  {
    return server.getItemsByType(type);
  }

  public void receiveOrder(Order order) throws RemoteException
  {
    server.receiveOrder(order);
  }

  public void completeOrder(Order order) throws RemoteException
  {
    server.completeOrder(order);
  }

  @Override public void cancelOrder(Order order) throws RemoteException
  {
    server.cancelOrder(order);
  }

  @Override public void editCommentInOrder(Order order, String comment)
      throws RemoteException
  {
    server.editCommentInOrder(order,comment);
  }

  public void receiveUnpaidOrder(Order order) throws RemoteException
  {
    server.receiveUnpaidOrder(order);
  }

  public void acceptPayment(Order order) throws RemoteException
  {
    server.acceptPayment(order);
  }

  public void addItemToProductList(Item item) throws RemoteException
  {
    server.addItemToProductList(item);
  }

  @Override public ArrayList<Order> getAllPendingOrders() throws RemoteException
  {
    return server.getAllPendingOrders();
  }

  @Override public ArrayList<Order> getAllUnpaidOrders() throws RemoteException
  {
    return server.getAllUnpaidOrders();
  }

  public void removeItemFromProductList(Item item) throws RemoteException
  {
    server.removeItemFromProductList(item);
  }

  public ArrayList<Extra> getAllExtrasByType(String type) throws RemoteException
  {
   return server.getAllExtrasByType(type);
  }
}
