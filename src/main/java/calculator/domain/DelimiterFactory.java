package calculator.domain;

import calculator.domain.delimiter.BasicDelimiter;
import calculator.domain.delimiter.CustomDelimiter;
import calculator.domain.delimiter.Delimiter;
import java.util.Arrays;
import java.util.List;

public class DelimiterFactory {

    private final List<Delimiter> delimiters;

    public DelimiterFactory() {
        this.delimiters = Arrays.asList(
                new CustomDelimiter(),
                new BasicDelimiter()
        );
    }

    public Delimiter findDelimiterFor(String expression) {
        for (Delimiter delimiter : delimiters) {
            if (delimiter.support(expression)) {
                return delimiter;
            }
        }

        throw new IllegalStateException("적합한 구분자를 찾을 수 없습니다.");
    }
}
