package calculator.domain.delimiter;

public class DelimiterValidator {
    public static void validateDelimiter(String delimiter) {
        if (isNumeric(delimiter)) {
            throw new IllegalArgumentException("숫자는 구분자로 사용할 수 없습니다.");
        }
    }

    private static boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
