package calculator.application;

import calculator.domain.Calculator;
import calculator.view.Keyboard;
import calculator.view.Screen;

public class CalculateController {
    private final Calculator calculator;
    private final Keyboard keyboard;
    private final Screen screen;

    public CalculateController(Calculator calculator, Keyboard keyboard, Screen screen) {
        this.calculator = calculator;
        this.keyboard = keyboard;
        this.screen = screen;
    }

    public void run() {
        screen.display("덧셈할 문자열을 입력해 주세요.");
        String expression = keyboard.read();
        int result = calculator.calculate(expression);
        screen.display("결과 : " + result);
    }
}
