package calculator.domain.delimiter;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.regex.Pattern;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


class BasicDelimiterTest {

    Delimiter delimiter;

    @BeforeEach
    void setUp() {
        delimiter = new BasicDelimiter();
    }

    @Test
    @DisplayName("support() 메서드는 언제나 true를 반환한다")
    void supportAlwaysTrue() {
        // given
        String expression = "1,2:3";
        boolean expected = true;

        // when
        boolean support = delimiter.support(expression);

        // then
        assertThat(support).isEqualTo(expected);
    }

    @Test
    @DisplayName("tokenize() 메서드는 문자를 분리해야 한다")
    void tokenizeAlwaysReturnTokenizedValues() {
        // given
        String expression = "1,2:3";
        String[] expected = new String[]{"1", "2", "3"};

        // when
        String[] tokenize = delimiter.tokenize(expression);

        // then
        assertThat(tokenize).isEqualTo(expected);
    }

    @Test
    @DisplayName("입력값이 빈 문자열인 경우에는 빈 문자열을 반환한다")
    void tokenizeReturnEmptyStringArrayWhenExpressionIsEmpty() {
        // given
        String expression = "";
        String[] expected = new String[]{""};

        // when
        String[] tokenize = delimiter.tokenize(expression);

        // then
        assertThat(tokenize).isEqualTo(expected);
    }

    @Test
    @DisplayName("입력값이 1:2, 와 같은 경우에 마지막 빈 문자열이 분리되어야 한다")
    void tokenizeReturnTrailingEmptyTokenWhenExpressionEndsWithDelimiter() {
        // given
        String expression = "1,2:";
        String[] expected = new String[]{"1", "2", ""};

        // when
        String[] tokenize = delimiter.tokenize(expression);

        // then
        assertThat(tokenize).isEqualTo(expected);
    }
}