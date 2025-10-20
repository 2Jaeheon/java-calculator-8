package calculator.domain.delimiter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

import java.util.regex.Pattern;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CustomDelimiterTest {

    Delimiter delimiter;

    @BeforeEach
    void setUp() {
        delimiter = new CustomDelimiter();
    }

    @Test
    @DisplayName("support() 메서드는 //로 시작할 때 true를 반환한다.")
    void support_startWithDoubleSlash_true() {
        // given
        String expression = "//_\\n3_4";

        // when
        boolean support = delimiter.support(expression);

        // then
        assertThat(support).isEqualTo(true);
    }

    @Test
    @DisplayName("support() 메서드는 //로 시작하지 않으면 false를 반환한다.")
    void support_notStartWithDoubleSlash_false() {
        // given
        String expression = "1,2:3";

        // when
        boolean support = delimiter.support(expression);

        // then
        assertThat(support).isEqualTo(false);
    }

    @Test
    @DisplayName("tokenize()는 끝에 구분자가 있으면 빈 토큰을 포함해야 한다.")
    void tokenize_trailingDelimiter_includesEmptyToken() {
        // given
        String expression = "//*\\n1*2*";
        String[] expected = {"1", "2", ""};

        // when
        String[] actual = delimiter.tokenize(expression);

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("tokenize()는 문자열을 커스텀 구분자로 분리해서 반환해야 한다.")
    void tokenize_customDelimiter() {
        // given
        String expression = "//*\\n1*3*5";
        String[] expected = new String[]{"1", "3", "5"};

        // when
        String[] tokenize = delimiter.tokenize(expression);

        // then
        assertThat(tokenize).isEqualTo(expected);
    }

    @Test
    @DisplayName("tokenize()는 커스텀 구분자가 없는 경우 예외를 발생해야 한다")
    void tokenize_emptyDelimiter_throws() {
        // given
        String expression = "//\\n3_4";

        // when & then
        assertThatThrownBy(() -> delimiter.tokenize(expression))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("구분자는 비어있을 수 없습니다.");
    }

    @Test
    @DisplayName("tokenize()는 커스텀 구분자가 하나가 아닌 경우 예외를 발생해야 한다")
    void tokenize_multiCharacterDelimiter_throws() {
        // given
        String expression = "//**\\n3*4";

        // when & then
        assertThatThrownBy(() -> delimiter.tokenize(expression))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("커스텀 구분자는 한 글자여야 합니다.");
    }

    @Test
    @DisplayName("tokenize()는 커스텀 구분자의 형식이 잘못된 경우 예외를 발생해야 한다")
    void tokenize_wrongDelimiterFormat_throws() {
        // given
        String expression = "//*\n3*4";

        // when & then
        assertThatThrownBy(() -> delimiter.tokenize(expression))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("잘못된 커스텀 구분자 형식입니다.");
    }

    @Test
    @DisplayName("tokenize()는 커스텀 구분자가 숫자인 경우 예외를 발생해야 한다")
    void tokenize_delimiterIsNumber_throws() {
        // given
        String expression = "//2\\n524";

        // when
        assertThatThrownBy(() -> delimiter.tokenize(expression))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("숫자는 구분자로 사용할 수 없습니다.");
    }
}