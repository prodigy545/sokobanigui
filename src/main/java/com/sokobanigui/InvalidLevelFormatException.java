package com.sokobanigui;

public class InvalidLevelFormatException extends Exception {
    
    public InvalidLevelFormatException(String message){
        super(message);
    }

    public InvalidLevelFormatException(String message, int line, int column){
        super(message + "found in line:" + line + "and in column " + column);
    }

}