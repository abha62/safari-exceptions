package functional;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

class Either<L, R> {
  private L left;
  private R right;

  private Either() {
  }

  public static <L, R> Either<L, R> failure(L problem) {
    Either<L, R> rv = new Either<>();
    rv.left = problem;
    return rv;
  }

  public static <L, R> Either<L, R> success(R value) {
    Either<L, R> rv = new Either<>();
    rv.right = value;
    return rv;
  }

//  public <R1> Either<L, R1> mapSuccess(Function<R, R1> fn) {}

//  public <L1, R1> Either<L1, R1> flatMapSuccess(Function<R, Either<L1, R1>> op) {
//    if (isSuccess()) {
//      return op.apply(right);
//    } else throw new IllegalStateException("no success");
//  }

  public Either<L, R> recover(Function<L, Either<L, R>> op) {
    if (isSuccess()) {
      return this;
    } else {
      return op.apply(left);
    }
  }

  public boolean isFailure() {
    return left != null;
  }

  public boolean isSuccess() {
    return left == null;
  }

  public L getFailure() {
    if (isFailure()) return left;
    else throw new IllegalStateException("No failure in a success!");
  }

  public R getSuccess() {
    if (isSuccess()) return right;
    else throw new IllegalStateException("No success in a failure!");
  }

  public static <T, R> Function<T, Either<Throwable, R>> wrap(ExFunction<T, R> op) {
    return t -> {
      try {
        return Either.success(op.apply(t));
      } catch (Throwable throwable) {
        return Either.failure(throwable);
      }
    };
  }
}

public class UseEither {
  public static void main(String[] args) throws Throwable {
    Stream.of("A.txt", "B.txt", "C.txt")
        .map(Either.wrap(fn -> Files.lines(Paths.get(fn))))
        .peek(e -> {
          if (e.isFailure()) {
            System.out.println("Problem with file: " +
                e.getFailure().getClass() + " " +
                e.getFailure().getMessage());
          }
        })
        .map(e -> e.recover(Either.wrap(e -> Files.lines(Paths.get("D.txt")))))
        .filter(e -> e.isSuccess())
        .flatMap(e -> e.getSuccess())
        .forEach(l -> System.out.println(l));
  }
}
