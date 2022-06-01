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
public class Bomb implements Serializable {
    
    private Matrix bombMap;

    private int totalBombs;
    
    Bomb(int totalBombs){
        
        this.totalBombs = totalBombs;
        FixBombsCount();
        
    }
    
    void Start(){
    
        bombMap = new Matrix(Box.ZERO);
        for (int i = 0; i < totalBombs; i++) {
            
            PlaceBomb();
            
        }
        
    }
    
        
    Box Get (Coord coord){
    
        return bombMap.Get(coord);
    
    }
    
    private void FixBombsCount (){
    
        int maxBombs = Ranges.GetSize().x * Ranges.GetSize().y / 2;
        if(totalBombs > maxBombs)
            totalBombs = maxBombs;
    
    }
    
    private void PlaceBomb(){
    
        while(true){
            
            Coord rCoord = Ranges.GetRandomCoord();
            if(bombMap.Get(rCoord) == Box.BOMB)
                continue;
            
            bombMap.Set(rCoord, Box.BOMB);
            IncNumbersAroundBomb(rCoord);
            break;

        }
        
        
       
    
    }
    
    private void IncNumbersAroundBomb(Coord coord){
    
        
        /*for (Coord around : Ranges.GetCoordsAround(coord))
            if(Box.BOMB != bombMap.Get(around))
            bombMap.Set(around, bombMap.Get(around).GetNextNumberBox()); */
        
        Ranges.GetCoordsAround(coord).stream().filter((around) -> (Box.BOMB != bombMap.Get(around))).forEachOrdered((around) -> {
            bombMap.Set(around, bombMap.Get(around).GetNextNumberBox());
        });
    
    }

    public int GetTotalBombs() {
        return totalBombs;
    }
    
    
}
