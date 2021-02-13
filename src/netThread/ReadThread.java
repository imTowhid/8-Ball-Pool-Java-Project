package netThread;


import GamePackage.ControllerPlayer;
import GamePackage.Turn;

public class ReadThread implements Runnable {

    private Thread thr;
    private networkObjStream nc;
    ControllerPlayer con;
    ControllerPlayer c;
    Turn turn;
    public ReadThread(networkObjStream nc, ControllerPlayer c, Turn myturn) {
        con=new ControllerPlayer();
        this.c=c;
        this.nc = nc;
        this.thr = new Thread(this);
        thr.start();
        turn=myturn;
    }

    public void run() {
        try {
            while(true) {

                Object o=nc.read();

                if(o instanceof ControllerPlayer){
                    con=(ControllerPlayer)o;

                    if(con==null){
                        System.out.println("failed to read ");
                    }
                    else{
                        c.setValue(con.getValue());
                        c.setAngle(con.getAngle());
                        c.setLayoutx(con.getLayoutx());
                        c.setLayouty(con.getLayouty());
                        c.setInitial_v(con.getInitial_v());
                        c.setLineEnlarge(con.getLineEnlarge());
                   }

                    }


            }
        } catch(Exception e) {
            System.out.println (e);
        }

    }
}






