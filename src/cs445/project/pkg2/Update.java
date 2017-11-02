package cs445.project.pkg2;

import org.lwjgl.opengl.Display;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;

/**
 * file: Update.java
 * author: Jorge Luis Carrera
 * class: CS 445 - Computer Graphics
 * 
 * assignment: Program 2
 * date last modified: 11/1/2017
 * 
 * purpose: This program takes input from a file, then applies transformations
 * to a polygon given by points. The transformed polygons are then drawn.
 */
public class Update {
    InstructionPosting ip = new InstructionPosting();

    //Method: Update
    //Purpose: Calls the drawing portion of the program and updates the screen
    //60 times a second.
    public Update() {
      while(!Display.isCloseRequested()){
          try{
              glClearColor(0.0f, 0.0f, 0.0f, 1.0f );
              glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
              
              ip.initiate();
              
              Display.update();
              Display.sync(60);
          }catch (Exception e){}
      }
      Display.destroy();
    }
    
}
