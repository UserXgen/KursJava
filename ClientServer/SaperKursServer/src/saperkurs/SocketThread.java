package saperkurs;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import sweeper.Coord;
import sweeper.Game;
import sweeper.Ranges;


class SocketThread extends Thread 
{
    private Socket fromClientSocket;
    private int number;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    private boolean status;
    Game game;

    public boolean IsStatus() {
        return status;
    }
    
   
    
    
 
    public SocketThread(Socket fromClientSocket, int number, Game game) {
        this.fromClientSocket = fromClientSocket;
        this.number = number;
        this.game = game;
        status = true;
       
        
    }
 
    @Override
    public void run() {
        InteractionWithTheClient(game);
    }
    
    public void PressButton(int number, String button, Coord coord) {
       
       
                  
           try {
               oos.writeObject(button);
               oos.writeObject(coord);
           } catch (IOException ex) {
               Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
           }
       
       }
    
    public void InteractionWithTheClient(Game game) {
    
        try {
                        
            oos = new ObjectOutputStream(fromClientSocket.getOutputStream());
            ois = new ObjectInputStream(fromClientSocket.getInputStream());
            
            oos.writeInt(Ranges.GetSize().x);
            oos.writeInt(Ranges.GetSize().y);

            oos.writeObject(game);
            
            
            String str;
            
            Coord coord = new Coord(0,0);
            
            System.out.println("Я живой, я клиент " + number);
            
            while ((str = (String) ois.readObject()) != null) {
                
                switch(str){
                
                    case "Left": 
                        coord = (Coord) ois.readObject();
                    
                        Server.PressButton(number, str, coord);
                        
                    break;
                    
                    case "Right": 
                        coord = (Coord) ois.readObject();
                                       
                        Server.PressButton(number, str, coord);
                        
                    break;
                    
                    case "NgPls": 
                    
                    int cols = Ranges.GetSize().x;
                    int rows = Ranges.GetSize().y;
                    int bombs = game.GetBomb().GetTotalBombs();
                    
                    game = new Game(cols, rows, bombs);
                    game.StartSolo();
                    Server.NewGameRequest(game);
 
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
        } catch (IOException ex) {
            Logger.getLogger(SocketThread.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        catch (ClassNotFoundException ex) {
                Logger.getLogger(SocketThread.class.getName()).log(Level.SEVERE, null, ex);
        }
        
     
        
        }
    
        public void StopConnection(){
            
            if(status == true){
            
                try {
                    status = false;
                    oos.writeObject("Exit");
                    try {
                        Thread.sleep(120);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(SocketThread.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    oos.close();
                    ois.close();
                    fromClientSocket.close();
                    System.out.println("Клиент " + number + " отключился");
                } catch (IOException ex) {
                    Logger.getLogger(SocketThread.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        
        }

        void SendNewGame(Game game) {
            try {
                oos.writeObject("Ng Guys");
                oos.writeObject(game);
            } catch (IOException ex) {
                Logger.getLogger(SocketThread.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    
    }
