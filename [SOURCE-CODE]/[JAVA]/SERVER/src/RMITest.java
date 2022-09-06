import mediator.RemoteServer;
import mediator.RemoteServerClient;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.SQLException;

public class RMITest
{
  public static void main(String[] args)
  {
    try
    {
      RemoteServerClient server = new RemoteServerClient();
    }
    catch (RemoteException | MalformedURLException e)
    {
      e.printStackTrace();
    } catch (NotBoundException e) {
      throw new RuntimeException(e);
    }
  }
}
