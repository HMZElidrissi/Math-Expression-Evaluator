import java.util.Scanner;
import java.util.Stack;

public class MathExpressionEvaluator {
    private static boolean isOperator(char ch) {
        return ch == '+' || ch == '-' || ch == '*' || ch == '/';
    }

    private static boolean isFunction(String func) {
        return func.equals("sin") || func.equals("cos") || func.equals("tan") || func.equals("asin") || func.equals("acos") || func.equals("atan") || func.equals("sqrt") || func.equals("pow");
    }

    private static boolean hasPrecedence(char op1, char op2) {
        if (op2 == '(' || op2 == ')') {
            return false;
        }
        return (op1 != '*' && op1 != '/') || (op2 != '+' && op2 != '-');
    }

    public static double evaluate(String expression) {
        char[] inputs = expression.toCharArray();

        Stack<Double> operands = new Stack<>();
        Stack<Character> operators = new Stack<>();
        Stack<String> functions = new Stack<>();

        int i = 0;
        while (i < inputs.length) {
            if (inputs[i] == ' ') {
                i++;
                continue;
            }

            if (inputs[i] == '(') {
                operators.push(inputs[i]);
            } else if (Character.isDigit(inputs[i]) || inputs[i] == '.') {
                StringBuilder sb = new StringBuilder();
                while (i < inputs.length && (Character.isDigit(inputs[i]) || inputs[i] == '.')) {
                    sb.append(inputs[i++]);
                }
                operands.push(Double.parseDouble(sb.toString()));
                i--;
            } else if (Character.isAlphabetic(inputs[i])) {
                StringBuilder sb = new StringBuilder();
                while (i < inputs.length && Character.isAlphabetic(inputs[i])) {
                    sb.append(inputs[i++]);
                }
                String funcName = sb.toString();
                if (isFunction(funcName)) {
                    functions.push(funcName);
                } else {
                    throw new IllegalArgumentException("Unknown function: " + funcName);
                }
                i--;
            } else if (isOperator(inputs[i])) {
                while (!operators.isEmpty() && hasPrecedence(inputs[i], operators.peek())) {
                    applyOperator(operands, operators);
                }
                operators.push(inputs[i]);
            } else if (inputs[i] == ')') {
                while (!operators.isEmpty() && operators.peek() != '(') {
                    applyOperator(operands, operators);
                }
                operators.pop();
                if (!functions.isEmpty()) {
                    String functionName = functions.pop();
                    double arg = operands.pop();
                    double result = switch (functionName) {
                        case "sin" -> Math.sin(arg);
                        case "cos" -> Math.cos(arg);
                        case "tan" -> Math.tan(arg);
                        case "asin" -> Math.asin(arg);
                        case "acos" -> Math.acos(arg);
                        case "atan" -> Math.atan(arg);
                        case "sqrt" -> Math.sqrt(arg);
                        case "pow" -> Math.pow(arg, 2);
                        default -> throw new IllegalArgumentException("Unknown function: " + functionName);
                    };
                    operands.push(result);
                }
            } else {
                throw new IllegalArgumentException("Malformed expression");
            }
            i++;
        }

        while (!operators.isEmpty()) {
            applyOperator(operands, operators);
        }

        if (operands.size() != 1) {
            throw new IllegalArgumentException("Malformed expression");
        }

        return operands.pop();
    }

    private static void applyOperator(Stack<Double> operands, Stack<Character> operators) {
        double right = operands.pop();
        double left = operands.pop();
        char op = operators.pop();
        if (op == '+') {
            operands.push(left + right);
        } else if (op == '-') {
            operands.push(left - right);
        } else if (op == '*') {
            operands.push(left * right);
        } else if (op == '/') {
            if (right == 0) {
                throw new ArithmeticException("Division by zero");
            }
            operands.push(left / right);
        }
    }

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Usage: java MathExpressionEvaluator <expression>");
            return;
        }
        try {
            double result = evaluate(args[0]);
            System.out.println("Result: " + result);
        } catch (IllegalArgumentException | ArithmeticException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Malformed expression");
        }
    }
}
