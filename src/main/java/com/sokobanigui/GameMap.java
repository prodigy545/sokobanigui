package com.sokobanigui;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
public class GameMap {
    
    public List<GameObjects> staticGameObjects;
    public List<GameObjects> dynamiGameObjects;
    private final List<GameEventListener> listeners = new ArrayList<>();


    int gridHeight;
    int gridWidth;

    Player player;

    public GameMap(){
        staticGameObjects = new ArrayList<>();
        dynamiGameObjects = new ArrayList<>();
    }

    public void addListener(GameEventListener l) { 
    listeners.add(l); 
}

public void removeListener(GameEventListener l) { 
    listeners.remove(l); 
}

private void emit(GameEvent e) { 
    for (GameEventListener l : listeners) {
        l.onEvent(e);
    }
}

   public  void loadFromFile(String filename) {
    InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filename);


    if(inputStream == null) {
        System.err.println("error Could not find file: " + filename);
        System.out.println("Loading default level...");
        createDefaultLevel();
        return;
    }

    try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
        List<String> lines = new ArrayList<>();
        String line;
        while ((line = reader.readLine()) != null) {
            lines.add(line);
        }
        
        int expectedWidth = lines.get(0).length();
        int playerCount = 0;
        
        for (int y = 0; y < lines.size(); y++) {
            String currentLine = lines.get(y);
            
            if (currentLine.length() != expectedWidth) {
                throw new InvalidLevelFormatException(
                    "Line has diff width", y + 1, 0);
            }
            
            for (int x = 0; x < currentLine.length(); x++) {
                char c = currentLine.charAt(x);
                
                if (c == 'P') {
                    playerCount++;
                }
                else if (c != '#' && c != 'T' && c != 'B' && c != ' ') {
                throw new InvalidLevelFormatException(
                    "Invalid character '" + c + "'", y + 1, x + 1);
                }
            }
                }
        
        if (playerCount != 1) {
            throw new InvalidLevelFormatException(
                "Must have exactly one player.");
        }

        
        
        staticGameObjects.clear();
        dynamiGameObjects.clear();
        
        int y = 0;
        for (String l : lines) {
            for (int x = 0; x < l.length(); x++) {
                char c = l.charAt(x);
                Position pos = new Position(x, y);
                switch (c) {
                    case '#': staticGameObjects.add(new Wall(pos)); break;
                    case 'P': 
                        player = new Player(pos, l.length(), lines.size()); 
                        dynamiGameObjects.add(player); 
                        break;
                    case 'B': dynamiGameObjects.add(new Boxes(pos)); break;
                    case 'T': staticGameObjects.add(new Targets(pos)); break;
                }
            }
            y++;
            gridWidth = l.length();
        }
        gridHeight = y;
        
    } catch (IOException e) {
        System.err.println("ERROR reading file: " + e.getMessage());
        System.out.println("Loading default level...");
        createDefaultLevel();
    } catch (InvalidLevelFormatException e) {
        System.err.println("ERROR: " + e.getMessage());
        System.out.println("Loading default level...");
        createDefaultLevel();
    }
}


