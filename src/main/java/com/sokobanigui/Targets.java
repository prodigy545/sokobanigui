package com.sokobanigui;

public class Targets extends GameObjects {
    
    public Targets(Position targetsPosition){
        super(targetsPosition);
    }

    @Override
    public char getCharacter(){
        return 'T';
    }

}
