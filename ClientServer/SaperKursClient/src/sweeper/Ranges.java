/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sweeper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author User
 */
public class Ranges implements Serializable {
    private static Coord size;
    private static ArrayList<Coord> allCoords;
    private static Random random = new Random();

    
    
    public static void SetSize(Coord oSize){
        
        size = oSize;
        allCoords = new ArrayList<Coord>();
        for(int y = 0; y < size.y; y++)
            for (int x = 0; x < size.x; x++) {
                allCoords.add(new Coord(x, y));
                
            }
    
    }
    
    public static Coord GetSize() {
        return size;
    }
    
    public static ArrayList<Coord> GetAllCoords(){
        return allCoords;
    }
    
    static boolean InRange(Coord coord){
    
        return coord.x >= 0 && coord.x < size.x &&
               coord.y >= 0 && coord.y < size.y;
    }
    
    static Coord GetRandomCoord(){
    
        return new Coord(random.nextInt(size.x), random.nextInt(size.y));
        
    }
    
    static ArrayList<Coord> GetCoordsAround(Coord coord){ // для перебора единиц
    
        Coord around;
        ArrayList<Coord> coordsList = new ArrayList<Coord>();
        
        for (int x = coord.x - 1; x <= coord.x + 1; x++)
            for (int y = coord.y - 1; y <= coord.y + 1; y++) {
                if(InRange(around = new Coord(x, y)))
                    if(!around.equals(coord))
                        coordsList.add(around);
                
            }
    
        
        return coordsList;
    }
    
    
}
