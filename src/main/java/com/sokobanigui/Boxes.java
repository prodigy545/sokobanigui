package com.sokobanigui;

public class Boxes extends GameObjects implements Movable {
    
    public Boxes(Position boxPosition){
        super(boxPosition);
    }

    @Override
    public char getCharacter(){
        return 'B';
    }
    
    // ADD THIS METHOD
    @Override
    public String getImageName() {
        return "box.png";
    }

    public void move(int dx, int dy) {
        Position p = getPosition();
        p.setPositionX(p.getPositionX() + dx);
        p.setPositionY(p.getPositionY() + dy);
    }
}