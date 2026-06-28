/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package polish.notation;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 *
 * @author bli
 */
public class Expression {

    private String input;
    private List<Token> infixTokens;
    private List<Token> postfixTokens;

    public Expression(String input) {
        this.input = input;
    }

    public int evaluate() throws ExpressionProcessingException {
        if (infixTokens == null) {
            tokenizeInput();
        }
        if (postfixTokens == null) {
            convertToPostfix();
        }
        return evaluateExpression();
    }

    public void tokenizeInput() throws ExpressionProcessingException {
        this.infixTokens = new ArrayList<>();

        try {
            while (!input.isEmpty()) {
                Token.TokenResult result = Token.readToken(input);
                Token token = result.getToken();

                if (token != null) {
                    infixTokens.add(token);
                }

                input = result.getRemainingInput();
            }
        } catch (Token.IllegalElementException ex) {
            throw new ExpressionProcessingException("Illegal character: " + ex.what());
        }

        if (infixTokens.isEmpty()) {
            throw new ExpressionProcessingException("Empty expression");
        }
    }

    public void convertToPostfix() throws ExpressionProcessingException {
        Stack<Token> operatorStack = new Stack<>();
        this.postfixTokens = new ArrayList<>();

        for (Token token : infixTokens) {
            switch (token) {
                case Operand operand ->
                    postfixTokens.add(operand);
                case LeftP leftP ->
                    operatorStack.push(leftP);
                case RightP rightP -> {
                    try {
                        while (!(operatorStack.peek() instanceof LeftP)) {
                            postfixTokens.add(operatorStack.pop());
                        }
                        operatorStack.pop(); // Discard the left parenthesis
                    } catch (Exception e) {
                        throw new ExpressionProcessingException("Syntax error: less left parenthesis than right ones");
                    }
                }
                case Operator currentOperator -> {
                    while (!operatorStack.empty()
                            && operatorStack.peek() instanceof Operator
                            && currentOperator.priority() <= ((Operator) operatorStack.peek()).priority()) {
                        postfixTokens.add(operatorStack.pop());
                    }
                    operatorStack.push(token);
                }
                default -> {
                    throw new ExpressionProcessingException("Syntax error: unrecognized token");
                }
            }
        }

        // Pop any remaining operators from the stack
        while (!operatorStack.empty()) {
            if (operatorStack.peek() instanceof LeftP) {
                throw new ExpressionProcessingException("Syntax error: more left parentheses than right ones");
            } else {
                postfixTokens.add(operatorStack.pop());
            }
        }

    }

    public int evaluateExpression() throws ExpressionProcessingException {
        Stack<Integer> valueStack = new Stack<>();

        for (Token token : postfixTokens) {
            switch (token) {
                case Operand operand ->
                    valueStack.push(operand.value());
                case Operator operator -> {
                    if (valueStack.size() < 2) {
                        throw new ExpressionProcessingException("Not enough operands");
                    }

                    int rightOperand = valueStack.pop();
                    int leftOperand = valueStack.pop();
                    valueStack.push(operator.evaluate(leftOperand, rightOperand));
                }
                default ->
                    throw new ExpressionProcessingException("Unknown token type");
            }
        }

        if (valueStack.size() != 1) {
            throw new ExpressionProcessingException("Syntax error: more operands than operators");
        }

        return valueStack.pop();
    }
}
