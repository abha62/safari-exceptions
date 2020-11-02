package pos;

import java.io.IOException;
import java.net.Socket;

// Should perhaps simply use IOException...
class InfrastructureException extends Exception {
  public InfrastructureException() {
  }

  public InfrastructureException(String message) {
    super(message);
  }

  public InfrastructureException(String message, Throwable cause) {
    super(message, cause);
  }

  public InfrastructureException(Throwable cause) {
    super(cause);
  }

  public InfrastructureException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}

class NoMoneyException extends Exception {}

class CardStolenException extends Exception {}

class ModemDidNotConnectException extends Exception {}

class ModemHandler {
  public static void dialModem(int num) throws ModemDidNotConnectException {

  }
}

public class SellStuff {
  private static boolean USE_MODEM = false;
//  public void authorizeCC(int ccnum) throws ModemDidNotConnectException, IOException {
  public void authorizeCC(int ccnum) throws InfrastructureException, NoMoneyException {
    int retries = 3;
    boolean success = false;
    while (!success) {
      try {
        if (USE_MODEM) {
          // dial modem
          ModemHandler.dialModem(303123);
        } else {
          // use network
          Socket s = new Socket("127.0.0.1", 8000);
        }
        // do protocol
        if (Math.random() > 0.5) {
          throw new NoMoneyException();
        }
        // get paid
        success = true;
//      } catch (ModemDidNotConnectException mdnce) {
//        if (--retries == 0) {
//          throw new InfrastructureException(mdnce);
//        }
//      } catch (IOException ioe){
//        if (--retries == 0) {
//          throw new InfrastructureException(ioe);
//        }
//      }

//      } catch (Exception ioe) {

      } catch (ModemDidNotConnectException | IOException ioe) {
        if (--retries == 0) {
          throw new InfrastructureException(ioe);
        }
      }
    }
  }

  public void sellStuff() {
    try {
      authorizeCC(1234);
    } catch (InfrastructureException ioe) {
      System.out.println("HELP!!!");
    } catch (NoMoneyException nme) {

    }
  }
}
