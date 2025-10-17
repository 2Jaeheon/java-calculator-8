package calculator.domain.delimiter;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ComplexDelimiter implements Delimiter {

    private static final Pattern CUSTOM_DELIMITER_PATTERN = Pattern.compile("//(.*)\n(.*)");
    private static final Pattern BASIC_DELIMITER_PATTERN = Pattern.compile("[,:]");
    private static final String BASIC_DELIMITER = ",|:";

    @Override
    public boolean support(String expression) {
        Matcher customMatcher = CUSTOM_DELIMITER_PATTERN.matcher(expression);
        if (!customMatcher.find()) {
            return false;
        }

        String content = customMatcher.group(2);

        Matcher basicMatcher = BASIC_DELIMITER_PATTERN.matcher(content);
        return basicMatcher.find();
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

    private static String extractUniqueDelimiters(Matcher matcher) {
        String customDelimiterStr = matcher.group(1);

        Set<Character> uniqueDelimiters = new LinkedHashSet<>();
        for (char c : customDelimiterStr.toCharArray()) {
            uniqueDelimiters.add(c);
        }

        String customRegexPart = uniqueDelimiters.stream()
                .map(c -> Pattern.quote(String.valueOf(c)))
                .collect(Collectors.joining("|"));

        return customRegexPart + "|" + BASIC_DELIMITER;
    }

}
