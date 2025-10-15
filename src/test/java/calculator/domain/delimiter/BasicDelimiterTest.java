package calculator.domain.delimiter;

import static org.assertj.core.api.Assertions.assertThat;

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
        String expression2 = "//*\n1*2!3";

        // when
        boolean isSupport = delimiter.support(expression);
        boolean isSupport2 = delimiter.support(expression);

        // then
        Assertions.assertTrue(isSupport);
        Assertions.assertTrue(isSupport2);
    }

    @Test
    @DisplayName("getRegex() 메서드는 항상 기본 구분자 정규표현식을 반환해야 한다")
    void getRegexReturnAlwaysDefaultRegex() {
        // given
        String expression = "1,2:3";
        String expression2 = "1\32\4/23*2";

        // when
        String regex = delimiter.getRegex(expression);
        String regex2 = delimiter.getRegex(expression);

        // then
        assertThat(regex).isEqualTo("[,|]");
        assertThat(regex2).isEqualTo("[,|]");
    }

    @Test
    @DisplayName("getContent() 메서드는 항상 기본 식을 반환해야 한다")
    void getContentReturnAlwaysDefaultContent() {
        // given
        String expression = "1,2:3";
        String expression2 = "//*\n1*2!3";

        // when
        String content = delimiter.getContent(expression);
        String content2 = delimiter.getContent(expression2);

        // then
        assertThat(content).isEqualTo("1,2:3");
        assertThat(content2).isNotEqualTo("content");
    }
}