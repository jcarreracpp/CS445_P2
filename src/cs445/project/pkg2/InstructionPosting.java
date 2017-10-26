/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs445.project.pkg2;
    
import static cs445.project.pkg2.CS445Project2.toot;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import static org.lwjgl.opengl.GL11.GL_POINTS;
import static org.lwjgl.opengl.GL11.GL_POLYGON;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
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
            input.get(i).calcEffectiveEdges();
            input.get(i).organizeEdgesTable();
            setColor(input.get(i));
            glPushMatrix();
            applyTransform(input.get(i));
            //dynamicRotate();
            drawPoints(input.get(i));
            drawFill(input.get(i));            
            glPopMatrix();
            //drawTraPol(input.get(i));
        }
    }

    private void drawTraPol(TraPol trpl) {
        trpl.calcEffectiveEdges();
        trpl.organizeEdgesTable();
        for(int j = 0; j < trpl.getPolySize(); j++){
            System.out.println("OWDWE"+j+": ["+trpl.getAllEdgesTable(j)[0]+"] ["+trpl.getAllEdgesTable(j)[1]+"] ["+trpl.getAllEdgesTable(j)[2]+"] ["+trpl.getAllEdgesTable(j)[3]+"]");
        }
    }

    private void dynamicRotate(){
        if(toot > 360)
            toot = -360;
        glRotatef(toot, 0, 0, 1);
        toot++;
    }
    private void applyTransform(TraPol trpl) {
        //glTranslatef(trpl.getHorizOffset(), trpl.getVertOffset(), 0.0f);

        //for(int k = 0; k < trpl.getTranSize(); k++){
        for(int k = trpl.getTranSize()-1; k >= 0; k--){
            if(trpl.getTransTypeAt(k).contains("t")){
                glTranslatef(trpl.getTransPointAt(k)[0], trpl.getTransPointAt(k)[1], 0.0f);
            }else if(trpl.getTransTypeAt(k).contains("s")){
                glScalef(trpl.getTransPointAt(k)[0], trpl.getTransPointAt(k)[1], trpl.getTransPointAt(k)[2]);
            }else if(trpl.getTransTypeAt(k).contains("r")){
                glRotatef(trpl.getTransPointAt(k)[0], trpl.getTransPointAt(k)[1], trpl.getTransPointAt(k)[2], 1);
            }
        }
        //glTranslatef(-trpl.getHorizOffset(), -trpl.getVertOffset(), 0.0f);

    }

    public void drawPoints(TraPol trpl) {
        glBegin(GL_POINTS);
        
        for(int j = 0; j < trpl.getPolySize(); j++){
            //System.out.println("Point at "+ trpl.getPointAt(j)[0] + ", "+ trpl.getPointAt(j)[1]);
            if(j == trpl.getPolySize()-1){
                loadLine(trpl.getPointAt(j)[0],trpl.getPointAt(j)[1], trpl.getPointAt(0)[0], trpl.getPointAt(0)[1]);
            }else{
                loadLine(trpl.getPointAt(j)[0],trpl.getPointAt(j)[1], trpl.getPointAt((j+1))[0], trpl.getPointAt((j+1))[1]);
            }
        }
        glEnd();
    }
    
    public void drawFill(TraPol trpl){
        List<float[]> internal = new ArrayList();
        List<float[]> active = new ArrayList();
        float[] temp = new float[4];
        float scanlinePos;
        
        internal = trpl.getTable();
        scanlinePos = internal.get(0)[0];
//        active.add(internal.get(0));
//        scanlinePos = active.get(0)[0];
//        internal.remove(0);
        while(internal.size() > 0){
            for(int j = 0; j < internal.size(); j++){
                if(internal.get(j)[0] == scanlinePos){
                    active.add(internal.get(0));
                    internal.remove(0);
                    j--;
                }
            }
            if(active.size() > 0){
                for(int i = 0; i < active.size(); i++){
                    if(active.get(i)[1] == scanlinePos){
                        active.remove(i);
                        i--;
                    }
                }
            }
            active = organizeEdgesTable(active);
            for(int k = 0; k < active.size(); k += 2){
                loadLine((int)active.get(k)[2], scanlinePos, (int)active.get(k+1)[2], scanlinePos);
            }
            scanlinePos++;

            for(int m = 0; m < active.size(); m++){
                temp = active.get(m);
                temp[2] = temp[2] + temp[3];
            }
        }
    }

    private void setColor(TraPol trpl) {
        glColor3f(trpl.getPolyColor()[0],trpl.getPolyColor()[1],trpl.getPolyColor()[2]);
    }
    
    //Method: loadLine
    //Purpose: Receives input and draws a line based on the two endpoints. 
    //Points are modified to draw starting from the left, and can detect if
    //slope is positive or negative, and also greater than 1.
    public void loadLine(float coord1, float coord2, float coord3, float coord4){
        float c1 = coord1;
        float c2 = coord2;
        float c3 = coord3;
        float c4 = coord4;
        float slope = 0;
        boolean vert = false;
        
        //glColor3f(1.0f, 0.0f, 0.0f);
        
        try{
        
        if(c1 > c3){
            c1 = c1 + c3;
            c3 = c1 - c3;
            c1 = c1 - c3;
            c2 = c2 + c4;
            c4 = c2 - c4;
            c2 = c2 - c4;
        }
        
        if(c4-c2 == 0){
            slope = 0;
        }else if(c3 - c1 == 0){
            vert = true;
        }else {
            slope = ((c4-c2)/(c3-c1));
        }
        
        if(vert){
            glVertex2f(c1, c2);
            if (c2 < c4) {
                while (c2 != c4) {
                    c2++;
                    glVertex2f(c1, c2);
                }
            } else {
                while (c2 != c4) {
                    c2--;
                    glVertex2f(c1, c2);
                }
            }
        }else if(slope == 0){
            glVertex2f(c1, c2);
            while(c1 != c3){
                c1++;
                glVertex2f(c1,c2);
            }
        //Station 1
        }else if(0 < slope && slope <= 1){
            float dx = c3 - c1;
            float dy = c4 - c2;
            float incR = 2*(dy);
            float incUR = 2*(dy - dx);
            float d = incR - dx;
            glVertex2f(c1, c2);
            
            while(c1 != c3 || c2 != c4){
                if(d > 0){
                    c1++;
                    c2++;
                    d = d + incUR;
                }else{
                    c1++;
                    d = d + incR;
                }
                glVertex2f(c1,c2);
            }
        //Station 2
        }else if(0 > slope && slope >= -1){
            float dx = c3 - c1;
            float dy = c2 - c4;
            float incR = 2*(dy);
            float incDR = 2*(dy - dx);
            float d = incR - dx;
            glVertex2f(c1, c2);
            
            while(c1 != c3 || c2 != c4){
                if(d > 0){
                    c1++;
                    c2--;
                    d = d + incDR;
                }else{
                    c1++;
                    d = d + incR;
                }
                glVertex2f(c1,c2);
            }
        //Station 3
        }else if(slope > 1){
            float dx = c3 - c1;
            float dy = c4 - c2;
            float incR = 2*(dx);
            float incUR = 2*(dx - dy);
            float d = incR - dy;
            glVertex2f(c1, c2);
            
            while(c1 != c3 || c2 != c4){
                if(d > 0){
                    c1++;
                    c2++;
                    d = d + incUR;
                }else{
                    c2++;
                    d = d + incR;
                }
                glVertex2f(c1,c2);
            }
        //Station 4
        }else{
            float dx = c3 - c1;
            float dy = c2 - c4;
            float incR = 2*(dx);
            float incDR = 2*(dx - dy);
            float d = incR - dy;
            glVertex2f(c1, c2);
            
            while(c1 != c3 || c2 != c4){
                if(d > 0){
                    c1++;
                    c2--;
                    d = d + incDR;
                }else{
                    c2--;
                    d = d + incR;
                }
                glVertex2f(c1,c2);
            }
        }
        }catch (Exception e){}
        vert = false;
    }
    
    public List<float[]> organizeEdgesTable(List<float[]> lf) {

        for (int i = 0; i < lf.size() - 1; i++) {
            int minimum = i;
            for (int j = i + 1; j < lf.size(); j++) {
                if (lf.get(j)[0] != lf.get(minimum)[0]) {
                    if (lf.get(j)[0] < lf.get(minimum)[0]) {
                        minimum = j;
                    } else {
                    }
                } else if (lf.get(j)[2] != lf.get(minimum)[2]) {
                    if (lf.get(j)[2] < lf.get(minimum)[2]) {
                        minimum = j;
                    } else {
                    }
                } else if (lf.get(j)[1] != lf.get(minimum)[1]) {
                    if (lf.get(j)[1] < lf.get(minimum)[1]) {
                        minimum = j;
                    } else {
                    }
                } else if (lf.get(j)[3] != lf.get(minimum)[3]) {
                    if (lf.get(j)[3] < lf.get(minimum)[3]) {
                        minimum = j;
                    } else {
                    }
                } else {
                }
            }
            Collections.swap(lf, i, minimum);
        }
        return lf;
    }
}
