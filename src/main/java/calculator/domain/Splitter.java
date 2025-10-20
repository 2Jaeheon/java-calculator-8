package calculator.domain;

import calculator.domain.delimiter.Delimiter;

public class Splitter {
    private final DelimiterFactory delimiterFactory;

    public Splitter(DelimiterFactory delimiterFactory) {
        this.delimiterFactory = delimiterFactory;
    }

    public String[] split(String expression) {
        if (expression == null || expression.isEmpty()) {
            return new String[0];
        }

        Delimiter delimiter = delimiterFactory.findDelimiterFor(expression);

        return delimiter.tokenize(expression);
    }
}
