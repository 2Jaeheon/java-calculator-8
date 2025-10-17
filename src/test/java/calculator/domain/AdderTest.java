package calculator.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AdderTest {

    Splitter splitter;
    Adder adder;
    DelimiterFactory delimiterFactory;

    @BeforeEach
    void setUp() {
        delimiterFactory = new DelimiterFactory();
        splitter = new Splitter(delimiterFactory);
        adder = new Adder(splitter);
    }

    @Test
    @DisplayName("기본 구분자가 포함된 문자열의 합계 계산")
    void computeWithBasicDelimiter() {
        // given
        String expression = "1,2:3";

        // when
        int calculate = adder.calculate(expression);

        // then
        assertThat(calculate).isEqualTo(6);
    }

    @Test
    @DisplayName("커스텀 구분자가 포함된 문자열의 함계를 계산한다")
    void computeWithCustomDelimiter() {
        // given
        String expression = "//*\n1*3*6";

        // when
        int calculate = adder.calculate(expression);

        // then
        assertThat(calculate).isEqualTo(10);
    }

    @Test
    @DisplayName("복합 구분자가 포함된 문자열의 함계를 계산한다")
    void computeWithComplexDelimiter() {
        // given
        String expression = "//*\n1*3,6";

        // when
        int calculate = adder.calculate(expression);

        // then
        assertThat(calculate).isEqualTo(10);
    }

    @Test
    @DisplayName("음수가 포함된 문자열의 함계를 계산한다")
    void computeWithZeroFromCustomComplexDelimiter() {
        // given
        String expression = "//*\n1*-4,6";

        // when
        int calculate = adder.calculate(expression);

        // then
        assertThat(calculate).isEqualTo(3);
    }
}