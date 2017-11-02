package cs445.project.pkg2;

import static cs445.project.pkg2.CS445Project2.DISPLAY_HEIGHT;
import static cs445.project.pkg2.CS445Project2.DISPLAY_WIDTH;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glOrtho;

/**
 * file: InitializeGL.java
 * author: Jorge Luis Carrera
 * class: CS 445 - Computer Graphics
 * 
 * assignment: Program 2
 * date last modified: 11/1/2017
 * 
 * purpose: This program takes input from a file, then applies transformations
 * to a polygon given by points. The transformed polygons are then drawn.
 */
public class InitializeGL {
    private static final int DW2 = (DISPLAY_WIDTH/2);
    private static final int DH2 = (DISPLAY_HEIGHT/2);
    
    //Method: InitializeGL
    //Purpose: When called, sets up all the default project settings for openGL.
    public InitializeGL(){
        try{
            Display.setDisplayMode(new DisplayMode(DISPLAY_WIDTH, DISPLAY_HEIGHT));
            Display.setTitle("CS 445 Computer Graphics Project 2 - Jorge Luis Carrera");
            Display.create();
            glOrtho(-DW2, DW2, -DH2, DH2, 1, -1);
            glMatrixMode(GL_MODELVIEW);
        }catch (Exception e){}
    }
    
}
