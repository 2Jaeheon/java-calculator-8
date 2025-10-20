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
    @DisplayName("커스텀 구분자만 사용된 경우 CustomDelimiter를 반환해야 한다")
    void findDelimiterForReturnCustomDelimiterForCustomCase() {
        // given
        String expression = "//;\\n1;2;3";

        // when
        Delimiter delimiter = delimiterFactory.findDelimiterFor(expression);

        // then
        assertThat(delimiter).isInstanceOf(CustomDelimiter.class);
    }

    @Test
    @DisplayName("기본 구분자만 사용된 경우 BasicDelimiter를 반환해야 한다")
    void findDelimiterForReturnBasicDelimiterForBasicCase() {
        // given
        String expression = "1,2:3";

        // when
        Delimiter delimiter = delimiterFactory.findDelimiterFor(expression);

        // then
        assertThat(delimiter).isInstanceOf(BasicDelimiter.class);
    }

    @Test
    @DisplayName("빈 문자열의 경우에는 basicDelimiter를 반환해야 한다")
    void findDelimiterForReturnBasicDelimiterForEmptyExpression() {
        // given
        String expression = "";

        // when
        Delimiter delimiter = delimiterFactory.findDelimiterFor(expression);

        // then
        assertThat(delimiter).isInstanceOf(BasicDelimiter.class);
    }
}