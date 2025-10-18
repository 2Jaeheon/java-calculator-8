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
    @DisplayName("support() 메서드는 //로 시작할 때만 true를 반환한다")
    void customDelimiterAlwaysStartWithDoubleSlash() {
        // given
        String expression = "//_\\n3_4";

        // when
        boolean support = delimiter.support(expression);

        // then
        assertThat(support).isEqualTo(true);
    }

    @Test
    @DisplayName("support() 메서드는 //로 시작하지 않으면 false를 반환한다")
    void customDelimiterMustStartWithDoubleSlash() {
        // given
        String expression = "1,2:3";

        // when
        boolean support = delimiter.support(expression);

        // then
        assertThat(support).isEqualTo(false);
    }

    @Test
    @DisplayName("getRegex() 메서드는 반드시 커스텀 구분자 정규표현식을 반환한다")
    void getRegexReturnAlwaysCustomDelimiter() {
        // given
        String expression = "//*\\n1*2*3";
        String expected = Pattern.quote("*");

        // when
        String regex = delimiter.getRegex(expression);

        // then
        assertThat(regex).isEqualTo(expected);
    }

    @DisplayName("getContent 메서드는 커스텀 구분자 선언부를 제외한 내용물을 반환한다")
    @Test
    void getContentReturnContent() {
        // given
        String expression = "//;\\n1;2;3";

        // when
        String content = delimiter.getContent(expression);

        // then
        assertThat(content).isEqualTo("1;2;3");
    }

    @Test
    @DisplayName("커스텀 구분자가 여러 개인 경우 예외를 반한한다")
    void getRegexReturnMultiCustomDelimiter() {
        // given
        String expression = "//;&^\\n1;2&3^5";

        // when & then
        assertThatThrownBy(() -> delimiter.getRegex(expression))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("커스텀 구분자는 한 글자여야 합니다.");

    }

    @Test
    @DisplayName("커스텀 구분자 형식이 잘못된 경우 예외를 발생한다.")
    void getRegexThrowsExceptionWhenWrongCustomDelimiter() {
        // given
        String expression = "//;*n1;2;3";

        // when & then
        assertThatThrownBy(() -> delimiter.getRegex(expression))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("잘못된 커스텀 구분자 형식입니다.");
    }

    @Test
    @DisplayName("커스텀 구분자가 비어있다면 예외를 발생한다.")
    void getRegexThrowsExceptionWhenEmptyCustomDelimiter() {
        // given
        String expression = "//\\n1;2;3";

        // when & then
        assertThatThrownBy(() -> delimiter.getRegex(expression))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("구분자는 비어있을 수 없습니다.");
    }
}