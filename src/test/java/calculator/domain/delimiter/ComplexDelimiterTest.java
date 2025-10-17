package calculator.domain.delimiter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.regex.Pattern;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ComplexDelimiterTest {

    Delimiter delimiter;

    @BeforeEach
    void setUp() {
        delimiter = new ComplexDelimiter();
    }

    @Test
    @DisplayName("support() 메서드는 커스텀 구분자와 기본 구분자 모두를 가지는 경우 true를 반한한다")
    void supportAlwaysHaveCustomDelimiterAndBasicDelimiter() {
        // given
        String expression = "//*^\n1:2*3,4^";

        // when
        boolean support = delimiter.support(expression);

        // then
        assertThat(support).isEqualTo(true);
    }

    @Test
    @DisplayName("support() 메서드는 커스텀 구분자만 존재하는 경우 false를 반환한다")
    void supportShouldReturnFalseWhenOnlyCustomDelimiter() {
        // given
        String expression = "//*\n1*3*4";

        // when
        boolean support = delimiter.support(expression);

        // then
        assertThat(support).isEqualTo(false);
    }

    @Test
    @DisplayName("support() 메서드는 기본 구분자만 존재하는 경우 false를 반환한다")
    void supportShouldReturnFalseWhenBasicDelimiter() {
        // given
        String expression = "1:2:3,4";

        // when
        boolean support = delimiter.support(expression);

        // then
        assertThat(support).isEqualTo(false);
    }

    @Test
    @DisplayName("getRegex() 메서드는 기본 구분자와 커스텀 구분자를 합친 정규표현식을 반환한다")
    void getRegexReturnAlwaysCustomAndBasicDelimiter() {
        // given
        String expression = "//*^\n1:2*3,4";
        String expectedRegex = Pattern.quote("*") + "|" + Pattern.quote("^") + "|,|:";

        // when
        String regex = delimiter.getRegex(expression);

        // then
        assertThat(regex).isEqualTo(expectedRegex);
    }

    @Test
    @DisplayName("getRegex() 메서드는 중복된 커스텀 구분자가 존재하는 경우 중복을 제거한 정규표현식을 반환한다")
    void getRegexRemoveDuplicatedCustomDelimiter() {
        // given
        String expression = "//***^^^\n1:2*3,4";
        String expectedRegex = Pattern.quote("*") + "|" + Pattern.quote("^") + "|,|:";

        // when
        String regex = delimiter.getRegex(expression);

        // then
        assertThat(regex).isEqualTo(expectedRegex);
    }

    @Test
    @DisplayName("getContent() 메서드는 커스텀 구분자 부분을 제외한 내용물을 반환한다")
    void getContentReturnContent() {
        // given
        String expression = "//;\n1;2,3:4";

        // when
        String content = delimiter.getContent(expression);

        // then
        assertThat(content).isEqualTo("1;2,3:4");
    }
}