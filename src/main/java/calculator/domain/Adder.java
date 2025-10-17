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
        validateNoNegativeNumbers(intNumbers);
        return intNumbers.stream().mapToInt(Integer::intValue).sum();
    }

    private List<Integer> toInt(String[] numbers) {
        try {
            return Arrays.stream(numbers)
                    .map(Integer::parseInt)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new IllegalArgumentException("입력 문자열에 숫자가 아닌 문자가 포함되어 있습니다.");
        }
    }

    private void validateNoNegativeNumbers(List<Integer> numbers) {
        List<Integer> negativeNumbers = numbers.stream()
                .filter(n -> n < 0)
                .toList();

        if (!negativeNumbers.isEmpty()) {
            throw new IllegalArgumentException("음수는 계산할 수 없습니다.");
        }
    }
}
