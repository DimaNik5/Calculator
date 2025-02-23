import calculator.Calculator;

import javax.swing.*;

public static void main() {
    JFrame frame = new JFrame("Калькулятор");
    Calculator calculator = new Calculator();
    frame.add(calculator);
    frame.pack();
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);
    frame.setResizable(false);
}