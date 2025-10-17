package calculator.domain;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Adder implements Calculator {
    private final Splitter splitter;

    public Adder(Splitter splitter) {
        this.splitter = splitter;
    }

    @Override
    public int calculate(String expression) {
        String[] numbers = splitter.split(expression);

        if (numbers.length == 0) {
            return 0;
        }

        List<Integer> intNumbers = toInt(numbers);
        return intNumbers.stream().mapToInt(Integer::intValue).sum();
    }

    private List<Integer> toInt(String[] numbers) {
        return Arrays.stream(numbers)
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }
}
