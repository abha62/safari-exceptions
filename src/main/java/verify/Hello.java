package verify;

import java.util.function.Consumer;

public class Hello {
  public static void main(String[] args) {
    ((Runnable)(() -> System.out.println("Hello World!"))).run();
  }
}
