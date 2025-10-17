package calculator.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SplitterTest {

    DelimiterFactory delimiterFactory;
    Splitter splitter;

    @BeforeEach
    void setUp() {
        delimiterFactory = new DelimiterFactory();
        splitter = new Splitter(delimiterFactory);
    }

    @Test
    @DisplayName("커스텀 표현식은 커스텀 구분자로 분리해야 한다")
    void splitFromCustomDelimiter() {
        // given
        String expression = "//*\n7*2*3";

        // when
        String[] split = splitter.split(expression);

        // then
        assertThat(split).isEqualTo(new String[]{"7", "2", "3"});
    }

    @Test
    @DisplayName("기본 표현식은 기본 구분자로 분리해야 한다")
    void splitFromBasicDelimiter() {
        // given
        String expression = "1,2:3";

        // when
        String[] split = splitter.split(expression);

        // then
        assertThat(split).isEqualTo(new String[]{"1", "2", "3"});
    }

    @Test
    @DisplayName("복합 표현식은 복합 구분자로 분리해야 한다")
    void splitFromComplexDelimiter() {
        // given
        String expression = "//*\n7*2,3";

        // when
        String[] split = splitter.split(expression);

        // then
        assertThat(split).isEqualTo(new String[]{"7", "2", "3"});
    }

    @Test
    @DisplayName("중복 커스텀 구분자의 경우 분리되어야 한다")
    void splitDuplicatedCustomDelimiter() {
        // given
        String expression = "//**\n7*2*3";

        // when
        String[] split = splitter.split(expression);

        // then
        assertThat(split).isEqualTo(new String[]{"7", "2", "3"});
    }

    @Test
    @DisplayName("커스텀 구분자가 여러개인 경우 각 구분자로 분리되어야 한다")
    void splitMultipleCustomDelimiter() {
        // given
        String expression = "//^&*\n7^2&3*4";

        // when
        String[] split = splitter.split(expression);

        // then
        assertThat(split).isEqualTo(new String[]{"7", "2", "3", "4"});
    }

    @Test
    @DisplayName("복합 구분자의 경우 각 구분자로 분리되어야 한다")
    void splitComplexDelimiter() {
        // given
        String expression = "//*\n7*2,3";

        // when
        String[] split = splitter.split(expression);

        // then
        assertThat(split).isEqualTo(new String[]{"7", "2", "3"});
    }
}