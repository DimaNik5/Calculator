package calculator;

import java.awt.*;
public class MyButton extends Component {
    private final int x;
    private final int y;
    private final int h;
    private final int w;
    private final String s;

    public MyButton(int x, int y, int h, int w, String s) {
        this.x = x;
        this.y = y;
        this.h = h;
        this.w = w;
        this.s = s;

    }

    @Override
    public void paint(Graphics g){
        super.paint(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.GRAY);
        g2.fillRect(x, y, w, h);
        g2.setColor(Color.BLACK);
        g2.drawString(s, x + w / 2 - 10, y + h /2 + 10);
    }

    public boolean isArea(int x, int y){
        return (this.x <= x && this.y <= y && this.x + w >= x && this.y + h >= y);
    }

    public String getS(){
        return s;
    }
}
