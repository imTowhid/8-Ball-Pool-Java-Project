package Server;

import netThread.networkObjStream;

import java.net.ServerSocket;
import java.net.Socket;

public class Main
{
    public static void main(String argv[]) throws Exception
    {
        int workerThreadCount = 0;
        ServerSocket welcomeSocket = new ServerSocket(33333);
        Socket player1Socket = null, player2Socket;
        networkObjStream nc;
        int id = 1;
        while(true)
        {
                if(id==1){
                    player1Socket = welcomeSocket.accept();
                    id++;
                }
                else if(id==2){
                    player2Socket = welcomeSocket.accept();
                    WorkerThread wt = new WorkerThread(player1Socket, player2Socket );
                    workerThreadCount++;
                    id=1;
//                    Thread threads= new Thread(wt);
//                    threads.start();
                    wt.run();
                }
            System.out.println("workerThread : "+workerThreadCount);
        }

    }
}

class WorkerThread implements Runnable{

    Socket player1Socket, player2Socket;

    WorkerThread(Socket player1Socket, Socket player2Socket) {
        this.player1Socket = player1Socket;
        this.player2Socket = player2Socket;
    }

    @Override
    public void run() {
        new ServerThread(player1Socket, player2Socket);
    }
}
