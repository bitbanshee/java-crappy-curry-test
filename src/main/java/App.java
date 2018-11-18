import java.util.function.Function;

public class App {
    public static void main (String[] args) {
        System.out.println("==== REAL LIFE(-ISH) USAGES ====");
        Curry add = new Curry((Function<Integer, Function>) a ->
            (Function<Integer, Integer>) b ->
                a + b);

        System.out.println("");
        System.out.println("Adding 5 to 10: " + ((ICurry) add.apply(5).apply(10)).get());

        ICurry<Integer, ICurry> addTo4 = add.apply(4);
        System.out.println("Adding 9 to 4: " + addTo4.apply(9).get());
        System.out.println("Adding 13 to 4: " + addTo4.apply(13).get());

        Function<String, Function> greetingBuilder = _greeting ->
            (Function<String, Function>) punctuation ->
                (Function<String, Function>) suffix ->
                    (Function<String, String>) vocative ->
                        _greeting + ", " + vocative + punctuation +
                            (suffix.length() > 0 ? (" " + suffix) : "");
        Curry greeting = new Curry(greetingBuilder, 4);

        ICurry hiGreeting = greeting.lazyApply("Hi");
        ICurry excitedHiGreeting = (ICurry) hiGreeting.lazyApply("!");
        ICurry worriedExcitedHiGreetingTo = (ICurry) excitedHiGreeting.lazyApply("Are you ok? :/");

        System.out.println("");
        System.out.println(
        ((ICurry) worriedExcitedHiGreetingTo.lazyApply("Clarice")).get()); // Hi, Clarice! Are you ok? :/

        System.out.println(
        ((ICurry) ((ICurry)
            excitedHiGreeting.lazyApply("You look very well!"))
                .lazyApply("Simone"))
                .get()); // Hi, Simone! You look very well!

        System.out.println(
        ((ICurry) ((ICurry) ((ICurry)
            hiGreeting.lazyApply("."))
                .lazyApply("Watch out! ~bullet flies"))
                .lazyApply("Claudia"))
                .get()); // Hi, Claudia. Watch out! ~bullet flies

        System.out.println(
        ((ICurry) ((ICurry) ((ICurry)
            greeting.lazyApply("Excuse me")
                .lazyApply("?"))
                .lazyApply("Can you help me?"))
                .lazyApply("Ada"))
                .get()); // Excuse me, Ada? Can you help me?


        System.out.println("");
        System.out.println("");
        System.out.println("=== TESTS (yeah, here, I'm lazy) ===");
        Function test = a ->
                (Function) b ->
                        (Function) c ->
                                a.toString() + b.toString() + c.toString();

        Curry curriedWithoutArity = new Curry(test);

        System.out.println("");
        System.out.println("Test 1: direct apply without arity");
        System.out.println(
                ((ICurry) ((ICurry)
                        curriedWithoutArity
                                .apply("oi, ")
                                .apply("george, "))
                        .apply("como vai?"))
                        .apply(null));

        System.out.println("");
        System.out.println("Test 2: lazy apply without arity (should behave as direct apply)");
        System.out.println(
                ((ICurry) ((ICurry)
                        curriedWithoutArity
                                .lazyApply("oi, ")
                                .lazyApply("george, "))
                        .lazyApply("como vai?"))
                        .lazyApply(null));

        System.out.println("");
        System.out.println("Test 3: direct and lazy applies without arity (should behave as direct apply)");
        System.out.println(
                ((ICurry) ((ICurry)
                        curriedWithoutArity
                                .lazyApply("oi, ")
                                .apply("george, "))
                        .lazyApply("como vai?"))
                        .apply(null));

        Curry curriedWithRightArity = new Curry(test, 3);

        System.out.println("");
        System.out.println("Test 4: direct apply with arity");
        System.out.println(
                ((ICurry) ((ICurry)
                        curriedWithRightArity
                                .apply("oi, ")
                                .apply("george, "))
                        .apply("como vai?"))
                        .apply(null));

        System.out.println("");
        System.out.println("Test 5: direct apply with arity applying more times then arity");
        try {
            System.out.println(
                    ((ICurry) ((ICurry) ((ICurry)
                            curriedWithRightArity
                                    .apply("oi, ")
                                    .apply("george, "))
                            .apply("como vai?"))
                            .apply(" :)"))
                            .apply(null));
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }

        System.out.println("");
        System.out.println("Test 6: lazy apply with arity");
        System.out.println(
                ((ICurry) ((ICurry)
                        curriedWithRightArity
                                .lazyApply("oi, ")
                                .lazyApply("george, "))
                        .lazyApply("como vai?"))
                        .lazyApply(null));

        System.out.println("");
        System.out.println("Test 7: lazy apply with arity applying more times then arity");
        try {
            System.out.println(
                    ((ICurry) ((ICurry) ((ICurry)
                            curriedWithRightArity
                                    .lazyApply("oi, ")
                                    .lazyApply("george, "))
                            .lazyApply("como vai?"))
                            .lazyApply(" :)"))
                            .lazyApply(null));
        } catch (Exception e) {
            e.printStackTrace(System.out);;
        }

        System.out.println("");
        System.out.println("Test 8: direct and lazy applies with arity");
        System.out.println(
                ((ICurry) ((ICurry)
                        curriedWithRightArity
                                .apply("oi, ")
                                .lazyApply("george, "))
                        .apply("como vai?"))
                        .lazyApply(null));

        System.out.println("");
        System.out.println("Test 9: direct and lazy applies with arity applying more times then arity");
        try {
            System.out.println(
                    ((ICurry) ((ICurry) ((ICurry)
                            curriedWithRightArity
                                    .apply("oi, ")
                                    .lazyApply("george, "))
                            .apply("como vai?"))
                            .lazyApply(" :)"))
                            .apply(null));
        } catch (Exception e) {
            e.printStackTrace(System.out);;
        }

        System.out.println("");
        System.out.println("Test 10: direct and lazy applies with arity");
        System.out.println(
                ((ICurry) ((ICurry)
                        curriedWithRightArity
                                .apply("oi, ")
                                .lazyApply("george, "))
                        .apply("como vai?"))
                        .get());
    }
}
