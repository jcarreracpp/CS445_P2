/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs445.project.pkg2;

import org.lwjgl.opengl.Display;
import static org.lwjgl.opengl.GL11.GL_POLYGON;
import static org.lwjgl.opengl.GL11.glBegin;
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
