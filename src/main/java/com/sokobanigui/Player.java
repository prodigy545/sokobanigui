package com.sokobanigui;

public class Player extends GameObjects implements Movable{
    private Position position;
    private int gridwidth;
    private int gridheight;

    Player(Position startPosition, int gridWidth, int gridHeight) {
        super(startPosition);
        this.position = startPosition;
        this.gridwidth = gridWidth;
        this.gridheight = gridHeight;
    }

    public void moveUp () {
        move(0, -1);
    }

    public void moveDown() {
        move(0, 1);
    }

    public void moveLeft() {
        move(-1, 0);
    }

    public void moveRight() {
        move(1, 0);
    }

    public Position getPosition() {
        return position;
    }

    @Override
    public char getCharacter(){
        return 'P';
    }

    @Override
    public void move(int dx, int dy) {
        // TODO Auto-generated method stub
        int newX = position.getPositionX() + dx;
        int newY = position.getPositionY() + dy;
        if(newX>=0 && newX < gridwidth && newY>=0 && newY < gridheight){
            position.setPositionX(newX);
            position.setPositionY(newY);
        }else{
            System.out.println("You cant move outside the map!");
        }
    }

    

}
