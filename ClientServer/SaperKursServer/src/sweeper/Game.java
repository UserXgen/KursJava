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
public class Game implements Serializable {
    
    private Bomb bomb;
 
    private Flag flag;
    
    private GameState gState;
    
    public Game(int cols, int rows){
    
        Ranges.SetSize(new Coord(cols, rows));
        
    }
    
    public Game(int cols, int rows, int bombs){
    
        Ranges.SetSize(new Coord(cols, rows));
        bomb = new Bomb(bombs);
        flag = new Flag();
    }

    public Game() {
        
    }
    
    public void StartSolo(){
    
        bomb.Start();
        flag.Start();
        gState = GameState.PLAYED;
        
    }
    
    public void StartCoop(){
    
        gState = GameState.PLAYED;
        
    }
    
    public Bomb GetBomb() {
        return bomb;
    }

    public Flag GetFlag() {
        return flag;
    }
    
    public void SetState(GameState gState) {
        this.gState = gState;
    }
    
    public void SetBomb(Bomb bomb) {
        this.bomb = bomb;
    }

    public void SetFlag(Flag flag) {
        this.flag = flag;
    }

    public GameState GetState() {
        return gState;
    }
    
    public Box GetBox(Coord coord){
    
        if(flag.Get(coord) == Box.OPENED)
            return bomb.Get(coord);
        else
            return flag.Get(coord);
    }
    
    public void PressLeftButton(Coord coord){
        
        if(GameOver())
            return;
        OpenBox(coord);
        CheckWinner();
       /// flag.SetOpenedToBox(coord);
    }
    
    public void PressRightButton(Coord coord){
    
        if(GameOver())
            return;
        flag.ToggleFlagedToBox(coord);
    }
    
    public void CheckWinner(){
    
        if (gState == GameState.PLAYED)
            if (flag.GetCountOfClosedBoxes() == bomb.GetTotalBombs())
                gState = GameState.WINNER;
    
    }
    
    public void OpenBox(Coord coord){
    
        switch(flag.Get(coord)){
        
            case OPENED: SetOpenedToClosedBoxesAroundNumber(coord); return;
            
            case FLAGED: return;
                
            case CLOSED: 
                switch(bomb.Get(coord)){
            
                case ZERO : OpenBoxesAround(coord); return;
                case BOMB : OpenBombs(coord); return;
                default   : flag.SetOpenedToBox(coord); return;
            
            }
        

        }
    
    
    }
    
    private void OpenBombs(Coord coordBombed) {
        gState = GameState.BOMBED;
        flag.SetBombedToBox(coordBombed);
        
        for(Coord coord : Ranges.GetAllCoords())
             if(bomb.Get(coord) == Box.BOMB)
                 flag.SetOpenedToClosedBombBox(coord);
             else
                 flag.SetNoBombToFlagedSafeBox(coord);
        
    }
    
    private void OpenBoxesAround(Coord coord) {
        flag.SetOpenedToBox(coord);
        for(Coord around : Ranges.GetCoordsAround(coord))
            OpenBox(around);
    }

    private boolean GameOver() {
        if(gState == GameState.PLAYED)
            return false;
        System.out.println("Я подорвался");
        
        return true;
            
    }
    
    void SetOpenedToClosedBoxesAroundNumber(Coord coord) {
        
        if(bomb.Get(coord) != Box.BOMB)
            if(flag.GetCountOfFlagedBoxesAround(coord) == bomb.Get(coord).GetNumber())
                for(Coord around : Ranges.GetCoordsAround(coord))
                    if (flag.Get(around) == Box.CLOSED)
                        OpenBox(around);
            
    }

}
