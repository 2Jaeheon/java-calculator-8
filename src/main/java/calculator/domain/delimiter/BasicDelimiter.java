package calculator.domain.delimiter;

public class BasicDelimiter implements Delimiter {

    private static final String BASIC_DELIMITER_REGEX = ",|:";

    @Override
    public boolean support(String expression) {
        return true;
    }

    @Override
    public String getRegex(String expression) {
        return BASIC_DELIMITER_REGEX;
    }

    @Override
    public String getContent(String expression) {

        return expression;
    }
}
