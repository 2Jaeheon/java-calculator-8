package calculator.domain.delimiter;

import java.util.Arrays;

public class BasicDelimiter implements Delimiter {

    private static final String BASIC_DELIMITER_REGEX = ",|:";

    @Override
    public boolean support(String expression) {
        return true;
    }

    @Override
    public String[] tokenize(String expression) {
        return expression.split(BASIC_DELIMITER_REGEX, -1);
    }
}
