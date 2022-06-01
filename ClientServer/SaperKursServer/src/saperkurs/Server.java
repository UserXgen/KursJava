/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package saperkurs;

/**
 *
 * @author User
 */ 
 

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import sweeper.Coord;
import sweeper.Game;
 

public class Server extends Thread {
    
    static ArrayList<SocketThread> sockets;

    ServerSocket servSocket;
    private static Game game;
    private int bombs;
    private int cols;
    private int rows;
    int port;
    private String sIp;
    
    
    public Server(int cols, int rows, int bombs, int port) {
        this.cols = cols;
        this.rows = rows;
        this.bombs = bombs;
       // sIp = ip;
        this.port = port;
        
    }
    
    public void run() {
        
        Server();
        
    }
    
    public void Server()  {

       // port = 1777;
 
        try {
            
            servSocket = new ServerSocket(port);
            
            sockets = new ArrayList<SocketThread>();
            NewGame(cols, rows, bombs);
            
            int countSocket = 0;
            while (true) {
                System.out.println("Ждём подключения к порту " + port);
                Socket fromClientSocket = servSocket.accept();
                if(IsWorking()){
                    sockets.add(new SocketThread(fromClientSocket, countSocket, game));
                    sockets.get(countSocket).start();

                    countSocket++;
                }
                else{
                    return;
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace(System.out);
        }
    }
    
    public Game NewGame(int cols, int rows, int bombs){
    
        game = new Game(cols, rows, bombs);
        game.StartSolo();
        
        return game;
    
    }
    
    
   public static void NewGameRequest(Game game) {
       
       Server.game = game;
       
       for(int i = 0; i < sockets.size(); i++){
           
               if(sockets.get(i).IsStatus() == true){
                    System.out.println(" Отправлен запрос на нг " + i);
                    sockets.get(i).SendNewGame(game);
               }
           }
       
           
    }
   
    
   public static void PressButton(int number, String button, Coord coord) {
       
       System.out.println(" Клиент " + number + " сделал " + " " + button + " на " + coord.x + " " + coord.y);
       
       for(int i = 0; i < sockets.size(); i++){
           
           if(i != number){
               if(sockets.get(i).IsStatus() == true){
              //  System.out.println(" Обнаружен клиент " + i);
                sockets.get(i).PressButton(number, button, coord);
               }
           }
       
           
       }
   }
   
   public void StopServer() {
        
       if(IsWorking()){
        for(int i = 0; i < sockets.size(); i++){

                if(sockets.get(i).IsStatus() == true){
                  sockets.get(i).StopConnection();
                }
            }
       }
       
        try {
            servSocket.close();
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
        
       
    }
   
   public boolean IsWorking(){
   
       if((servSocket != null))
           return !servSocket.isClosed();
       else
           return false;
   
   }
       
}
 


    
    

