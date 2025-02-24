package calculator;

import java.util.function.DoubleBinaryOperator;

public class Operator {
    private final String operator;
    private boolean isCorrect = true;
    private DoubleBinaryOperator operation;

    public Operator(String s){
        operator = s;
        switch (s){
            case "*":
                operation = (left, right) -> left * right;
                break;
            case "/":
                operation = (left, right) -> left / right;
                break;
            case "+":
                operation = Double::sum;
                break;
            case "-":
                operation = (left, right) -> left - right;
                break;
            case "^":
                operation = Math::pow;
                break;
            case "%":
                operation = (left, right) -> left % right;
                break;
        }

    }

    public String getOperator(){
        return operator;
    }

    public boolean isCorrect(){
        return isCorrect;
    }

    public double calculation(double a, double b){
        double res = 0;
        try{
            res = operation.applyAsDouble(a, b);
        }catch (Exception e){
            isCorrect = false;
        }
        if(Double.isNaN(res)) isCorrect = false;
        return res;
    }
}
