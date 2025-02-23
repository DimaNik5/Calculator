package calculator;

public class Parser {

    private boolean isCorrect = false;
    private double lastAnswer = 0;

    public void setString(String s){
        isCorrect = true;
        Operand operand = new Operand();
        operand.setContent(s);
        lastAnswer = operand.getValue();
        isCorrect = operand.isCorrect();
    }

    public String getLastAnswer(){
        if(isCorrect) return String.valueOf(lastAnswer);
        return "Error";
    }
}
