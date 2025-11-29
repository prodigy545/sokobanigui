package com.sokobanigui;

public abstract class GameObjects {

    private Position newPosition;

    public GameObjects(Position newPosition){
        this.newPosition = newPosition;
    }

    public Position getPosition(){
        return newPosition;
    }

    public boolean isAt(int x, int y) {
        return newPosition.getPositionX() == x &&  newPosition.getPositionY() == y;
    }
    public abstract char getCharacter();
        
    

}