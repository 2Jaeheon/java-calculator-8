package calculator.domain.delimiter;

public class DelimiterValidator {
    public static void validateDelimiter(String delimiter) {
        if (delimiter == null || delimiter.isEmpty()) {
            throw new IllegalArgumentException("구분자는 비어있을 수 없습니다.");
        }

        if (delimiter.length() != 1) {
            throw new IllegalArgumentException("커스텀 구분자는 한 글자여야 합니다.");
        }

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
