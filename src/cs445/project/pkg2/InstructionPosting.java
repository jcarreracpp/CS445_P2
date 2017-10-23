/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs445.project.pkg2;
    
import java.util.ArrayList;
import java.util.List;
import static org.lwjgl.opengl.GL11.GL_POLYGON;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glScalef;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glVertex2f;

/**
 *
 * @author Jorge
 */
public class InstructionPosting {

    
    public InstructionPosting() {
        /**
         * Heres where the actual instructions will be done,
         * this class is probably going to call a file reader and get the 
         * polygon's coords and transformations as array objects or as their own.
         * Remember that transformations are applied FIRST, IN REVERSE, then
         * the polygon vertices are plotted.
         */


    }
    
    public void initiate(){
        InstructionFiler iF = new InstructionFiler();
        List<TraPol> instructionBank = new ArrayList();
        instructionBank = iF.processFile();
        drawPrimitives(instructionBank);
        //System.out.println("IB " + instructionBank.size());
    }

    public void drawPrimitives(List<TraPol> input) {
        for(int i = 0; i < input.size(); i++){
            System.out.println("Input size " + input.size());
            setColor(input.get(i));
            applyTransform(input.get(i));
            drawPoints(input.get(i));
        }
    }

    private void drawTraPol(TraPol trpl) {

    }

    private void applyTransform(TraPol trpl) {
        for(int k = trpl.getTranSize(); k >= 0; k--){
            if(trpl.getTransTypeAt(k).contains("t")){
                glTranslatef(trpl.getTransPointAt(k)[0], trpl.getTransPointAt(k)[1], 0.0f);
            }else if(trpl.getTransTypeAt(k).contains("s")){
                glScalef(trpl.getTransPointAt(k)[0], trpl.getTransPointAt(k)[1], trpl.getTransPointAt(k)[2]);
            }else if(trpl.getTransTypeAt(k).contains("r")){
                glRotatef(trpl.getTransPointAt(k)[0], trpl.getTransPointAt(k)[1], trpl.getTransPointAt(k)[2], 0.0f);
            }
        }
    }

    public void drawPoints(TraPol trpl) {
                System.out.println("here1 " + trpl.getPolySize());
        glBegin(GL_POLYGON);
        for(int j = 0; j < trpl.getPolySize(); j++){
            System.out.println("Point at "+ trpl.getPointAt(j)[0] + ", "+ trpl.getPointAt(j)[1]);
            glVertex2f(trpl.getPointAt(j)[0],trpl.getPointAt(j)[1]);
        }
        glEnd();
    }

    private void setColor(TraPol trpl) {
        glColor3f(trpl.getPolyColor()[0],trpl.getPolyColor()[1],trpl.getPolyColor()[2]);
    }
    
}
