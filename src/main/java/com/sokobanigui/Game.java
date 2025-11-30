package com.sokobanigui;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.FileSystemException;
import java.util.Scanner;

public class Game {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Scanner scannerfile = new Scanner(System.in);
        GameMap map = new GameMap();
        InputHandler inputHandler = new InputHandler();

        map.loadFromFile("level1.txt");
        String currentfile = "";
        map.draw();

        System.out.println(" WASD to move or q to quit");

        String input;
        while (!((input = scanner.nextLine()).equals("q"))) {

            if (input.isEmpty()) {
                continue;
            }

            String c = input.toLowerCase();

            switch (c) {
                case "w": inputHandler.fireMoveUp(map); break;
                case "a": inputHandler.fireMoveLeft(map); break;
                case "s": inputHandler.fireMoveDown(map); break;
                case "d": inputHandler.fireMoveRight(map); break;
                case "load": {
                    System.out.println("input filename: "); String targetfile = scannerfile.nextLine(); currentfile = targetfile; map.loadFromFile(targetfile); map.draw();}
                case "save": {
                    try{
                        System.out.println("Give a name to the save file"); String targetsavefile = scannerfile.nextLine(); map.writeToFile(targetsavefile);
                    }catch(Exception e){
                        System.out.println("Eroor : " + e.getMessage());
                    }
                } break;
                case "r": map.loadFromFile(currentfile); map.draw(); System.out.println("Level restarted"); break;
                default:
                    System.out.println("Wrong input select wasd to move or q to quit.");       
            }
        }

        System.out.println("Game over!");
        scanner.close();
    }
  
   }
