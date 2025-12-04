package com.sokobanigui;

import javafx.scene.image.ImageView;

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
    
    
    public abstract String getImageName();
    
    
    public ImageView getImageView() {
        ImageView view = new ImageView(ImageLoader.load(getImageName()));
        view.setFitWidth(64);
        view.setFitHeight(64);
        return view;
    }
}