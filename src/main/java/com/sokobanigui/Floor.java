package com.sokobanigui;

public class Floor extends GameObjects {
    
    public Floor(Position floorPosition) {
        super(floorPosition);
    }
    
    @Override
    public char getCharacter() {
        return '.';
    }
    
    @Override
    public String getImageName() {
        return "floor.png";
    }
}