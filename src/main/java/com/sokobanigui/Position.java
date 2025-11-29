package com.sokobanigui;

public class Position {
    private int Xcoordinate;
    private int Ycoordinate;

    public Position(int Xcoordinate, int Ycoordinate) {
        this.Xcoordinate = Xcoordinate;
        this.Ycoordinate = Ycoordinate;
    }

    public int getPositionX() {
        return Xcoordinate;
    }

    public int getPositionY() {
        return Ycoordinate;
    }

    public void setPositionX(int setinposX) {
        Xcoordinate = setinposX;
    }

    public void setPositionY(int setinposY) {
        Ycoordinate = setinposY;
    }

    public String toString() {
        return "(" + Xcoordinate + ", " + Ycoordinate + ")";
    }
}
