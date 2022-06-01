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
public enum Box implements Serializable {
    
    ZERO,
    NUM1,
    NUM2,
    NUM3,
    NUM4,
    NUM5,
    NUM6,
    NUM7,
    NUM8,
    BOMB,
    OPENED,
    CLOSED,
    FLAGED,
    BOMBED,
    NOBOMB;
    
    public Object image;
    
    Box GetNextNumberBox (){
    
        return Box.values()[this.ordinal() + 1];
    
    }
    
    int GetNumber(){
    
        return this.ordinal();
    
    }
    
    
}
