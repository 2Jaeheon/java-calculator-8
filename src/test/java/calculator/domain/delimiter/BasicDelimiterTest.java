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
    @DisplayName("getRegex() 메서드는 항상 기본 구분자 정규표현식을 반환해야 한다")
    void getRegexReturnAlwaysDefaultRegex() {
        // given
        String expression = "1,2:3";
        String expected = ",|:";

        // when
        String regex = delimiter.getRegex(expression);

        // then
        assertThat(regex).isEqualTo(expected);
    }

    @Test
    @DisplayName("getContent() 메서드는 항상 기본 식을 반환해야 한다")
    void getContentReturnAlwaysDefaultContent() {
        // given
        String expression = "1,2:3";
        String expected = "1,2:3";

        // when
        String content = delimiter.getContent(expression);

        // then
        assertThat(content).isEqualTo(expected);
    }
}