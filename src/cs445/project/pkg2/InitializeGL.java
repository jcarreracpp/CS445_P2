/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs445.project.pkg2;

import static cs445.project.pkg2.CS445Project2.DISPLAY_HEIGHT;
import static cs445.project.pkg2.CS445Project2.DISPLAY_WIDTH;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import static org.lwjgl.opengl.GL11.glOrtho;

/**
 *
 * @author Jorge
 */
public class InitializeGL {
    
    public InitializeGL(){
        try{
            Display.setDisplayMode(new DisplayMode(DISPLAY_WIDTH, DISPLAY_HEIGHT));
            Display.setTitle("CS 445 Computer Graphics Project 2 - Jorge Luis Carrera");
            Display.create();
            glOrtho(0, DISPLAY_WIDTH, 0, DISPLAY_HEIGHT, 1, -1);
        }catch (Exception e){}
    }
    
}
