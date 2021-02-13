package netThread;


import GamePackage.ControllerPlayer;

public class WriteThread implements Runnable {

    private Thread thr;
    private networkObjStream nc;
    ControllerPlayer c;

    public WriteThread(networkObjStream nc, ControllerPlayer c) {
        this.c=c;
        this.nc = nc;
        this.thr = new Thread(this);
        thr.start();
    }

    public void run() {
        try {
            while(true) {
                nc.write(c.clone());
            }
        } catch(Exception e) {
            System.out.println (e);
        }
    }
}



