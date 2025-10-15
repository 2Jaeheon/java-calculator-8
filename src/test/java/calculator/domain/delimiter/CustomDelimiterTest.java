package calculator.domain.delimiter;

import static org.assertj.core.api.Assertions.assertThat;
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
        String expression = "//_\n3_4";

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
        String expression = "//*\n1*2*3";

        // when
        String regex = delimiter.getRegex(expression);

        // then
        assertThat(regex).isEqualTo("[*]");
    }

    @Test
    @DisplayName("getRegex 메서드는 여러 문자 구분자를 반환한다")
    void getRegexReturnMultiCustomDelimiter() {
        // given
        String expression = "//;&^\n1;2&3^5";

        // when
        String regex = delimiter.getRegex(expression);

        // then
        assertThat(regex).isEqualTo("[;&^]");
    }

    @Test
    @DisplayName("getRegex 메서드는 중복된 구분자를 제거한 정규표현식을 반환한다")
    void getRegexRemoveDuplicatedCustomDelimiter() {
        // given
        String expression = "//***^^^\n1^2*3*4";

        // when
        String regex = delimiter.getRegex(expression);

        // then
        assertThat(regex).isEqualTo("[*^]");
    }

    @DisplayName("getContent 메서드는 커스텀 구분자 선언부를 제외한 내용물을 반환한다")
    @Test
    void getContentReturnContent() {
        // given
        String expression = "//;\n1;2;3";

        // when
        String content = delimiter.getContent(expression);

        // then
        assertThat(content).isEqualTo("1;2;3");
    }
}