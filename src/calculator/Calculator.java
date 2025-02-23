package calculator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.LinkedList;

public class Calculator extends JPanel {
    private static final String[] CONTENT = {
            "H", "C", "<", "(", ")",
            "7", "8", "9", "*", "/",
            "4", "5", "6", "+", "-",
            "1", "2", "3", "%", "^",
            "A", "0", ".", "=", "√"
    };
    private static final int WIDTH_CONTENT = 5;
    private static final int WIDTH_BUTTON = 70;
    private static final int HEIGHT_BUTTON = 70;
    private static final int WIDTH_SPACE = 7;
    private static final int HEIGHT_SPACE = 7;
    private static final int WIDTH_TEXT_AREA = (WIDTH_BUTTON + WIDTH_SPACE) * WIDTH_CONTENT - WIDTH_SPACE;
    private static final int HEIGHT_TEXT_AREA = 100;

    private final MyButton[] buttons;
    private final MyTextField textField;

    LinkedList<String> history;
    private int numHistory = 0;

    private final Parser parser;

    public Calculator(){
        setPreferredSize(new Dimension((WIDTH_BUTTON + WIDTH_SPACE) * WIDTH_CONTENT + WIDTH_SPACE,
                HEIGHT_TEXT_AREA + HEIGHT_SPACE * (CONTENT.length / WIDTH_CONTENT + 2) + HEIGHT_BUTTON * (CONTENT.length / WIDTH_CONTENT)));
        setFocusable(true);

        parser = new Parser();

        textField = new MyTextField(WIDTH_SPACE, HEIGHT_SPACE, HEIGHT_TEXT_AREA, WIDTH_TEXT_AREA);
        buttons = new MyButton[CONTENT.length];
        for (int i = 0; i < CONTENT.length; i++) {
            buttons[i] = new MyButton(WIDTH_SPACE + (WIDTH_SPACE + WIDTH_BUTTON) * (i % WIDTH_CONTENT),
                    HEIGHT_SPACE * 2 + HEIGHT_TEXT_AREA + (HEIGHT_SPACE + HEIGHT_BUTTON) * (i / WIDTH_CONTENT),
                    HEIGHT_BUTTON, WIDTH_BUTTON, CONTENT[i]);
        }

        history = new LinkedList<>();


        this.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                for (MyButton button : buttons) {
                    if (button.isArea(e.getX(), e.getY())) {
                        input(button.getS().charAt(0));
                    }
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        this.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                input(e.getKeyChar());
            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
    }

    @Override
    public void paint(Graphics g){
        super.paint(g);
        g.setFont(new Font("Sans", Font.BOLD, 30));
        textField.paint(g);
        for (MyButton button : buttons) {
            button.paint(g);
        }
    }

    private void input(char c) {
        String s = String.valueOf(c);
        if ("0123456789+-*/%^√.()".contains(s)) {
            textField.addContent(s);
        } else {
            switch (c) {
                case 8:
                case '<':
                    textField.delLast();
                    break;
                case 'C':
                    textField.delAll();
                    break;
                case 'H':
                    if (!history.isEmpty()) {
                        if (numHistory < history.size()) {
                            textField.setContent(history.get(numHistory++));
                        } else {
                            textField.delAll();
                            numHistory = 0;
                        }
                    }
                    break;
                case 'A':
                    textField.addContent(parser.getLastAnswer());
                    break;
                case 10:
                case '=':
                    parser.setString(textField.getContent());
                    history.addFirst(textField.getContent());
                    if (history.size() > 15) {
                        history.removeLast();
                    }
                    textField.setContent(parser.getLastAnswer());
                    break;
            }
        }
        repaint(WIDTH_SPACE, HEIGHT_SPACE, WIDTH_TEXT_AREA, HEIGHT_TEXT_AREA);

    }
}
