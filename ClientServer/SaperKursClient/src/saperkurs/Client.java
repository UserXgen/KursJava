/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package saperkurs;
 
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import sweeper.Coord;
import sweeper.Game;

import sweeper.GameState;
 

public class Client extends Thread {

    
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    private Game game;
    private JLabel label; 
    private JPanel panel;
    private Socket socket;
    
    private boolean status;
    
    private int cols;
    private int rows;
    
    private String sIp;
    private int port;

    
    public Client(String ip, int port) {
        
        sIp = ip;
        this.port = port;
        
    }

    public void SetGame(Game game) {
        this.game = game;
    }

    public Game GetGame() {
        return game;
    }
    
    public void SetLabel(JLabel label) {
        this.label = label;
    }
    
        
    public void SetPanel(JPanel panel) {
        this.panel = panel;
    }

    public void run() {
        try {
            WaitingForAction();
        } catch (Exception ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public boolean IsConnected() {
        if((socket != null) && (!socket.isClosed()))
            return socket.isConnected();
        else
            return false;
    }
    
    public void ClientStart() {
    
        try {
            
           
            // socket = new Socket(sIp, port);
            
            socket = new Socket();
            
            socket.connect(new InetSocketAddress(sIp, port), 666);
            
            if(IsConnected()){
                
                oos = new ObjectOutputStream(socket.getOutputStream()); 
                ois = new ObjectInputStream(socket.getInputStream());
                try {

                    System.out.println("Client is started");

                    cols = ois.readInt();
                    rows = ois.readInt();

                    game = new Game(cols, rows); 

                    game = (Game)ois.readObject();
                    System.out.println(game.toString());

                    status = true;

                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                }
            } 
   
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
    
    public void NewGameRequest(){
    
         if(IsConnected()){
                try {
                    oos.writeObject("NgPls");
                } catch (IOException ex) {
                    Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                }
         }
        
        
    
    }
    
    private void WaitingForAction() throws Exception {
    
    
        Coord coord = new Coord(0, 0);
        String str = "123";
        
 
        
        while ((str = (String)ois.readObject()) != null) {
            
            
            switch(str){
            
                case "Left":
                    coord = (Coord) ois.readObject();
                    System.out.println("Получен ответ: " + str + " на " + coord.x + " " + coord.y);
                    game.PressLeftButton(coord);
                    RepaintPanel();

                    break;
                    
                case "Right":
                    coord = (Coord) ois.readObject();
                    System.out.println("Получен ответ: " + str + " на " + coord.x + " " + coord.y);
                    game.PressRightButton(coord);
                    RepaintPanel();

                    break;
                    
                case "Ng Guys":
                    System.out.println("Получен ответ: " + str);
                    game.SetState(GameState.READY);
                    game = (Game)ois.readObject();
                    System.out.println(game.toString());
                    
                    
                    break;
                    
                case "Exit":
                   
                   if(status) 
                    StopConnection();

                   return;
                   
                default: 
                    
                    System.out.println(" Получен необработанный ответ: " + str);
                    
                    break;
            
            }
            
            
        }
        

    }

    
    void RepaintPanel(){
        
        SwingUtilities.invokeLater(new Runnable() {
                            public void run() {
                                label.setText(SaperUI.GetMessage(IsConnected(), game));
                                panel.repaint();
                            }
        });
    
    }
    
    
    void PressButton(String string, Coord coord) {
        if(IsConnected()){
            try {
                oos.writeObject(string);
                oos.writeObject(coord);
            } catch (IOException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    void StopConnection() {
        
        if(IsConnected()){
        
            try {
                status = false;
                oos.writeObject("Exit");
                try {
                        Thread.sleep(120);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                    }
                oos.close();
                ois.close();
                socket.close();
            } catch (IOException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }


}
