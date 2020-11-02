package functional;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class PrintFile {
  public static Stream<String> getLines(String fn) {
    try {
      return Files.lines(Paths.get(fn));
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }
  public static void main(String[] args) throws Throwable {
    Stream.of("A.txt"/*, "B.txt"*/, "C.txt")
        .flatMap(fn -> getLines(fn))
        .forEach(l -> System.out.println(l));

//    Files.lines(Paths.get("B.txt"))
//        .forEach(l -> System.out.println(l));
  }
}
