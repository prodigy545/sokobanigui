package com.sokobanigui;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Game {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        GameMap map = new GameMap();

        map.loadFromFile("level1.txt");
        map.draw();

        System.out.println(" WASD to move or q to quit");

        String input;
        while (!((input = scanner.nextLine()).equals("q"))) {
            switch (input) {
                case "w": map.movePlayer(0, -1); break;
                case "a": map.movePlayer(-1, 0); break;
                case "s": map.movePlayer(0, 1); break;
                case "d": map.movePlayer(1, 0); break;
                    
            }
        }

        System.out.println("Game over!");
        scanner.close();
    }
  
   }
