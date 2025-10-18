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
        return expression.startsWith("//");
    }

    @Override
    public String getRegex(String expression) {
        Matcher matcher = CUSTOM_DELIMITER_PATTERN.matcher(expression);
        if (!matcher.find()) {
            return "";
        }
        return extractUniqueDelimiters(matcher);
    }

    @Override
    public String getContent(String expression) {
        Matcher matcher = CUSTOM_DELIMITER_PATTERN.matcher(expression);

        if (matcher.find()) {
            return matcher.group(2);
        }

        return expression;
    }

    private String extractUniqueDelimiters(Matcher matcher) {
        String customDelimiterStr = matcher.group(1);

        Set<Character> uniqueDelimiters = new LinkedHashSet<>();
        for (char c : customDelimiterStr.toCharArray()) {
            uniqueDelimiters.add(c);
        }

        for (Character delimiterChar : uniqueDelimiters) {
            DelimiterValidator.validateDelimiter(String.valueOf(delimiterChar));
        }

        return uniqueDelimiters.stream()
                .map(c -> Pattern.quote(String.valueOf(c)))
                .collect(Collectors.joining("|"));
    }
}
