package calculator.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import calculator.domain.delimiter.BasicDelimiter;
import calculator.domain.delimiter.CustomDelimiter;
import calculator.domain.delimiter.Delimiter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DelimiterFactoryTest {

    private DelimiterFactory delimiterFactory;

    @BeforeEach
    void setUp() {
        delimiterFactory = new DelimiterFactory();
    }

    @Test
    @DisplayName("findDelimiterFor()은 커스텀 구분자만 사용된 경우 CustomDelimiter를 반환해야 한다.")
    void findDelimiter_customExpression_customDelimiter() {
        // given
        String expression = "//;\\n1;2;3";

        // when
        Delimiter actual = delimiterFactory.findDelimiterFor(expression);

        // then
        assertThat(actual).isInstanceOf(CustomDelimiter.class);
    }

    @Test
    @DisplayName("findDelimiterFor()은 기본 구분자만 사용된 경우 BasicDelimiter를 반환해야 한다.")
    void findDelimiterFor_basicExpression_basicDelimiter() {
        // given
        String expression = "1,2:3";

        // when
        Delimiter actual = delimiterFactory.findDelimiterFor(expression);

        // then
        assertThat(actual).isInstanceOf(BasicDelimiter.class);
    }

    @Test
    @DisplayName("findDelimiterFor()은 빈 문자열의 경우에 basicDelimiter를 반환해야 한다.")
    void findDelimiterFor_nonExpression_BasicDelimiter() {
        // given
        String expression = "";

        // when
        Delimiter actual = delimiterFactory.findDelimiterFor(expression);

        // then
        assertThat(actual).isInstanceOf(BasicDelimiter.class);
    }
}