package com.sokobanigui;

public class Wall extends GameObjects {

    public Wall(Position wallPosition){
        super(wallPosition);
    }    
    
    @Override
    public char getCharacter(){
        return '#';
    }
}
