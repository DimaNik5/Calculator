package calculator;

import java.util.LinkedList;

public class Operand {
    private LinkedList<Operand> operands;
    private LinkedList<Operator> operators;

    private String input;
    private boolean isCorrect = true;

    private enum Type{
        NUMBER, BRACKETS, ROOT
    }

    public boolean isCorrect(){
        return isCorrect;
    }

    private Type type;

    public void setContent(String in){
        if(in.isEmpty()){
            isCorrect = false;
            return;
        }
        char[] str = in.toCharArray();
        if(in.indexOf("-") == 0){
            if(endOperand(str, 1) == str.length){
                type = Type.NUMBER;
                input = in;
                return;
            }
            else {
                in = '0' + in;
                str = in.toCharArray();
            }
        }
        else if(in.indexOf("√") == 0 && endOperand(str, 0) == str.length){
            Operand o = new Operand();
            o.setContent(in.substring(1));
            operands = new LinkedList<>();
            operands.add(o);
            type = Type.ROOT;
            return;
        }
        else if (in.indexOf("(") == 0 && endOperand(str, 0) == str.length) {
            str = in.substring(1, in.length() - 1).toCharArray();
        }
        if(!isCorrect) return;

        if(endOperand(str, 0) == str.length){
            type = Type.NUMBER;
            input = new String(str);
            return;
        }

        type = Type.BRACKETS;
        operands = new LinkedList<>();
        operators = new LinkedList<>();
        int i = 0;
        int j;
        while(true){
            j = endOperand(str, i);
            if(j == -1){
                isCorrect = false;
                break;
            }
            Operand o = new Operand();
            o.setContent((new String(str)).substring(i, j));
            operands.add(o);
            if(j == str.length) break;
            operators.add(new Operator(String.valueOf(str[j++])));
            if(j == str.length) {
                isCorrect = false;
                break;
            }
            i = j;
            if(!isCorrect) break;
        }

    }

    private int endOperand(char[] str, int i){
        if(i >= str.length){
            isCorrect = false;
            return -1;
        }
        if(str[i] == '√'){
            return endOperand(str, i + 1);
        }
        if(str[i] == '('){
            int extraBrackets = 0;
            for (int j = i + 1; j < str.length; j++) {
                if(str[j] == '('){
                    extraBrackets++;
                    continue;
                }
                if(str[j] == ')'){
                    if(extraBrackets > 0){
                        extraBrackets--;
                        continue;
                    }
                    return j + 1; // TODO + 1?
                }
                if(!"0123456789.*/+-%^√".contains(String.valueOf(str[j]))){
                    isCorrect = false;
                    return -1;
                }
            }
        }
        int j = i;
        while (j < str.length && "0123456789.".contains(String.valueOf(str[j]))) j++;
        if(j == i){
            isCorrect = false;
        }

        if(j == str.length || ")*/+-^%".contains(String.valueOf(str[j]))) return j;
        isCorrect = false;
        return -1;
    }

    public double getValue(){
        if(!isCorrect) return 0;
        double res = 0;
        if(type.equals(Type.NUMBER)){
            try{
                res = Double.parseDouble(input);
            }catch (Exception e){
                isCorrect = false;
            }
            return res;
        }
        if(type.equals(Type.ROOT)){
            try{
                res = Math.sqrt(operands.getFirst().getValue());
                isCorrect = operands.getFirst().isCorrect;
            }catch (Exception e){
                isCorrect = false;
            }
            return res;
        }

        // * / % ^
        int i = 0;
        do {
            if ("*/%^".contains(operators.get(i).getOperator())) {
                Operand o = new Operand();
                o.setContent(String.valueOf(operators.get(i).calculation(operands.get(i).getValue(), operands.get(i + 1).getValue())));
                isCorrect = operands.get(i).isCorrect && operands.get(i + 1).isCorrect && operators.get(i).isCorrect();
                operators.remove(i);
                operands.add(i, o);
                operands.remove(i + 1);
                operands.remove(i + 1);
            }
            else i++;
        } while (i < operators.size());
        // + -
        i = 0;
        while (i < operators.size()) {
            Operand o = new Operand();
            double answer = operators.get(i).calculation(operands.get(i).getValue(), operands.get(i + 1).getValue());
            o.setContent(String.valueOf(answer));
            isCorrect = operands.get(i).isCorrect && operands.get(i + 1).isCorrect && operators.get(i).isCorrect();
            operators.remove(i);
            operands.add(i, o);
            operands.remove(i + 1);
            operands.remove(i + 1);
            //i++;
        }
        return operands.getFirst().getValue();
    }
}
