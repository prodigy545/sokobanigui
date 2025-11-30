package com.sokobanigui;

public class InputHandler {

    public void fireMoveUp(GameMap map){
        map.movePlayer(0, -1);
    }

    public void fireMoveDown(GameMap map){
        map.movePlayer(0, 1);
    }
    
    public void fireMoveRight(GameMap map){
        map.movePlayer(1, 0);
    }

    public void fireMoveLeft(GameMap map){
        map.movePlayer(-1, 0);
    }

}

