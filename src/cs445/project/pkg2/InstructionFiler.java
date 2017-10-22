/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs445.project.pkg2;

import java.io.File;
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
    
    public void processFile(){
        
    }
}
