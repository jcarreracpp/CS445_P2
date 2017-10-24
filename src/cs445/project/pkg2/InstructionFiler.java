/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs445.project.pkg2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jorge
 */
public class InstructionFiler {
    private String fileName = "coordinates.txt";
    private File file = new File(fileName);
    private List<TraPol> drawList = new ArrayList();
    private TraPol trpl = new TraPol();
    
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
                        //System.out.println("Point "+ point[0] + " "+point[1]);
                        pointVal[0] = Integer.parseInt(point[0]);
                        pointVal[1] = Integer.parseInt(point[1]);
                        trpl.addPol(pointVal);
                    }
                }
                if(temp.contains("P") && !polPassed){
                    temp = temp.substring(2, temp.length());
                    String[] colorVal = temp.split("\\s+");
                    //System.out.println("Color "+temp);
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
                        //System.out.println("TAMP: " + temp);
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
        orgTrans();
        return drawList;
    }

    private void orgTrans() {
        for(int i = 0; i < drawList.size(); i++){
            List<float[]> tranBuffer = new ArrayList();
            List<String> typeBuffer = new ArrayList();
            for(int j = 0; j < drawList.get(i).getTranSize(); j++){
                if(drawList.get(i).getTransTypeAt(j).contains("r") || drawList.get(i).getTransTypeAt(j).contains("s")){
                    tranBuffer.add(drawList.get(i).getTransPointAt(j));
                    typeBuffer.add(drawList.get(i).getTransTypeAt(j));
                }
            }
            for(int k = 0; k < drawList.get(i).getTranSize(); k++){
                if(drawList.get(i).getTransTypeAt(k).contains("t")){
                    tranBuffer.add(drawList.get(i).getTransPointAt(k));
                    typeBuffer.add(drawList.get(i).getTransTypeAt(k));
                }
            }
            drawList.get(i).setTransfo(tranBuffer);
            drawList.get(i).setTranType(typeBuffer);
        }
    }
}
