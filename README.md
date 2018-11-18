# java-crappy-curry-test
A (very) crappy curry implementation in Java with support to lazy application of arguments.

[WARN] This project is VERY experimental, do not (never) use it in production.

Real life(-ish) usages:
```java
Curry add = new Curry((Function<Integer, Function>) a ->
    (Function<Integer, Integer>) b ->
        a + b);

add.apply(4).apply(10).apply(null); // 14

Curry addTo4 = add.apply(4);

// It also implements Supplier interface
addTo4.apply(10).get(); //14
addTo4.apply(15).get(); //19
addTo4.apply(2).get(); //6

// The main goal is to use lazy application of arguments. The curried function
// will be called only when Curry's `apply` is called, or if the arity was not
// provided, or if the arity is 1 (termination function)
Function<String, Function> greetingBuilder = _greeting ->
    (Function<String, Function>) punctuation ->
        (Function<String, Function>) suffix ->
            (Function<String, String>) vocative ->
                _greeting + ", " + vocative + punctuation +
                    (suffix.length() > 0 ? (" " + suffix) : "");

// With arity > 1, we can use lazy application
Curry greeting = new Curry(greetingBuilder, 4);

ICurry hiGreeting = greeting.lazyApply("Hi");
ICurry excitedHiGreeting = (ICurry) hiGreeting.lazyApply("!");
ICurry worriedExcitedHiGreetingTo = (ICurry) excitedHiGreeting.lazyApply("Are you ok? :/");

((ICurry)
    // `greetingBuilder` and its partials are called only here
    worriedExcitedHiGreetingTo.lazyApply("Clarice")).get(); // Hi, Clarice! Are you ok? :/

((ICurry) ((ICurry)
    excitedHiGreeting.lazyApply("You look very well!"))
        // `greetingBuilder` and its partials are called only here
        .lazyApply("Simone"))
        .get(); // Hi, Simone! You look very well!

((ICurry) ((ICurry) ((ICurry)
    hiGreeting.lazyApply("."))
        .lazyApply("Watch out! ~bullet flies"))
        // `greetingBuilder` and its partials are called only here
        .lazyApply("Claudia"))
        .get(); // Hi, Claudia. Watch out! ~bullet flies

((ICurry) ((ICurry) ((ICurry)
    greeting.lazyApply("Excuse me")
        .lazyApply("?"))
        .lazyApply("Can you help me?"))
        // `greetingBuilder` and its partials are called only here
        .lazyApply("Ada"))
        .get(); // Excuse me, Ada? Can you help me?
```
