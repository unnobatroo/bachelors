/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package polish.notation;

import java.util.Optional;

/**
 *
 * @author bli
 */

/**
 * Base class for tokens in an arithmetic expression.
 * Provides factory methods for token creation.
 * 
 * @author bli
 */
public abstract class Token {
    
    /**
     * Exception thrown when an illegal character is encountered.
     */
    public static class IllegalElementException extends Exception {
        private final char ch;
        
        /**
         * Creates a new exception with the illegal character.
         *
         * @param c The illegal character
         */
        public IllegalElementException(char c) {
            super("Illegal character: " + c);
            this.ch = c;
        }
        
        /**
         * Gets the illegal character.
         *
         * @return The illegal character as a string
         */
        public String what() {
            return String.valueOf(ch);
        }
    }
    
    /**
     * Result of token reading operation, containing the token and remaining input.
     */
    public static class TokenResult {
        private final Token token;
        private final String remainingInput;
        
        public TokenResult(Token token, String remainingInput) {
            this.token = token;
            this.remainingInput = remainingInput;
        }
        
        public Token getToken() {
            return token;
        }
        
        public String getRemainingInput() {
            return remainingInput;
        }
    }
    
    /**
     * Reads a token from the input string.
     *
     * @param input the input string
     * @return the token read
     * @throws IllegalElementException If an illegal character is encountered
     */
    public static TokenResult readToken(String input) throws IllegalElementException {
        if (input.isEmpty()) {
            return new TokenResult(null, input);
        }
        
        int i = 1;
        final String digits = "0123456789";
        
        
        char firstChar = input.charAt(0);
        Token token = switch (firstChar) {
            case '+' -> OperatorAdd.getInstance();
            case '-' -> OperatorSub.getInstance();
            case '*' -> OperatorMul.getInstance();
            case '/' -> OperatorDiv.getInstance();
            case '(' -> LeftP.getInstance();
            case ')' -> RightP.getInstance();
            case '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' -> {
                StringBuilder str = new StringBuilder();
                for (i = 0; i < input.length() && digits.indexOf(input.charAt(i)) >= 0; ++i) {
                    str.append(input.charAt(i));
                }
                yield new Operand(Integer.parseInt(str.toString()));
            }
            default -> throw new IllegalElementException(firstChar);
        };
           
        String remainingInput = input.substring(i);
        return new TokenResult(token, remainingInput);
    }
}
