package calculator;

import java.awt.*;

public class MyTextField extends Component{
    private int x;
    private int y;
    private int h;
    private int w;
    private String s;

    public MyTextField(int x, int y, int h, int w) {
        this.x = x;
        this.y = y;
        this.h = h;
        this.w = w;
        s = "";
    }

    @Override
    public void paint(Graphics g){
        super.paint(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.GRAY);
        g2.setColor(Color.BLACK);
        String t;
        if(s.length() > 18){
            t = s.substring(s.length() - 18);
        }
        else t = s;
        g2.drawString(t, x + 2, y + h / 2);

    }

    public boolean isArea(int x, int y){
        return (this.x <= x && this.y <= y && this.x + w >= x && this.y + h >= y);
    }

    public String getContent(){
        return s;
    }

    public void setContent(String c){
        s = c;
    }

    public void delLast(){
        if(!s.isEmpty()){
            s = s.substring(0, s.length() - 1);
        }
    }

    public void delAll(){
        s = "";
    }

    public void addContent(String c){
        s += c;

    }
}
