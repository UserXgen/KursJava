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
class Matrix implements Serializable {
    
    private Box[][] matrix;
    
    Matrix(Box defaultBox){
        matrix = new Box[Ranges.GetSize().x][Ranges.GetSize().y];
        
        for(Coord coord : Ranges.GetAllCoords())
            matrix [coord.x] [coord.y] = defaultBox;
        
    }
    
    Box Get(Coord coord)
    {
        if(Ranges.InRange(coord))
            return matrix[coord.x][coord.y];
        else
            return null;
    }
    
    void Set(Coord coord, Box box){
        
        if(Ranges.InRange(coord))
            matrix[coord.x][coord.y] = box;
    }
    
}
