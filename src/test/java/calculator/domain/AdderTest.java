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
    @DisplayName("calculate()는 기본 구분자가 포함된 문자열의 합계를 계산한다.")
    void calculate_basicDelimiter_sum() {
        // given
        String expression = "1,2:3";
        int expected = 6;

        // when
        int actual = adder.calculate(expression);

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("calculate()는 커스텀 구분자가 포함된 문자열의 함계를 계산한다.")
    void calculate_custom_delimiter_sum() {
        // given
        String expression = "//*\\n1*3*6";
        int expected = 10;

        // when
        int actual = adder.calculate(expression);

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("calculate()는 커스텀 구분자가 있지만 문자열에 구분자가 없는 경우에 합계를 계산한다.")
    void calculate_custom_singleNumber_sum() {
        // given
        String expression = "//$\\n1";
        int expected = 1;

        // when
        int actual = adder.calculate(expression);

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("calculate()는 음수가 포함된 문자열은 예외를 발생해야 한다.")
    void calculate_custom_negativeNumber_throws() {
        // given
        String expression = "//*\\n1*-4*6";

        // when & then
        assertThatThrownBy(() -> adder.calculate(expression))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("음수는 계산할 수 없습니다.");
    }

    @Test
    @DisplayName("calculate()는 커스텀 구분자 표현식에서 공백이 포함된 입력 ' 1 * 2 ' 일 때 예외를 발생해야 한다.")
    void calculate_custom_whitespace_throws() {
        // given
        String expression = "//*\\n 1 * 2 ";

        // when & then
        assertThatThrownBy(() -> adder.calculate(expression))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("입력 문자열에 문제가 있습니다.");
    }

    @Test
    @DisplayName("calculate()는 커스텀 구분자 표현식에서 커스텀 구분자 이외 문자가 존재하면 예외를 발생해야 한다.")
    void calculate_custom_invalidDelimiter_throws() {
        // given
        String expression = "//*\\n1*2,3";

        // when & then
        assertThatThrownBy(() -> adder.calculate(expression))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("입력 문자열에 문제가 있습니다.");
    }

    @Test
    @DisplayName("calculate()는 커스텀 구분자가 없는 경우 예외를 발생해야 한다.")
    void calculate_custom_nonDelimiter_throws() {
        // given
        String expression = "//\\n1,3,6";

        // when & then
        assertThatThrownBy(() -> adder.calculate(expression))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("구분자는 비어있을 수 없습니다.");
    }

    @Test
    @DisplayName("calculate()는 숫자가 커스텀 구분자로 들어가는 경우 예외를 발생해야 한다.")
    void calculate_custom_numberDelimiter_throws() {
        // given
        String expression = "//3\\n136";

        // when & then
        assertThatThrownBy(() -> adder.calculate(expression))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("숫자는 구분자로 사용할 수 없습니다.");
    }

    @Test
    @DisplayName("calculate()는 구분자가 연속된 경우 예외를 발생시킨다.")
    void calculate_multiDelimiter_throws() {
        // given
        String expression = "1,,2";

        // when & then
        assertThatThrownBy(() -> adder.calculate(expression)).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("입력 문자열에 문제가 있습니다.");
    }

    @Test
    @DisplayName("calculate()는 숫자가 아닌 문자가 포함된 경우 예외를 발생시킨다.")
    void calculate_nonDigit_throws() {
        // given
        String expression = "1,a,2";

        // when & then
        assertThatThrownBy(() -> adder.calculate(expression))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("입력 문자열에 문제가 있습니다.");
    }

    @Test
    @DisplayName("calculate()는 기본 구분자 표현식에서 기본 구분자 이외 문자가 존재하는 경우 예외를 출력해야 한다.")
    void calculate_invalidDelimiter_throws() {
        // given
        String expression = "1,2:3*4";

        // when & then
        assertThatThrownBy(() -> adder.calculate(expression))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("입력 문자열에 문제가 있습니다.");
    }

    @Test
    @DisplayName("calculate()는 기본 구분자 표현식에서 연속 구분자 1,,2일 때 예외를 발생해야 한다.")
    void calculate_consecutiveDelimiter_throws() {
        // given
        String expression = "1,,2";

        // when & then
        assertThatThrownBy(() -> adder.calculate(expression))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("입력 문자열에 문제가 있습니다.");
    }

    @Test
    @DisplayName("calculate()는 기본 구분자 표현식에서 공백이 포함된 입력 1 , 2일 때 예외를 발생해야 한다.")
    void calculate_whiteSpace_throws() {
        // given
        String expression = " 1 , 2 ";

        // when & then
        assertThatThrownBy(() -> adder.calculate(expression))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("입력 문자열에 문제가 있습니다.");
    }

    @Test
    @DisplayName("calculate()는 기본 구분자 표현식에서 마지막 구분자가 존재하는 문자열인 1,2,일 때 예외를 발생해야 한다.")
    void calculate_trailingDelimiter_throws() {
        // given
        String expression = "1,2,";

        // when & then
        assertThatThrownBy(() -> adder.calculate(expression))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("입력 문자열에 문제가 있습니다.");
    }

    @Test
    @DisplayName("calculate()는 기본 구분자 표현식에서 음수가 존재하면 예외를 발생해야 한다.")
    void calculate_negative_throws() {
        // given
        String expression = "1:-4:6";

        // when & then
        assertThatThrownBy(() -> adder.calculate(expression))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("음수는 계산할 수 없습니다.");
    }

    @Test
    @DisplayName("calculate()는 입력값이 비어있으면 0을 반환한다.")
    void calculate_nonExpression_zero() {
        // given
        String expression = "";

        // when
        int result = adder.calculate(expression);

        // then
        assertThat(result).isZero();
    }

    @Test
    @DisplayName("calculate()는 입력값이 null 인 경우 0을 반환한다.")
    void calculate_nullExpression_zero() {
        // given
        String expression = null;

        // when
        int actual = adder.calculate(expression);

        // then
        assertThat(actual).isZero();
    }
}