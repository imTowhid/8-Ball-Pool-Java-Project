package BallandCue;


public class BallBundle {
    double solidballx;
    double solidbally;
    double stripeballx;
    double stripbally;
    int solidballsize;
    int stripeballsize;

    BallBundle(){
        solidballx=322+50;
        solidbally=626;

        stripbally=626;
        stripeballx=576+50;
        solidballsize=0;
        stripeballsize=0;
    }

    public void pushSolidball(Ball ball){
        ball.setVx(0);
        ball.setVy(0);
        ball.sphere.setLayoutX(solidballx);
        ball.sphere.setLayoutY(solidbally);
        solidballx-=20;
        solidballsize++;
    }


    public void pushStripeball(Ball ball){
        ball.setVx(0);
        ball.setVy(0);
        ball.sphere.setLayoutX(stripeballx);
        ball.sphere.setLayoutY(stripbally);
        stripeballx+=20;
        stripeballsize++;
    }

}

