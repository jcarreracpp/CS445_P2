/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs445.project.pkg2;

import org.lwjgl.opengl.Display;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_POLYGON;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glEnd;

/**
 *
 * @author Jorge
 */
public class Update {
    InstructionPosting ip = new InstructionPosting();

    public Update() {
      while(!Display.isCloseRequested()){
          try{
              glClearColor(0.0f, 0.0f, 0.0f, 1.0f );
              glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
              //glBegin(GL_POLYGON);
              ip.initiate();
              //glEnd();
              
              Display.update();
              Display.sync(60);
          }catch (Exception e){}
      }
      Display.destroy();
    }
    
}
