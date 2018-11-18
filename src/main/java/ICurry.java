import java.util.function.Function;
import java.util.function.Supplier;

public interface  ICurry <T, R> extends Function<T, R>, Supplier<R> {
    default R get () {
        return null;
    }

    default boolean hasReachedResult () {
        return get() != null;
    }

    default R apply (T arg) {
        return this.get();
    }

    default R lazyApply(T arg) {
        return apply(arg);
    }
}
