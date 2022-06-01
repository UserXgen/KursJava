/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sweeper;

import java.io.Serializable;

/**
 *
 * @author student
 */
public enum GameState implements Serializable {
    
    PLAYED,
    BOMBED,
    WINNER,
    READY
    
}
