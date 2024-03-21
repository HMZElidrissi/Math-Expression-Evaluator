# Math Expression Evaluator

This is a simple math expression evaluator that can evaluate simple math expressions. It can support the following:

- Basic arithmetic operations (+, -, *, /) on integers and doubles.

- Common math functions: cos, acos, sin, asin, tan, atan, sqrt, pow.

## How does it work?

1. First, the expression is converted to an array of characters.
2. Then, the operands, operators, and functions are extracted from the expression, and each is stored in a stack.
3. A `while` loop is used to iterate through each character in the expression array.
4. If the character is an opening parenthesis, it is pushed to the operators stack.
5. If the character is a digit or a decimal point, a StringBuilder is used to accumulate the entire number.
6. If the character is alphabetic, it is considered a function name, and the function name is stored in a StringBuilder.
7. If the character is an operator, it checks the precedence of the operator against the top operator in the stack.
8. It keeps popping operators from the stack until the precedence of the current operator is greater than the top operator in the stack.
9. If the character is a closing parenthesis, it keeps popping operators from the stack until an opening parenthesis is found.
10. If the character is the end of the expression, it keeps popping operators from the stack until the stack is empty.
11. The result is then printed to the console.

## How to run?

This project requires a Java compiler (JDK) installed on your system.

1. Clone the repository to your local machine.
```bash
git clone https://github.com/HMZElidrissi/Math-Expression-Evaluator.git
```

2. Navigate to the project directory and compile the source code
```bash
cd Math-Expression-Evaluator
javac src/*.java
```

3. Run the program
```bash
java -cp src MathExpressionEvaluator <expression>
```

Example:
```bash
java -cp src MathExpressionEvaluator "1 + (2 * 3 - 10.5)"
```

This will output:
```
Result: -3.5
```