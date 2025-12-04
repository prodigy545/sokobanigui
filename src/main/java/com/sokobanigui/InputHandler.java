package com.sokobanigui;

public class InputHandler {
    
    private GameMap map;
    private String levelPath;
    
    public InputHandler(GameMap map, String levelPath) {
        this.map = map;
        this.levelPath = levelPath;
    }
    
    public void fireMoveUp() {
        map.movePlayer(0, -1);
    }
    
    public void fireMoveDown() {
        map.movePlayer(0, 1);
    }
    
    public void fireMoveLeft() {
        map.movePlayer(-1, 0);
    }
    
    public void fireMoveRight() {
        map.movePlayer(1, 0);
    }
    
    public void fireRestart() {
        map.restart(levelPath);
    }
}