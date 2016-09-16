/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Thesis;

import ec.Evolve;

/**
 *
 * @author arus2
 */
public class MainClass {
     /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        String[] HelloWorld;
        HelloWorld = new String[] {"-file","src\\Chars\\chars.params"};
        //file = new String[] {"-file","test\\ec\\app\\tutorial4\\tutorial4.params"};
        
        Evolve.main(HelloWorld);
        
    }
}
