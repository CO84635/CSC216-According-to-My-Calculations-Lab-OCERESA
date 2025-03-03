import java.util.Stack;

public class Calculator {
    public static void main(String[] args) {
        Calculator calculator = new Calculator();
        // // Prints out the number 0
        System.out.println(calculator.evaluate("0"));

        // // Prints out the number 0
        System.out.println(calculator.evaluate("0 + 0"));

        // // Prints out the number 2
        System.out.println(calculator.evaluate("1 + 1"));

        // // Prints out the number 1
        System.out.println(calculator.evaluate("1 * 1"));

        // // Prints out the number 24
        System.out.println(calculator.evaluate("2 * 3 * 4"));

        // // Prints out the number 15
        System.out.println(calculator.evaluate("1 + 2 + 3 + 4 + 5"));

        // Prints out the number 7
        System.out.println(calculator.evaluate("2 + 5"));
    
        // Prints out the number 33
        System.out.println(calculator.evaluate("3 + 6 * 5"));
    
        // Prints out the number 20
        System.out.println(calculator.evaluate("4 * ( 2 + 3 )"));
    
        // Prints out the number 2
        System.out.println(calculator.evaluate("( 7 + 9 ) / 8"));

        // Prints out the number 2.0E9
        System.out.println(calculator.evaluate("1000000000 + 1000000000"));

    }

    static Double evaluate(String infix) {
        String postfix = infixToPostfix(infix);

        return evaluatePostfix(postfix);
    }

    static String infixToPostfix(String infix) {
        /* To find out the precedence, we take the index of the
           token in the ops string and divide by 2 (rounding down). 
           This will give us: 0, 0, 1, 1, 2 */
        final String ops = "-+/*^";

        StringBuilder sb = new StringBuilder();
        Stack<Integer> s = new Stack<>();

        for (String token : infix.split("\\s")) {
            if (token.isEmpty())
                continue;
            char c = token.charAt(0);
            int idx = ops.indexOf(c);

            // check for operator
            if (idx != -1) {
                if (s.isEmpty())
                    s.push(idx);
          
                else {
                    while (!s.isEmpty()) {
                        int prec2 = s.peek() / 2;
                        int prec1 = idx / 2;
                        if (prec2 > prec1 || (prec2 == prec1 && c != '^'))
                            sb.append(ops.charAt(s.pop())).append(' ');
                        else break;
                    }
                    s.push(idx);
                }
            } 
            else if (c == '(') {
                s.push(-2); // -2 stands for '('
            } 
            else if (c == ')') {
                // until '(' on stack, pop operators.
                while (s.peek() != -2)
                    sb.append(ops.charAt(s.pop())).append(' ');
                s.pop();
            }
            else {
                sb.append(token).append(' ');
            }
        }
        while (!s.isEmpty())
            sb.append(ops.charAt(s.pop())).append(' ');
        return sb.toString();
    }

    static Double evaluatePostfix(String input) {
        String[] postfix = input.split(" ");
        Stack<Double> evStack = new Stack();

        for (String token : postfix) {

            if (token.matches("-?\\d+")) {
                evStack.push(Double.parseDouble(token));
            }

            else {
                Double valueOne = evStack.pop();
                Double valueTwo = evStack.pop();

                switch(token) {
                    case "+":
                        evStack.push(valueTwo + valueOne);
                        break;
                    case "-":
                        evStack.push(valueTwo - valueOne);
                        break;
                    case "*":
                        evStack.push(valueTwo * valueOne);
                        break;
                    case "/":
                        evStack.push(valueTwo / valueOne);
                        break;
                    
                }
            }
        }

        return evStack.pop();
    }
}
