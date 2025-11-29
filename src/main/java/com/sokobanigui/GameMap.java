package com.sokobanigui;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
public class GameMap {
    
    public List<GameObjects> staticGameObjects;
    public List<GameObjects> dynamiGameObjects;

    int gridHeight;
    int gridWidth;

    Player player;

    public GameMap(){
        staticGameObjects = new ArrayList<>();
        dynamiGameObjects = new ArrayList<>();
    }

    public void loadFromFile(String filename) {

        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filename);

        if(inputStream == null){
            System.out.println("Could not find this spesific filename" + filename + "Using the default level");
            createDefaultLevel();
            return;
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            int y = 0;
            while ((line = reader.readLine()) != null) {
                for (int x = 0; x < line.length(); x++) {
                    char c = line.charAt(x);
                    Position pos = new Position(x, y);
                    switch (c) {
                        case '#': staticGameObjects.add(new Wall(pos)); break;
                        case 'P': 
                            player = new Player(pos, line.length(), 100); 
                            dynamiGameObjects.add(player); 
                            break;
                        case 'B': dynamiGameObjects.add(new Boxes(pos)); break;
                        case 'T': staticGameObjects.add(new Targets(pos)); break;
                    }
                }
                y++;
                gridWidth = line.length();
            }
            gridHeight = y;
            reader.close();
        } catch (IOException e) {
            System.out.println("Loadig default level....");
            createDefaultLevel();
        }
    }


   public void createDefaultLevel() {
        gridHeight = 7; gridWidth = 7;

        for (int i = 0; i < gridWidth; i++) {
            staticGameObjects.add(new Wall(new Position(i, 0)));
            staticGameObjects.add(new Wall(new Position(i, gridHeight-1)));
        }
        for (int k = 1; k < gridHeight-1; k++) {
            staticGameObjects.add(new Wall(new Position(0, k)));
            staticGameObjects.add(new Wall(new Position(gridWidth-1, k)));
        }

        player = new Player(new Position(2, 2), gridWidth, gridHeight);
        dynamiGameObjects.add(player);
        dynamiGameObjects.add(new Boxes(new Position(3, 2)));
        staticGameObjects.add(new Targets(new Position(4, 3)));
    }

    public void draw(){
        for (int y = 0; y < gridHeight; y++) {
            for (int x = 0; x < gridWidth; x++) {
                System.out.print(getCharAt(x, y) + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public char getCharAt(int x, int y){
        
        if (player != null && player.isAt(x, y)) return 'P';
        
        
        for (GameObjects obj : dynamiGameObjects) {
            if (obj instanceof Boxes && obj.isAt(x, y)) {
                
                for (GameObjects target : staticGameObjects) {
                    if (target instanceof Targets && target.isAt(x, y)) {
                        return 'X';
                    }
                }
                return 'B';
            }
        }
        
        
        for (GameObjects obj : staticGameObjects) {
            if (obj.isAt(x, y)) {
                if (obj instanceof Wall) return '#';
                if (obj instanceof Targets) return 'T';
            }
        }
        
        return '.';
    }

    
    public void movePlayer(int dx, int dy) {
        if (player == null) return;
        
        int nextX = player.getPosition().getPositionX() + dx;
        int nextY = player.getPosition().getPositionY() + dy;

        GameObjects objectAtNextPosition = getObjectAt(nextX, nextY);

        if (objectAtNextPosition == null || objectAtNextPosition instanceof Targets) {
            player.move(dx, dy);
        } else if (objectAtNextPosition instanceof Wall) {
            System.out.println("You can't move through walls!");
        } else if (objectAtNextPosition instanceof Boxes) {
            int positionBeyondBoxX = nextX + dx;
            int positionBeyondBoxY = nextY + dy;

            GameObjects objectBeyondBox = getObjectAt(positionBeyondBoxX, positionBeyondBoxY);

            if (objectBeyondBox == null || objectBeyondBox instanceof Targets) {
                Boxes boxToPush = (Boxes) objectAtNextPosition;
                boxToPush.move(dx, dy);
                player.move(dx, dy);
                
                if (objectBeyondBox instanceof Targets) {
                    System.out.println("Box placed on target!");
                }
            } else {
                System.out.println("Box can't move that way.");
            }
        }
        
        draw();
        checkWin();
        
        
    }

    public GameObjects getObjectAt(int x, int y) {
        for (GameObjects currentObject : dynamiGameObjects) 
            if (currentObject.isAt(x, y)) return currentObject;
        for (GameObjects currentObject : staticGameObjects) 
            if (currentObject.isAt(x, y)) return currentObject;
        return null;
    }

    public void checkWin(){
        for (GameObjects gameObject : staticGameObjects){
            if(gameObject instanceof Targets){
                boolean hasBox = false;
                for (GameObjects gameObject2 : dynamiGameObjects) {
                    if (gameObject2 instanceof Boxes && gameObject2.isAt(gameObject.getPosition().getPositionX(), gameObject.getPosition().getPositionY())){
                            hasBox = true;
                            System.out.println("Box placed on target");
                            
                            break;
                    }
                }
                if(!hasBox){
                    return;
                }
            }
        }
        System.out.println("You Win! All boxes are in target ");
        System.exit(0);
    }

    
}