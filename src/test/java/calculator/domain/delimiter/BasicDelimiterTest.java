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
    @DisplayName("support()는 어떤 입력에도 true를 반환한다")
    void support_alwaysTrue() {
        // given
        String expression = "1,2:3";
        boolean expected = true;

        // when
        boolean actual = delimiter.support(expression);

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("tokenize() 메서드는 문자를 기본 구분자로 분리해야 한다")
    void tokenize_basicDelimiter() {
        // given
        String expression = "1,2:3";
        String[] expected = new String[]{"1", "2", "3"};

        // when
        String[] tokenize = delimiter.tokenize(expression);

        // then
        assertThat(tokenize).isEqualTo(expected);
    }

    @Test
    @DisplayName("tokenize()는 입력값이 빈 문자열인 경우에 빈 문자열을 반환한다")
    void tokenize_emptyExpression() {
        // given
        String expression = "";
        String[] expected = new String[]{""};

        // when
        String[] actual = delimiter.tokenize(expression);

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("tokenize()는 입력값이 1:2, 와 같은 경우에 마지막 빈 문자열이 분리되어야 한다")
    void tokenize_trailingDelimiter() {
        // given
        String expression = "1,2:";
        String[] expected = new String[]{"1", "2", ""};

        // when
        String[] actual = delimiter.tokenize(expression);

        // then
        assertThat(actual).isEqualTo(expected);
    }
}