/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs445.project.pkg2;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jorge
 */
class TraPol {
    private float[] polyColor = new float[3];
    private List<int[]> polynom = new ArrayList();
    private List<float[]> transfo = new ArrayList();
    private List<String> tranType = new ArrayList();
    
    private void addTType(String type){
        tranType.add(type);
    }
    
    public TraPol(){}
    
    public void addPol(int[] point){
        polynom.add(point);
    }
    
    public void addTra(String type, float[] transformation){
        addTType(type);
        transfo.add(transformation);
    }
    
    public void addPolyColor(float[] x){
        polyColor = x;
    }
    
    public void setTransfo(List<float[]> tf){
        transfo = tf;
    }
    
    public void setTranType(List<String> s){
        tranType = s;
    }
    
    public int getPolySize(){
        return polynom.size();
    }
    
    public int getTranSize(){
        return transfo.size();
    }
    
    public int[] getPointAt(int x){
        return polynom.get(x);
    }
    
    public float[] getTransPointAt(int x){
        return transfo.get(x);
    }
    
    public String getTransTypeAt(int x){
        return tranType.get(x);
    }
    
    public float[] getPolyColor(){
        return polyColor;
    }
    
    public void dumpData(){
        polyColor[0] = 0.0f; polyColor[1] = 0.0f; polyColor[2] = 0.0f;
        polynom.removeAll(polynom);
        transfo.removeAll(transfo);
        tranType.removeAll(tranType);
    }

}
