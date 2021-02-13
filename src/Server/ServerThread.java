package Server;

import GamePackage.ControllerPlayer;
import GamePackage.Turn;
import netThread.ReadThread;
import netThread.WriteThread;
import netThread.networkObjStream;

import java.net.Socket;


public class ServerThread implements Runnable {

    Socket player1Socket;
    Socket player2Socket;
    Thread t;
    String pl1, pl2, pl3, pl4;
    ControllerPlayer Player2Controller;
    ControllerPlayer Player1Controller;
    String s;
    Turn turn1, turn2;
    public networkObjStream player1Stream;
    public networkObjStream player2Stream;

    ServerThread(Socket player1Socket, Socket player2Socket) {
        s = new String();
        Player2Controller = new ControllerPlayer();
        Player1Controller = new ControllerPlayer();
        this.player1Socket = player1Socket;
        this.player2Socket = player2Socket;
        t = new Thread(this);
        t.start();
        System.out.println("reached to server");
    }

    @Override
    public void run() {

        try {
            player1Stream = new networkObjStream(player1Socket);
            player2Stream = new networkObjStream(player2Socket);

            player1Stream.write("true");
            while(pl1==null){
                pl1 = (String) player1Stream.read();
            }

            player2Stream.write("false");
            while (pl2==null){
                pl2 = (String) player2Stream.read();
            }

            System.out.println("first read");

            player1Stream.write("true");
            while(pl3==null){
                pl3 = (String) player1Stream.read();
            }
            System.out.println("2nd 1st");

            player2Stream.write("true");
            while (pl4==null){
                pl4 = (String) player2Stream.read();
            }
            System.out.println("2nd 2nd");


            //read from player1 and write it to player2
            new ReadThread(player1Stream, Player1Controller, turn1);
            new WriteThread(player1Stream, Player2Controller);
            //read from player2 and write it to player1
            new ReadThread(player2Stream, Player2Controller, turn2);
            new WriteThread(player2Stream, Player1Controller);

            System.out.println("thread is started");

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}


