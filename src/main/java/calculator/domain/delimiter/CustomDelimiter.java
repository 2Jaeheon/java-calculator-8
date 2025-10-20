package calculator.domain.delimiter;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class CustomDelimiter implements Delimiter {

    private static final Pattern CUSTOM_DELIMITER_PATTERN = Pattern.compile("//(.*)\\\\n(.*)");

    @Override
    public boolean support(String expression) {
        return expression != null && expression.startsWith("//");
    }

    @Override
    public String[] tokenize(String expression) {
        Matcher matcher = CUSTOM_DELIMITER_PATTERN.matcher(expression);

        if (!matcher.matches()) {
            throw new IllegalArgumentException("잘못된 커스텀 구분자 형식입니다.");
        }

        String delimiter = matcher.group(1);
        String content = matcher.group(2);

        DelimiterValidator.validateDelimiter(delimiter);

        String regex = Pattern.quote(delimiter);
        return content.split(regex, -1);
    }
}