public void writeToFile(String filename){
        String currentDir = System.getProperty("user.dir");
        
        String fullPath = currentDir + "/src/main/resources/" + filename;
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fullPath))) {
                char[][] grid = new char[gridHeight][gridWidth];

                for (int y = 0; y<gridHeight; y++){
                    for(int x = 0; x<gridWidth; x++){
                        grid[y][x] = '.';
                    }
                }
    

       for (GameObjects obj : staticGameObjects) {
            int x = obj.getPosition().getPositionX();
            int y = obj.getPosition().getPositionY();
            if (obj instanceof Wall) {
                grid[y][x] = '#';
            } else if (obj instanceof Targets) {
                grid[y][x] = 'T';
            }
        }
        
        
        for (GameObjects obj : dynamiGameObjects) {
            if (obj instanceof Boxes) {
                int x = obj.getPosition().getPositionX();
                int y = obj.getPosition().getPositionY();
                grid[y][x] = 'B';
            }
        }
        
        
        if (player != null) {
            int x = player.getPosition().getPositionX();
            int y = player.getPosition().getPositionY();
            grid[y][x] = 'P';
        }
        
        
        for (int y = 0; y < gridHeight; y++) {
            for (int x = 0; x < gridWidth; x++) {
                writer.write(grid[y][x]);
            }
            writer.newLine();
        }
        
     }  catch (IOException e) {
        System.err.println("Autosave failed: " + e.getMessage());
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
    
    Position from = new Position(player.getPosition().getPositionX(), 
                                  player.getPosition().getPositionY());
    
    int nextX = player.getPosition().getPositionX() + dx;
    int nextY = player.getPosition().getPositionY() + dy;

    GameObjects objectAtNextPosition = getObjectAt(nextX, nextY);

    if (objectAtNextPosition == null || objectAtNextPosition instanceof Targets) {
        player.move(dx, dy);
        writeToFile("autosave.txt");
        
        Position to = player.getPosition();
        emit(new GameEvent(GameEventType.MOVE, from, to, null));
        
    } else if (objectAtNextPosition instanceof Wall) {
        System.out.println("You can't move through walls!");
        emit(new GameEvent(GameEventType.INVALID_MOVE, from, from, null));
        
    } else if (objectAtNextPosition instanceof Boxes) {
        int positionBeyondBoxX = nextX + dx;
        int positionBeyondBoxY = nextY + dy;

        GameObjects objectBeyondBox = getObjectAt(positionBeyondBoxX, positionBeyondBoxY);

        if (objectBeyondBox == null || objectBeyondBox instanceof Targets) {
            Boxes boxToPush = (Boxes) objectAtNextPosition;
            boxToPush.move(dx, dy);
            player.move(dx, dy);
            writeToFile("autosave.txt");
            
            Position to = player.getPosition();
            emit(new GameEvent(GameEventType.PUSH, from, to, null));
            
            if (objectBeyondBox instanceof Targets) {
                System.out.println("Box placed on target!");
            }
        } else {
            System.out.println("Box can't move that way.");
            emit(new GameEvent(GameEventType.INVALID_MOVE, from, from, null));
        }
    }
    
    draw();
    
    // Check win and emit event
    if (checkWin()) {
        emit(new GameEvent(GameEventType.WIN, null, null, "All boxes on targets"));
    }
}

    public GameObjects getObjectAt(int x, int y) {
        for (GameObjects currentObject : dynamiGameObjects) 
            if (currentObject.isAt(x, y)) return currentObject;
        for (GameObjects currentObject : staticGameObjects) 
            if (currentObject.isAt(x, y)) return currentObject;
        return null;
    }

   public boolean checkWin(){
    for (GameObjects gameObject : staticGameObjects){
        if(gameObject instanceof Targets){
            boolean hasBox = false;
            for (GameObjects gameObject2 : dynamiGameObjects) {
                if (gameObject2 instanceof Boxes && 
                    gameObject2.isAt(gameObject.getPosition().getPositionX(), 
                                    gameObject.getPosition().getPositionY())){
                    hasBox = true;
                    break;
                }
            }
            if(!hasBox){
                return false;
            }
        }
    }
    System.out.println("You Win! All boxes are on targets!");
    return true;  
}


public GameObjects getTopObjectAt(int x, int y) {
    // Check player first
    if (player != null && player.isAt(x, y)) {
        return player;
    }
    
    // Check dynamic objects (boxes)
    for (GameObjects obj : dynamiGameObjects) {
        if (obj.isAt(x, y)) {
            return obj;
        }
    }
    
    // Check static objects (walls, targets)
    for (GameObjects obj : staticGameObjects) {
        if (obj.isAt(x, y)) {
            return obj;
        }
    }
    
    return null;
}

public int getWidth() {
    return gridWidth;
}

public int getHeight() {
    return gridHeight;
}

public void restart(String path) {
    loadFromFile(path);
    emit(new GameEvent(GameEventType.RESTART, null, null, path));
}
    
}