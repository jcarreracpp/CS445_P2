/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs445.project.pkg2;

/**
 *
 * @author Jorge
 */
public class CS445Project2 {
  public static final int DISPLAY_HEIGHT = 480;
  public static final int DISPLAY_WIDTH = 640;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new InitializeGL();
        new Update();
    }
    
}
