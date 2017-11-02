package cs445.project.pkg2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 * file: InstructionFiler.java
 * author: Jorge Luis Carrera
 * class: CS 445 - Computer Graphics
 * 
 * assignment: Program 2
 * date last modified: 11/1/2017
 * 
 * purpose: This program takes input from a file, then applies transformations
 * to a polygon given by points. The transformed polygons are then drawn.
 */
public class InstructionFiler {
    private String fileName = "coordinates.txt";
    private File file = new File(fileName);
    private List<TraPol> drawList = new ArrayList();
    private TraPol trpl = new TraPol();
    
    //Method: InstructionFiler
    //Purpose: Constructor that checks to see if the file coordinates.txt exists
    // and if not, it makes a default one.
    public InstructionFiler(){
        if(file.exists()){
        }else{
            try{
            file.createNewFile();
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
    
    //Method: processFile
    //Purpose: Reads an entire file formatted as specified in the prompt, and
    //generates any given number of TraPol objects into a list with specified
    //transformations and vertex points.
    public List<TraPol> processFile(){
        boolean polPassed = false;
        boolean traPassed = false;
        boolean newTraPol = false;
        String getLine;
        String temp;
        String typeBuffer;
        
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            while (( getLine = br.readLine()) != null) {
                temp = getLine;
                newTraPol = false;
                
                if(temp.contains("P") && polPassed && traPassed){
                    drawList.add(trpl);
                    trpl = new TraPol();
                    polPassed = false;
                    traPassed = false;
                    newTraPol = true;
                }
                if(!temp.contains("P") && polPassed && !traPassed && !newTraPol){
                    if(temp.contains("T")){
                        traPassed = true;
                    }else{
                        String[] point = temp.split("\\s+");
                        int[] pointVal = new int[2];
                        pointVal[0] = Integer.parseInt(point[0]);
                        pointVal[1] = Integer.parseInt(point[1]);
                        trpl.addPol(pointVal);
                    }
                }
                if(temp.contains("P") && !polPassed){
                    temp = temp.substring(2, temp.length());
                    String[] colorVal = temp.split("\\s+");
                    float[] colorValues = new float[3];
                    colorValues[0] = Float.parseFloat(colorVal[0]);
                    colorValues[1] = Float.parseFloat(colorVal[1]);
                    colorValues[2] = Float.parseFloat(colorVal[2]);
                    trpl.addPolyColor(colorValues);
                    polPassed = true;
                }

                if(polPassed && traPassed && !newTraPol){
                    if(temp.contains("t")){
                        typeBuffer = temp.substring(0, 1);
                        temp = temp.substring(2, temp.length());
                        String[] transVal = temp.split(" ");
                        float[] transValues = new float[transVal.length];
                        transValues[0] = Integer.parseInt(transVal[0]);
                        transValues[1] = Integer.parseInt(transVal[1]);
                        trpl.addTra(typeBuffer, transValues);
                    }else if(temp.contains("s")){
                        typeBuffer = temp.substring(0, 1);
                        temp = temp.substring(2, temp.length());
                        String[] scaleVal = temp.split(" ");
                        float[] scaleValues = new float[scaleVal.length];
                        scaleValues[0] = Float.parseFloat(scaleVal[0]);
                        scaleValues[1] = Float.parseFloat(scaleVal[1]);
                        scaleValues[2] = Float.parseFloat(scaleVal[2]);
                        scaleValues[3] = Float.parseFloat(scaleVal[3]);
                        trpl.addTra(typeBuffer, scaleValues);
                    }else if(temp.contains("r")){
                        typeBuffer = temp.substring(0, 1);
                        temp = temp.substring(2, temp.length());
                        String[] rotateVal = temp.split(" ");
                        float[] rotateValues = new float[rotateVal.length];
                        rotateValues[0] = Integer.parseInt(rotateVal[0]);
                        rotateValues[1] = Integer.parseInt(rotateVal[1]);
                        rotateValues[2] = Integer.parseInt(rotateVal[2]);
                        trpl.addTra(typeBuffer, rotateValues);
                    }
                }
            }
            
            if(trpl.getPolySize() != 0){
                drawList.add(trpl);
            }
            br.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return drawList;
    }
}
