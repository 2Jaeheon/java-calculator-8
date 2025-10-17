package calculator.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
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
    @DisplayName("커스텀 구분자가 있지만 문자열에 구분자가 없는 경우에 합계를 계산한다")
    void computeNoDelimiterInContent() {
        // given
        String expression = "//$\n1";

        // when
        int calculate = adder.calculate(expression);

        // then
        assertThat(calculate).isEqualTo(1);
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
    @DisplayName("음수가 포함된 문자열은 오류를 발생한다")
    void computeWithZeroFromCustomComplexDelimiter() {
        // given
        String expression = "//*\n1*-4,6";

        // when & then
        assertThatThrownBy(() -> adder.calculate(expression))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("음수는 계산할 수 없습니다.");
    }

    @Test
    @DisplayName("구분자가 연속된 경우 예외를 발생시킨다.")
    void computeThrowsExceptionForConsecutiveDelimiter() {
        // given
        String expression = "1,,2";

        // when & then
        assertThatThrownBy(() -> adder.calculate(expression))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("입력 문자열에 숫자가 아닌 문자가 포함되어 있습니다.");
    }

    @Test
    @DisplayName("숫자가 아닌 문자가 포함된 경우 예외를 발생시킨다")
    void compute_throws_exception_for_non_numeric_input() {
        // given
        String expression = "1,a,2";

        // when & then
        assertThatThrownBy(() -> adder.calculate(expression))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("입력 문자열에 숫자가 아닌 문자가 포함되어 있습니다.");
    }

    @Test
    @DisplayName("숫자가 커스텀 구분자로 들어가는 경우 예외를 발생시킨다")
    void computeThrowsExceptionWhenNumberIsCustomDelimiter() {

        String expression = "//3\n1*3,6";

        assertThatThrownBy(() -> adder.calculate(expression))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("숫자는 구분자로 사용할 수 없습니다.");
    }

    @Test
    @DisplayName("입력값이 비어있으면 0을 반환한다")
    void computeReturnZeroIfInputEmpty() {
        // given
        String expression = "";

        // when
        int result = adder.calculate(expression);

        // then
        assertThat(result).isZero();
    }

    @Test
    @DisplayName("커스텀 구분자가 없는 경우 문자열의 합을 구해야 한다")
    void computeReturnSumIfNotCustomDelimiter() {
        // given
        String expression = "//\n1,3,6";

        // when & then
        assertThatThrownBy(() -> adder.calculate(expression))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("구분자는 비어있을 수 없습니다.");
    }
}