package cs445.project.pkg2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * file: TraPol.java
 * author: Jorge Luis Carrera
 * class: CS 445 - Computer Graphics
 * 
 * assignment: Program 2
 * date last modified: 11/1/2017
 * 
 * purpose: This program takes input from a file, then applies transformations
 * to a polygon given by points. The transformed polygons are then drawn.
 */
class TraPol {
    private float[] polyColor = new float[3];
    private List<int[]> polynom = new ArrayList();
    private List<float[]> transfo = new ArrayList();
    private List<float[]> allEdgeTable = new ArrayList();
    private List<String> tranType = new ArrayList();
    private float horizOffset;
    private float vertOffset;
    

    //Method: TraPol
    //Purpose: Empty constructor to hold the private variables and methods.
    public TraPol(){}
    
    //Method: addTType
    //Purpose: Adds a new transformation type entry. Must be added with
    //appropriate values of a transformation at the same index number for this
    //instruction to be interpreted correctly.
    private void addTType(String type){
        tranType.add(type);
    }
    
    //Method: addPol
    //Purpose: Adds a new int array that is presumably size of 2, the x and y
    //of a specific point.
    public void addPol(int[] point){
        polynom.add(point);
    }
    
    //Method: addTra
    //Purpose: Adds a new transformation. Calls addTType to associate the values
    //with a specific transformation.
    public void addTra(String type, float[] transformation){
        addTType(type);
        transfo.add(transformation);
    }
    
    //Method: addPolyColor
    //Purpose: Saves point colors in the form of a size 3 float array.
    public void addPolyColor(float[] x){
        polyColor = x;
    }
    
    //Method: organizeEdgesTable
    //Purpose: Initial organization of edge tables for scanline fill algorithm.
    // Organizes first by y-min, then x-val, then y-max, and finally, inverse
    //slope.
    public void organizeEdgesTable() {

        for (int i = 0; i < allEdgeTable.size() - 1; i++) {
            int minimum = i;
            for (int j = i + 1; j < allEdgeTable.size(); j++) {
                if (allEdgeTable.get(j)[0] != allEdgeTable.get(minimum)[0]) {
                    if (allEdgeTable.get(j)[0] < allEdgeTable.get(minimum)[0]) {
                        minimum = j;
                    } else {
                    }
                } else if (allEdgeTable.get(j)[2] != allEdgeTable.get(minimum)[2]) {
                    if (allEdgeTable.get(j)[2] < allEdgeTable.get(minimum)[2]) {
                        minimum = j;
                    } else {
                    }
                } else if (allEdgeTable.get(j)[1] != allEdgeTable.get(minimum)[1]) {
                    if (allEdgeTable.get(j)[1] < allEdgeTable.get(minimum)[1]) {
                        minimum = j;
                    } else {
                    }
                } else if (allEdgeTable.get(j)[3] != allEdgeTable.get(minimum)[3]) {
                    if (allEdgeTable.get(j)[3] < allEdgeTable.get(minimum)[3]) {
                        minimum = j;
                    } else {
                    }
                } else {
                }
            }
            Collections.swap(allEdgeTable, i, minimum);
        }
    }
    
    //Method: calcEffectiveEdges
    //Purpose: Calculates appropriate global edge table from all edges.
    public void calcEffectiveEdges(){
        for(int i = 0; i < polynom.size(); i ++){
            float[] newEdge = new float[4];
            if(i == polynom.size()-1){
                
            }else{
                if(polynom.get(i)[1] < polynom.get(i+1)[1]){
                    newEdge[0] = polynom.get(i)[1];
                    newEdge[1] = polynom.get(i+1)[1];
                    newEdge[2] = polynom.get(i)[0];
                }else{
                    newEdge[0] = polynom.get(i+1)[1];
                    newEdge[1] = polynom.get(i)[1];
                    newEdge[2] = polynom.get(i+1)[0];
                }
                if((polynom.get(i)[1] - polynom.get(i+1)[1]) == 0){
                }else{
                    newEdge[3] = ((float)(polynom.get(i)[0] - (float)polynom.get(i+1)[0]) / (float)(polynom.get(i)[1] - (float)polynom.get(i+1)[1]));
                    allEdgeTable.add(newEdge);
                }
            }
        }
    }
    
    //Method: calcOffset
    //Purpose: Calculates the midpoint for x and y of a given polygon.
    public void calcOffset(){
        float smx;
        float smy;
        float lrx;
        float lry;
        
        if(polynom.size()==1){
            horizOffset = polynom.get(0)[0];
            horizOffset /= 2;
            vertOffset = polynom.get(0)[1];
            vertOffset /= 2;
        }else{
            smx = polynom.get(0)[0];
            lrx = polynom.get(0)[0];
            smy = polynom.get(0)[1];
            lry = polynom.get(0)[1];
            for(int i = 1; i < polynom.size(); i++){
                if(polynom.get(i)[0] < smx)
                    smx = polynom.get(i)[0];
                if(polynom.get(i)[0] > lrx)
                    lrx = polynom.get(i)[0];
                if(polynom.get(i)[1] < smy)
                    smy = polynom.get(i)[1];
                if(polynom.get(i)[1] > lry)
                    lry = polynom.get(i)[1];
            }
            horizOffset = ((smx + lrx)/2);
            vertOffset = ((smy + lry)/2);
        }
    }
    
    //Method: setTransfo
    //Purpose: Replaces the current transformations list.
    public void setTransfo(List<float[]> tf){
        transfo = tf;
    }
    
    //Method: setTranType
    //Purpose: Replaces the current transformation types list.
    public void setTranType(List<String> s){
        tranType = s;
    }
    
    //Method: getAllEdgesTable
    //Purpose: Returns a specific edge from the all edges table.
    public float[] getAllEdgesTable(int i){
        return allEdgeTable.get(i);
    }
    
    //Method: getTable
    //Purpose: Returns the entire all edges table.
    public List<float[]> getTable(){
        return allEdgeTable;
    }
    
    //Method: getPolySize
    //Purpose: Returns the size of the point list.
    public int getPolySize(){
        return polynom.size();
    }
    
    //Method: getTranSize
    //Purpose: Returns the size of the transformations list.
    public int getTranSize(){
        return transfo.size();
    }
    
    //Method: getPointAt
    //Purpose: Returns a point from the points list at a given index.
    public int[] getPointAt(int x){
        return polynom.get(x);
    }
    
    //Method: getTransPointAt
    //Purpose: Returns the values for a transformation from the transformations
    //list at a given index.
    public float[] getTransPointAt(int x){
        return transfo.get(x);
    }
    
    //Method: getTransTypeAt
    //Purpose: Returns the type of transformation from the transformation types
    //list at a given index.
    public String getTransTypeAt(int x){
        return tranType.get(x);
    }
    
    //Method: getPolyColor
    //Purpose: Returns the stored color array.
    public float[] getPolyColor(){
        return polyColor;
    }
    
    //Method: getVertOffset
    //Purpose: Returns the stored vertical offset value.
    public float getVertOffset(){
        return vertOffset;
    }
    
    //Method: getHorizOffset
    //Purpose: Returns the stored horizontal offset value.
    public float getHorizOffset(){
        return horizOffset;
    }
}
