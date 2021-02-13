package GamePackage;
import java.io.Serializable;


public class Turn implements Serializable,Cloneable {
    public boolean turn;

    public Turn(boolean myturn) {
        turn = myturn;
    }

    public synchronized void setTurn(boolean myturn) {
        turn = myturn;
    }

    @Override
    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }



    public synchronized boolean getTurn(){
            return  turn;
    }
}
