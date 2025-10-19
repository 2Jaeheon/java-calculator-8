package calculator.application;

import calculator.domain.Adder;
import calculator.domain.Calculator;
import calculator.domain.DelimiterFactory;
import calculator.domain.Splitter;
import calculator.view.Keyboard;
import calculator.view.Screen;

public class Application {
    public static void main(String[] args) {
        DelimiterFactory delimiterFactory = new DelimiterFactory();
        Splitter splitter = new Splitter(delimiterFactory);
        Calculator calculator = new Adder(splitter);

        Keyboard keyboard = new Keyboard();
        Screen screen = new Screen();

        CalculateController controller = new CalculateController(calculator, keyboard, screen);
        controller.run();
    }
}
