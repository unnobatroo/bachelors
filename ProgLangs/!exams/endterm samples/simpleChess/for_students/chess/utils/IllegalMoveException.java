package chess.utils;

public class IllegalMoveException extends Exception
{
    private String msg;
    public String getMsg() { return msg; }
    public IllegalMoveException(String msg) { this.msg = msg; }
}