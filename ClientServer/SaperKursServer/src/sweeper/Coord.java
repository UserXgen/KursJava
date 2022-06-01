/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sweeper;

import java.io.Serializable;

/**
 *
 * @author User
 */
public class Coord implements Serializable {
    public int x;
    public int y;
    
    public Coord(int x, int y){
        this.x = x;
        this.y = y;
    
    }
    
    
    @Override
    public boolean equals(Object obj){
    
        if (obj instanceof Coord){
        
            Coord to = (Coord)obj;
            return to.x == x && to.y == y; 
        }
        
        return super.equals(obj);
            
    
    }
}
