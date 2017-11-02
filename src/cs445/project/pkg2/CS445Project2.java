package cs445.project.pkg2;

/**
 * file: CS445Project2.java
 * author: Jorge Luis Carrera
 * class: CS 445 - Computer Graphics
 * 
 * assignment: Program 2
 * date last modified: 11/1/2017
 * 
 * purpose: This program takes input from a file, then applies transformations
 * to a polygon given by points. The transformed polygons are then drawn.
 */
public class CS445Project2 {
  public static final int DISPLAY_HEIGHT = 480;
  public static final int DISPLAY_WIDTH = 640;
  public static float toot = 0;

    public static void main(String[] args) {
        new InitializeGL();
        new Update();
    }
    
}
