package pos;

import java.io.IOException;

class ModemHandler {
  public static void dialModem(int num) throws IOException {

  }
}
public class SellStuff {
  public void authorizeCC(int ccnum) throws IOException {
    int retries = 3;
    boolean success = false;
    while (!success) {
      try {
        // dial modem
        ModemHandler.dialModem(303123);
        // do protocol
        // get paid
        success = true;
      } catch (IOException ioe) {
        if (--retries == 0) {
          throw ioe;
        }
      }
    }
  }
  public void sellStuff() {
    try {
      authorizeCC(1234);
    } catch (IOException ioe) {
      System.out.println("HELP!!!");
    }
  }
}
