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
class Flag implements Serializable {
    
    private Matrix flagMap;
    private int countOfClosedBoxes;
    
    void Start(){
    
        flagMap = new Matrix(Box.CLOSED);
        countOfClosedBoxes = Ranges.GetSize().x * Ranges.GetSize().y;       
    
    }
    
    Box Get (Coord coord){
    
        return flagMap.Get(coord);
    
    }

    void SetOpenedToBox(Coord coord) {
        
        flagMap.Set(coord, Box.OPENED);
        countOfClosedBoxes--;
    }

    void ToggleFlagedToBox (Coord coord){
    
        switch(flagMap.Get(coord)){

            case FLAGED : SetClosedToBox(coord); break;
            case CLOSED : SetFlagedToBox(coord); break;
        
        }
        
    }
    
    void SetFlagedToBox(Coord coord) {
        
        flagMap.Set(coord, Box.FLAGED);
        
    }

    void SetClosedToBox(Coord coord) {
        
        flagMap.Set(coord, Box.CLOSED);
    }

    int GetCountOfClosedBoxes() {
        return countOfClosedBoxes;
    
    }

    void SetBombedToBox(Coord coord) {
        flagMap.Set(coord, Box.BOMBED);
       
    }

    void SetOpenedToClosedBombBox(Coord coord) {
       if(flagMap.Get(coord) == Box.CLOSED)
           flagMap.Set(coord, Box.OPENED);
    }

    void SetNoBombToFlagedSafeBox(Coord coord) {
        if(flagMap.Get(coord) == Box.FLAGED)
           flagMap.Set(coord, Box.NOBOMB);
    }
    
    int GetCountOfFlagedBoxesAround(Coord coord) {
        
        int count = 0;
        for(Coord around : Ranges.GetCoordsAround(coord))
            if(flagMap.Get(around) == Box.FLAGED)
                count++;
        
        return count;
    }
}