package functional;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

interface ExFunction<T, R> {
  R apply(T t) throws Throwable;
}

public class PrintFile {
//  public static Optional<Stream<String>> getLines(String fn) {
//    try {
//      return Optional.of(Files.lines(Paths.get(fn)));
//    } catch (IOException e) {
////      System.out.println("oops, file not found");
//      return Optional.empty();
//
////      e.printStackTrace();
////      throw new RuntimeException(e);
////      return null;
//    }
//  }

  public static <T, R> Function<T, Optional<R>> wrap(ExFunction<T, R> op) {
    return t -> {
      try {
        return Optional.of(op.apply(t));
      } catch (Throwable throwable) {
        return Optional.empty();
      }
    };
  }

  public static void main(String[] args) throws Throwable {
    try {
      Stream.of("A.txt", "B.txt", "C.txt")
//          .flatMap(fn -> getLines(fn))
//          .map(fn -> getLines(fn))
          .map(wrap(fn -> Files.lines(Paths.get(fn))))
          .peek(opt -> {
            if (!opt.isPresent()) {
              System.out.println("Problem with file");
            }
          })
          .filter(opt -> opt.isPresent())
          .flatMap(opt -> opt.get())
          .forEach(l -> System.out.println(l));
    } catch (Throwable t) {
      System.out.println("oops, that broke");
    }
//    Files.lines(Paths.get("B.txt"))
//        .forEach(l -> System.out.println(l));
  }
}
