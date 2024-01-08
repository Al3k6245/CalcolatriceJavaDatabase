package formPackage;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class FrmCalculator {
    private JPanel Panel;
    private JButton btn7;
    private JButton btn4;
    private JButton btn8;
    private JButton btn9;
    private JButton btn5;
    private JButton btn6;
    private JButton btn2;
    private JButton btn3;
    private JButton btn1;
    private JButton btn0;
    private JButton btnPlus;
    private JButton btnMinus;
    private JButton button1;
    private JButton xButton;
    private JButton btnDivide;
    private JButton btnCanc;
    private JTextPane tpnOutput;
    private JButton btnOpenParentesis;
    private JButton btnClosedParentesis;
    private JButton btnComma;


    public FrmCalculator() {

        ActionListener listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JButton sourceButton = (JButton) e.getSource();

                WriteOnButtonClick(sourceButton.getText().charAt(0));
            }
        };
        btn4.addActionListener(listener);
        btn8.addActionListener(listener);
        btn9.addActionListener(listener);
        btn6.addActionListener(listener);
        btn2.addActionListener(listener);
        btn3.addActionListener(listener);
        btn1.addActionListener(listener);
        btn0.addActionListener(listener);
        btnPlus.addActionListener(listener);
        btnMinus.addActionListener(listener);
        xButton.addActionListener(listener);
        btnDivide.addActionListener(listener);
        btn5.addActionListener(listener);
        btn7.addActionListener(listener);
        btnOpenParentesis.addActionListener(listener);
        btnClosedParentesis.addActionListener(listener);
        btnComma.addActionListener(listener);
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TransformInRPN(tpnOutput.getText());
            }
        });
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String result = String.valueOf(Calculate(TransformInRPN(tpnOutput.getText())));
                String rpn = GetRpnInString(TransformInRPN(tpnOutput.getText()));
                tpnOutput.setText(result + "\n" + rpn);
            }
        });
        btnCanc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tpnOutput.setText(tpnOutput.getText().substring(0,tpnOutput.getText().length()-1));
            }
        });
    }

    public static void main() {
        JFrame frame = new JFrame("FrmCalculator");
        frame.setContentPane(new FrmCalculator().Panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    void WriteOnButtonClick(char input){
        tpnOutput.setText(tpnOutput.getText().concat(String.valueOf(input)));
    }

    List<String> TransformInRPN(String expression){
        StringBuilder number = new StringBuilder();
        List<String> rpnExpression = new ArrayList<>();
        Stack<String> operators = new Stack<String>();

        for(int i=0;i<expression.length();i++){

            if(expression.charAt(i)>='0' && expression.charAt(i)<='9' || expression.charAt(i)=='.'){
                number.append(expression.charAt(i));
            }
            else{
                if(!number.toString().isEmpty())
                    rpnExpression.add(String.valueOf(number));

                number = new StringBuilder();

                if(GetOperatorValue(expression.charAt(i)) == 4){  //se trova una parentesi chiusa

                    while(!Objects.equals(operators.peek(), "(")){
                        rpnExpression.add(operators.pop());
                    }

                    operators.pop();
                }
                else if(operators.isEmpty() || GetOperatorValue(expression.charAt(i)) > GetOperatorValue(operators.peek().charAt(0))){
                    operators.push(String.valueOf(expression.charAt(i)));
                }
                else if(GetOperatorValue(expression.charAt(i)) <= GetOperatorValue(operators.peek().charAt(0)) ){

                    if(!Objects.equals(operators.peek(), "(")){

                        do{
                            rpnExpression.add(operators.pop());
                        }while(!operators.isEmpty() && GetOperatorValue(expression.charAt(i)) <= GetOperatorValue(operators.peek().charAt(0)));
                    }

                    operators.push(String.valueOf(expression.charAt(i)));
                }
            }
        }

        if(!number.toString().isEmpty())
            rpnExpression.add(String.valueOf(number));

        while (!operators.isEmpty())
            rpnExpression.add(operators.pop());

        return rpnExpression;
    }

    int GetOperatorValue(char operator){
        if(operator == ')')
            return 4;
        if(operator == '(')
            return 3;
        if(operator == 'x' || operator == '/')
            return 2;
        if(operator == '-' || operator == '+')
            return 1;

        return -1;  //nel caso in cui non viene inserito un operatore previsto
    }

    String GetRpnInString(List<String> rpnExp){
        String rpn = "";

        for (int i=0;i<rpnExp.size();i++){
            rpn += rpnExp.get(i);
            rpn += " ";
        }

        return rpn;
    }

    double Calculate(List<String> rpnExp) {
        double result = 0;
        double tmpNumber = 0;

        Stack<Double> numbers = new Stack<>();

        for(int i=0;i<rpnExp.size();i++){

            if (rpnExp.get(i).matches("[0-9]+"))
                numbers.add(Double.valueOf(rpnExp.get(i)));
            else{
                tmpNumber = numbers.pop();

                switch (rpnExp.get(i)) {

                    case "+":
                        result = tmpNumber + numbers.pop();
                        break;

                    case "-":
                        result = (numbers.pop() - tmpNumber);
                        break;

                    case "x":
                        result = tmpNumber * numbers.pop();
                        break;

                    case "/":
                        result = numbers.pop() / tmpNumber;
                        break;
                }

                numbers.push(result);
            }
        }

        return numbers.pop();
    }

}
