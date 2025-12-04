package com.sokobanigui;

public class Targets extends GameObjects {
    
    public Targets(Position targetsPosition){
        super(targetsPosition);
    }

    @Override
    public char getCharacter(){
        return 'T';
    }
    
    // ADD THIS METHOD
    @Override
    public String getImageName() {
        return "target.png";
    }
}