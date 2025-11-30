package com.sokobanigui;
import javafx.scene.image.Image;
import java.util.HashMap;

public class ImageLoader {

    private static final HashMap<String, Image> cache = new HashMap<>();

    public static Image load(String name) {
        if (!cache.containsKey(name)) {
            try {
                String path = ImageLoader.class.getResource("/" + name).toExternalForm();
                Image img = new Image(path);
                cache.put(name, img);
                System.out.println("Loaded image: " + name);
            } catch (Exception e) {
                System.err.println("Could not load image: " + name);
                System.err.println("Error: " + e.getMessage());
                return null;
            }
        }
        return cache.get(name);
    }
}