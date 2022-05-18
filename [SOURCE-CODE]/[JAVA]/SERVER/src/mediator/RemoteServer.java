package mediator;

import database.CafeDatabase;
import database.CafePersistence;
import database.ConcreteOrderDAO;
import database.OrderDAO;
import utility.Item;
import utility.ItemList;
import utility.Order;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.ArrayList;

public class RemoteServer implements RemoteCafeServer

{
  private CafePersistence cafePersistence;

  public RemoteServer()
      throws RemoteException, MalformedURLException, SQLException
  {
    cafePersistence = CafeDatabase.getInstance();
    startRegistry();
    startServer();
  }

  private void startRegistry() throws RemoteException
  {
    try
    {
      Registry reg = LocateRegistry.createRegistry(1099);
      System.out.println("Registry started...");
    }
    catch (java.rmi.server.ExportException e)
    {
      System.out.println("Registry already started? " + e.getMessage());
    }
  }

  private void startServer() throws RemoteException, MalformedURLException
  {
    UnicastRemoteObject.exportObject(this, 0);
    Naming.rebind("Cafe", this);
    System.out.println("Server started...");
  }

  @Override public ItemList getAllItems() throws RemoteException, SQLException
  {
    return cafePersistence.getAllItems();
  }

  @Override public ItemList getItemsByType(String type)
      throws RemoteException, SQLException
  {
    return null;
  }

  @Override public void receiveOrder(Order order) throws RemoteException
  {
    cafePersistence.receiveOrder(order);
    //Shouldn't we call the CafePersistence instead?
    //CafeDatabase get instance method is not used

    System.out.println(order);
  }

  @Override public void completeOrder(Order order) throws RemoteException
  {
    cafePersistence.completeOrder(order.getId());
  }

  @Override public void cancelOrder(Order order) throws RemoteException
  {
    cafePersistence.cancelOrder(order.getId());
  }

  @Override public void editCommentInOrder(Order order, String comment)
      throws RemoteException
  {
    cafePersistence.editComment(order.getId(),comment);
  }

  @Override public void receiveUnpaidOrder(Order order) throws RemoteException
  {
    cafePersistence.receiveOrder(order);
  }

  @Override public void acceptPayment(Order order) throws RemoteException
  {
    cafePersistence.acceptPayment(order.getId());
  }

  @Override public void addItemToProductList(Item item)
  {
    cafePersistence.addItemToProductList(item);
  }

  @Override public ArrayList<Order> getAllPendingOrders() throws RemoteException
  {
    return cafePersistence.getOrdersByStatus("pending");
  }

  @Override public ArrayList<Order> getAllUnpaidOrders() throws RemoteException
  {
    return cafePersistence.getOrdersByStatus("unpaid");
  }

  @Override public void removeItemFromProductList(Item item)
      throws RemoteException, SQLException
  {
    cafePersistence.removeItemFromProductList(item);
  }
}
