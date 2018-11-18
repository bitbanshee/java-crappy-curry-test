import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class Curry implements ICurry<Object, ICurry> {
    private Function fn;
    private int arity = 0;
    private List<Object> lazyArgs = new ArrayList<>();

    public Curry(Function fn) {
        this.fn = fn;
    }

    public Curry(Function fn, int arity) {
        this(fn);
        this.arity = arity < 0 ? 0 : arity;
    }

    private Curry(Function fn, int arity, List<Object> lazyArgs) {
        this(fn, arity);
        this.lazyArgs = lazyArgs;
    }

    public ICurry apply(Object arg) {
        try {
            Function actualFn = applyLazyArgs();
            Object result = actualFn.apply(arg);
            if (result instanceof Function && arity != 1) {
                return new Curry((Function) result, arity - 1);
            }
            return new TerminatedCurry(result);
        } catch (Exception e) {
            return new TerminatedCurry(e);
        }
    }

    private Function applyLazyArgs () {
        if (lazyArgs.size() == 0)
            return fn;

        Function<Object, Function> actualFn = fn;
        for (Object obj : lazyArgs)
            actualFn = actualFn.apply(obj);
        return actualFn;
    }

    public ICurry lazyApply(Object arg) {
        if (arity <= 1)
            return apply(arg);

        List<Object> lazyArgsAccumulator = new ArrayList<>(lazyArgs);
        lazyArgsAccumulator.add(arg);
        return new Curry(fn, arity - 1, lazyArgsAccumulator);
    }

    private class TerminatedCurry implements ICurry<Object, Object> {
        private Object result;

        public TerminatedCurry (Object result) {
            this.result = result;
        }

        public Object get () {
            return result;
        }

        public boolean isError () {
            return result instanceof Throwable;
        }
    }
}
