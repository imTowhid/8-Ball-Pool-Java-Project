package BallandCue;

import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.transform.Rotate;
import GamePackage.StartGame;
import GamePackage.Turn;

public class BallControll {
    Group Store;
    Scene scene;

    private Ball[] ball;
    Text Message;
    public Circle Indicator;
    StartGame startGame;
    BallBundle ballBundle;

    Rectangle Direction;

    public Turn turn=new Turn(true);
    public HandleCue cueStick;

    double angle;
    double a=0;
    double InitialVelocity =0;

    boolean flag = true;
    boolean isBound=true;
    boolean ismyturn;
    boolean CueBallVelocity = false;
    boolean OpCueBallVelocity =false;
    public boolean isgameOver=false;
    public boolean isplay;
    public boolean isFaul=true;

    public int myballtype;
    int solidBallCount;
    int stripeBallCount;


    //image files source path
    String[] imageName = {"Extras\\1.png", "Extras\\2.png", "Extras\\3.png",
            "Extras\\4.png", "Extras\\5.png", "Extras\\6.png", "Extras\\7.png",
            "Extras\\8.png", "Extras\\9.png", "Extras\\10.png", "Extras\\11.png",
            "Extras\\12.png", "Extras\\13.png", "Extras\\14.png", "Extras\\15.png",
            "Extras\\cueball.png",
    };


    public BallControll(Group root, Scene scene) {
        this.scene=scene;
        Store = root;

        ballBundle = new BallBundle();
        cueStick = new HandleCue(root,scene);

        ball = new Ball[16];

        //initial position of ball(billard setup)
        ball = new Ball[16];
        int bx=704, by=350;

        ball[0] = new Ball(root, bx, by, imageName[0], Color.WHITE);
        ball[1] = new Ball(root, bx+21, by+11, imageName[1], Color.WHITE);
        ball[12] = new Ball(root, bx+21, by-11, imageName[12], Color.WHITE);
        ball[7] = new Ball(root, bx+42, by, imageName[7], Color.WHITE);
        ball[8] = new Ball(root, bx+42, by+22, imageName[8], Color.WHITE);
        ball[13] = new Ball(root, bx+42, by-22, imageName[13], Color.WHITE);
        ball[14] = new Ball(root, bx+63, by+11, imageName[14], Color.WHITE);
        ball[6] = new Ball(root, bx+63, by-11, imageName[6], Color.WHITE);
        ball[9] = new Ball(root, bx+63, by+33, imageName[9], Color.WHITE);
        ball[5] = new Ball(root, bx+63, by-33, imageName[5], Color.WHITE);
        ball[10] = new Ball(root, bx+84, by, imageName[10], Color.WHITE);
        ball[3] = new Ball(root, bx+84, by+22, imageName[3], Color.WHITE);
        ball[11] = new Ball(root, bx+84, by-22, imageName[11], Color.WHITE);
        ball[2] = new Ball(root, bx+84, by+44, imageName[2], Color.WHITE);
        ball[4] = new Ball(root, bx+84, by-44, imageName[4], Color.WHITE);


        //cueBall initial position
        ball[15] = new Ball(root, 300, 350, imageName[15], Color.WHITE);

        //setting id of balls // solids==0 // stripes==1 // 8th==8 // cueball==10
        for (int i = 0; i < 7; i++) {
            ball[i].setIdofBall(0);
        }
        ball[7].setIdofBall(8);

        for (int i =8 ; i < 15; i++) {
            ball[i].setIdofBall(1);
        }
        ball[15].setIdofBall(10);

        //direction line
        Direction =new Rectangle();
        Direction.setWidth(1.7);
        Direction.setFill(Color.AQUA);
        Direction.getTransforms().add(new Rotate(-90,Rotate.Z_AXIS));

        //Game Instruction
        Message = new Text("");
        Message.setFont(Font.font("Calibri", FontWeight.BOLD,12));
        DropShadow t=new DropShadow();
        Message.setFill(Color.WHITE);
        Message.setLayoutX(440);
        Message.setLayoutY(80);
        Message.setTextAlignment(TextAlignment.CENTER);

        Message.setEffect(t);
        Direction.setEffect(t);
        root.getChildren().addAll(Direction, Message);
    }


    public boolean isVelocityZero() {
        int i = 0;
        while (ball[i].getV() <= 0.07) {
            i++;
            if (i == 16) {
                return true;
            }
        }
        return false;

    }

    public void BallPackage_Add(StartGame text){
        this.startGame =text;
    }


    public void UpdateState(Scene scene) {

        if(turn.getTurn()) {
            ball[15].sphere.setOnMouseDragged(event ->
            {
                if (isBoundedbyPool() && isFaul) {
                    flag = false;
                    Store.getChildren().remove(cueStick.getStick());
                    Store.getChildren().remove(Indicator);
                    Store.getChildren().remove(cueStick.line);
                    ball[15].sphere.setCursor(Cursor.CLOSED_HAND);
                    ball[15].sphere.setLayoutX(event.getSceneX());
                    ball[15].sphere.setLayoutY(event.getSceneY());
                    isBoundedbyPool();
                }

                ball[15].sphere.setOnMouseReleased(event1 -> {
                    Store.getChildren().remove(cueStick.getStick());
                    Store.getChildren().remove(cueStick.line);
                    Store.getChildren().remove(Indicator);
                    flag = true;
                    isBoundedbyPool();
                });
            });
        }

        if (flag == true) {
            cueStick = new HandleCue(Store,scene);
            Indicator=new Circle();
            Indicator.setRadius(10);
            Indicator.setFill(Color.TRANSPARENT);
            Indicator.setStroke(Color.AQUA);
            Indicator.setStrokeWidth(2);
            Indicator.setStrokeType(StrokeType.INSIDE);
            DropShadow d=new DropShadow();
            Indicator.setEffect(d);
            Store.getChildren().add(Indicator);
            Stick();
            flag = false;
            Indicator.setLayoutX(ball[15].sphere.getLayoutX());
            Indicator.setLayoutY(ball[15].sphere.getLayoutY());
        }

        if(turn.getTurn()) {
            cueStick.MoveStick(ball[15].sphere.getLayoutX(), ball[15].sphere.getLayoutY());
        }
        if(turn.getTurn()) {
            cueStick.RotateStick(scene, ball[15].sphere.getLayoutX(), ball[15].sphere.getLayoutY());
        }
    }

   public  void Stick() {
            cueStick.InitializeStick(ball[15].sphere.getLayoutX(), ball[15].sphere.getLayoutY());
    }


    public void setVelocityofCueball() {
            cueStick.setIniVelocity();
            if ((turn.getTurn() && cueStick.getIniVelocity() >1  && !CueBallVelocity)) {
                isFaul = false;
                double angle = (cueStick.getAngle() + 180) % 360;
                ball[15].setAngle(Math.toRadians(angle));
                ball[15].setV(cueStick.getIniVelocity());

                ball[15].setVx((ball[15].getV() * Math.cos((ball[15].getAngle()))));
                ball[15].setVy((ball[15].getV() * Math.sin((ball[15].getAngle()))));
                ball[15].setAx((ball[15].getA() * Math.cos((ball[15].getAngle()))));
                ball[15].setAy(ball[15].getA() * Math.sin((ball[15].getAngle())));
                CueBallVelocity = true;
                solidBallCount = ballBundle.solidballsize;
                stripeBallCount = ballBundle.stripeballsize;
            }

        if ((!turn.getTurn() && getInitialVelocity()>1  && !OpCueBallVelocity)) {
            isFaul = false;
            double angle = (cueStick.getAngle() + 180) % 360;
            ball[15].setAngle(Math.toRadians(angle));
            ball[15].setV(getInitialVelocity());

            ball[15].setVx((ball[15].getV() * Math.cos((ball[15].getAngle()))));
            ball[15].setVy((ball[15].getV() * Math.sin((ball[15].getAngle()))));
            ball[15].setAx((ball[15].getA() * Math.cos((ball[15].getAngle()))));
            ball[15].setAy(ball[15].getA() * Math.sin((ball[15].getAngle())));
            OpCueBallVelocity = true;
            solidBallCount = ballBundle.solidballsize;
            stripeBallCount = ballBundle.stripeballsize;
        }
    }

    public void cueBallVelocityControll() {
        if (CueBallVelocity) {
            for (int i = 0; i < 16; i++) {
                Store.getChildren().remove(Indicator);
                Store.getChildren().remove(cueStick.getStick());
                Store.getChildren().remove(cueStick.line);


                ball[i].setV(Math.sqrt(Math.pow(ball[i].getVx(), 2) + Math.pow(ball[i].getVy(), 2)));

                if (ball[i].getV() > 0.07) {
                    ball[i].setVx(ball[i].getVx() - (ball[i].getAx() * ball[i].getTime()));
                    ball[i].setVy(ball[i].getVy() - (ball[i].getAy() * ball[i].getTime()));

                    ball[i].setTime(ball[i].getTime() + .00005);

                    ball[i].sphere.setLayoutX(ball[i].sphere.getLayoutX() + ball[i].getVx());
                    ball[i].sphere.setLayoutY(ball[i].sphere.getLayoutY() + ball[i].getVy());

                }

                if (ball[i].getV() <= 0.07) {
                    ball[i].setTime(0);
                    ball[i].setAngle(0);
                    ball[i].setVx(0);
                    ball[i].setVy(0);
                }
            }
            if (isVelocityZero()) {
                cueStick.setIniVelocity(0);
                flag = true;
                CueBallVelocity = false;
                System.out.println("velocity of balls is 0");
                if(turn.getTurn() && myballtype==0 && ballBundle.solidballsize== solidBallCount) {
                    turn.setTurn(false);
                }

                else  if(turn.getTurn() && myballtype==1 && ballBundle.stripeballsize== stripeBallCount) {
                     turn.setTurn(false);
                }

                else startGame.setText("great");
            }
        }

        if (OpCueBallVelocity) {
            for (int i = 0; i < 16; i++) {

                Store.getChildren().remove(Indicator);
                Store.getChildren().remove(cueStick.getStick());
                Store.getChildren().remove(cueStick.line);


                ball[i].setV(Math.sqrt(Math.pow(ball[i].getVx(), 2) + Math.pow(ball[i].getVy(), 2)));

                if (ball[i].getV() > 0.07) {
                    ball[i].setVx(ball[i].getVx() - (ball[i].getAx() * ball[i].getTime()));
                    ball[i].setVy(ball[i].getVy() - (ball[i].getAy() * ball[i].getTime()));

                    ball[i].setTime(ball[i].getTime() + .00005);

                    ball[i].sphere.setLayoutX(ball[i].sphere.getLayoutX() + ball[i].getVx());
                    ball[i].sphere.setLayoutY(ball[i].sphere.getLayoutY() + ball[i].getVy());

                }

                if (ball[i].getV() <= 0.07) {
                    ball[i].setTime(0);
                    ball[i].setAngle(0);
                    ball[i].setVx(0);
                    ball[i].setVy(0);
                }
            }
            if (isVelocityZero()) {

                setInitialVelocity(0);
                flag = true;
                OpCueBallVelocity = false;

                if(!turn.getTurn() && myballtype==1 && ballBundle.solidballsize== solidBallCount) {
                    turn.setTurn(true);
                }

                else  if(!turn.getTurn() && myballtype==0 && ballBundle.stripeballsize== stripeBallCount) {
                    turn.setTurn(true);
                }

            }
        }
    }

    public void detectWallCollision() {

        for (int i = 0; i < 16; i++) {
            //1: upper left side border
            if (ball[i].sphere.getLayoutX() >= 125 && ball[i].sphere.getLayoutX() <= 475) {
                if ((ball[i].sphere.getLayoutY() - 150 <= 11)) {
                    ball[i].setVy(-(ball[i].getVy()));
                    ball[i].setAy(-(ball[i].getAy()));
                    UpdateAngle(ball[i]);
                }
            }

            //2: upper right side border
            if (ball[i].sphere.getLayoutX() >= 525 && ball[i].sphere.getLayoutX() <= 875) {
                if ((ball[i].sphere.getLayoutY() - 150 <= 11)) {
                    ball[i].setVy(-(ball[i].getVy()));
                    ball[i].setAy(-(ball[i].getAy()));
                    UpdateAngle(ball[i]);
                }
            }

            //3: lower left side border
            if (ball[i].sphere.getLayoutX() >= 125 && ball[i].sphere.getLayoutX() <= 474) {
                if ((550 - ball[i].sphere.getLayoutY()) <= 11) {
                    ball[i].setVy(-(ball[i].getVy()));
                    ball[i].setAy(-(ball[i].getAy()));
                    UpdateAngle(ball[i]);
                }
            }

            //4: lower right side border
            if (ball[i].sphere.getLayoutX() >= 525 && ball[i].sphere.getLayoutX() <= 875) {
                if ((-ball[i].sphere.getLayoutY() + 550) <= 11) {
                    ball[i].setVy(-(ball[i].getVy()));
                    ball[i].setAy(-(ball[i].getAy()));
                    UpdateAngle(ball[i]);
                }
            }

            //5: vertical left side border
            if (ball[i].sphere.getLayoutY() >= 175 && ball[i].sphere.getLayoutY() <= 525) {
                if ((ball[i].sphere.getLayoutX() - 100) <= 11) {
                    ball[i].setVx(-(ball[i].getVx()));
                    ball[i].setAx(-(ball[i].getAx()));
                    UpdateAngle(ball[i]);
                }
            }

            //6: vertical right side border
            if (ball[i].sphere.getLayoutY() >= 175 && ball[i].sphere.getLayoutY() <= 525) {
                if ((-ball[i].sphere.getLayoutX() + 900) <= 11) {
                    ball[i].setVx(-(ball[i].getVx()));
                    ball[i].setAx(-(ball[i].getAx()));
                    UpdateAngle(ball[i]);
                }
            }
        }
    }


    public void handleBalltoBallCollision() {
        double xDist, yDist;
        for (int i = 0; i < 16; i++) {
            Ball A = ball[i];
            for (int j = i + 1; j < 16; j++) {
                Ball B = ball[j];
                xDist = A.sphere.getLayoutX() - B.sphere.getLayoutX();
                yDist = A.sphere.getLayoutY() - B.sphere.getLayoutY();
                double distSquared = xDist * xDist + yDist * yDist;
                if (distSquared <= 20 * 20) {
                    double xVelocity = B.getVx() - A.getVx();
                    double yVelocity = B.getVy() - A.getVy();
                    double dotProduct = xDist * xVelocity + yDist * yVelocity;
                    if (dotProduct > 0) {
                        double collisionScale = dotProduct / distSquared;
                        double xCollision = xDist * collisionScale;
                        double yCollision = yDist * collisionScale;
                        double collisionWeightA = 1;
                        double collisionWeightB = 1;

                        A.setVx((collisionWeightA * xCollision) + A.getVx());
                        A.setVy(collisionWeightA * yCollision + A.getVy());
                        B.setVx(B.getVx() - collisionWeightB * xCollision);
                        B.setVy(B.getVy() - collisionWeightB * yCollision);

                        A.setAngle(Math.toDegrees(Math.atan2(A.getVy(), A.getVx())));
                        B.setAngle(Math.toDegrees(Math.atan2(B.getVy(), B.getVx())));

                        if (A.getAngle() < 0) {
                            A.setAngle((A.getAngle() + 360));
                        }
                        if (B.getAngle() < 0) {
                            B.setAngle((B.getAngle() + 360));
                        }


                        A.setAx(A.getA() * Math.cos(Math.toRadians(A.getAngle())));
                        A.setAy(A.getA() * Math.sin(Math.toRadians(A.getAngle())));

                        B.setAx(B.getA() * Math.cos(Math.toRadians(B.getAngle())));
                        B.setAy(B.getA() * Math.sin(Math.toRadians(B.getAngle())));


                    }
                }
            }
        }
    }


    public void detectBallPocketEdgeCollision() {

        for (int i = 0; i < 16; i++) {
            //upside left horizontal
            if (ball[i].sphere.getLayoutX() >= 98 && ball[i].sphere.getLayoutX() < 125) {
                if (isTouchtoPocketEdge(15, -20, 1125, ball[i].sphere.getLayoutX(), ball[i].sphere.getLayoutY())) {
                    pocketHorizontalCollisionPlus(90 - 36.86, ball[i]);
                }
            }

            //upside middle-left
            if (ball[i].sphere.getLayoutX() > 475 && ball[i].sphere.getLayoutY() > 135 && ball[i].sphere.getLayoutY() < 162) {
                if (isTouchtoPocketEdge(-15, -10, 8625, ball[i].sphere.getLayoutX(), ball[i].sphere.getLayoutY())) {
                    pocketHorizontalCollisionMinus(33.7, ball[i]);

                }
            }

            //upside middle-right
            if (ball[i].sphere.getLayoutX() < 525 && ball[i].sphere.getLayoutY() > 135 && ball[i].sphere.getLayoutY() < 162) {
                if (isTouchtoPocketEdge(15, -10, -6375, ball[i].sphere.getLayoutX(), ball[i].sphere.getLayoutY())) {
                    pocketHorizontalCollisionPlus(33.7, ball[i]);
                }
            }

            //upside right horizontal
            if (ball[i].sphere.getLayoutX() > 875 && ball[i].sphere.getLayoutX() < 902) {
                if (isTouchtoPocketEdge(-15, -20, 16125, ball[i].sphere.getLayoutX(), ball[i].sphere.getLayoutY())) {
                    pocketHorizontalCollisionMinus(90 - 36.86, ball[i]);
                }
            }

            //bottom-side left horizontal
            if (ball[i].sphere.getLayoutX() >= 98 && ball[i].sphere.getLayoutX() < 125) {
                if (isTouchtoPocketEdge(15, 20, -12875, ball[i].sphere.getLayoutX(), ball[i].sphere.getLayoutY())) {
                    pocketHorizontalCollisionMinus(90 - 36.86, ball[i]);
                }
            }

            //bottom-side middle left
            if (ball[i].sphere.getLayoutX() > 474 && ball[i].sphere.getLayoutY() > 540 && ball[i].sphere.getLayoutY() <= 565) {
                if (isTouchtoPocketEdge(15, -10, -1625, ball[i].sphere.getLayoutX(), ball[i].sphere.getLayoutY())) {
                    pocketHorizontalCollisionPlus(33.7, ball[i]);
                }
            }

            //bottom-side middle right
            if (ball[i].sphere.getLayoutX() < 525 && ball[i].sphere.getLayoutY() < 565 && ball[i].sphere.getLayoutY() >= 540) {
                if (isTouchtoPocketEdge(15, 10, -13375, ball[i].sphere.getLayoutX(), ball[i].sphere.getLayoutY())) {
                    pocketHorizontalCollisionMinus(33.7, ball[i]);
                }
            }
            //bottom-side right horizontal
            if (ball[i].sphere.getLayoutX() > 875 && ball[i].sphere.getLayoutX() < 902) {
                if (isTouchtoPocketEdge(15, -20, -2125, ball[i].sphere.getLayoutX(), ball[i].sphere.getLayoutY())) {
                    pocketHorizontalCollisionPlus(90 - 36.86, ball[i]);
                }
            }

            //upside left verticle
            if (ball[i].sphere.getLayoutY() > 149 && ball[i].sphere.getLayoutY() < 175) {
                if (isTouchtoPocketEdge(20, -15, 625, ball[i].sphere.getLayoutX(), ball[i].sphere.getLayoutY())) {
                    pocketVerticalCollisionMinus(90 - 36.86, ball[i]);
                }
            }

            //bottom-side left verticle
            if (ball[i].sphere.getLayoutY() > 525 && ball[i].sphere.getLayoutY() < 551) {
                if (isTouchtoPocketEdge(20, 15, -9875, ball[i].sphere.getLayoutX(), ball[i].sphere.getLayoutY())) {
                    pocketVerticalCollisionPlus(90 - 36, ball[i]);
                }
            }

            //upside right verticle
            if (ball[i].sphere.getLayoutY() > 149 && ball[i].sphere.getLayoutY() < 175) {
                if (isTouchtoPocketEdge(20, 15, -20625, ball[i].sphere.getLayoutX(), ball[i].sphere.getLayoutY())) {
                    pocketVerticalCollisionPlus(90 - 36, ball[i]);
                }
            }

            //bottom-side right verticle
            if (ball[i].sphere.getLayoutY() > 525 && ball[i].sphere.getLayoutY() < 551) {
                if (isTouchtoPocketEdge(20, -15, -10125, ball[i].sphere.getLayoutX(), ball[i].sphere.getLayoutY())) {
                    pocketVerticalCollisionMinus(90 - 36.86, ball[i]);
                }
            }
        }
    }




    public void pocketHorizontalCollisionPlus(double angle, Ball a) {

        UpdateAngle(a);
        UpdateAngle(a, angle);
        a.setVx((a.getV() * Math.cos((a.getAngle()))));
        a.setVy((a.getV() * Math.sin((a.getAngle()))));
        a.setAx(a.getA() * Math.cos((a.getAngle())));
        a.setAy(a.getA() * Math.sin((a.getAngle())));

        a.setVx(-a.getVx());
        a.setAx(-a.getAx());
        UpdateAngle(a);
        UpdateAngle(a, -angle);

        a.setVx((a.getV() * Math.cos((a.getAngle()))));
        a.setVy((a.getV() * Math.sin((a.getAngle()))));
        a.setAx(a.getA() * Math.cos((a.getAngle())));
        a.setAy(a.getA() * Math.sin((a.getAngle())));
    }

    public void pocketHorizontalCollisionMinus(double angle, Ball a) {

        UpdateAngle(a);
        UpdateAngle(a, -angle);

        a.setVx((a.getV() * Math.cos((a.getAngle()))));
        a.setVy((a.getV() * Math.sin((a.getAngle()))));
        a.setAx(a.getA() * Math.cos((a.getAngle())));
        a.setAy(a.getA() * Math.sin((a.getAngle())));

        a.setVx(-a.getVx());
        a.setAx(-a.getAx());
        UpdateAngle(a);

        UpdateAngle(a, +angle);

        a.setVx((a.getV() * Math.cos((a.getAngle()))));
        a.setVy((a.getV() * Math.sin((a.getAngle()))));
        a.setAx(a.getA() * Math.cos((a.getAngle())));
        a.setAy(a.getA() * Math.sin((a.getAngle())));
    }



    public void pocketVerticalCollisionPlus(double angle, Ball a) {

        UpdateAngle(a);

        UpdateAngle(a, angle);


        a.setVx((a.getV() * Math.cos((a.getAngle()))));
        a.setVy((a.getV() * Math.sin((a.getAngle()))));
        a.setAx(a.getA() * Math.cos((a.getAngle())));
        a.setAy(a.getA() * Math.sin((a.getAngle())));

        a.setVy(-a.getVy());
        a.setAy(-a.getAy());
        UpdateAngle(a);


        UpdateAngle(a, -angle);
        a.setVx((a.getV() * Math.cos((a.getAngle()))));
        a.setVy((a.getV() * Math.sin((a.getAngle()))));
        a.setAx(a.getA() * Math.cos((a.getAngle())));
        a.setAy(a.getA() * Math.sin((a.getAngle())));
    }

    public void pocketVerticalCollisionMinus(double angle, Ball a) {

        UpdateAngle(a);
        UpdateAngle(a, -angle);
        a.setVx((a.getV() * Math.cos((a.getAngle()))));
        a.setVy((a.getV() * Math.sin((a.getAngle()))));
        a.setAx(a.getA() * Math.cos((a.getAngle())));
        a.setAy(a.getA() * Math.sin((a.getAngle())));

        a.setVy(-a.getVy());
        a.setAy(-a.getAy());
        UpdateAngle(a);

        UpdateAngle(a, +angle);

        a.setVx((a.getV() * Math.cos((a.getAngle()))));
        a.setVy((a.getV() * Math.sin((a.getAngle()))));
        a.setAx(a.getA() * Math.cos((a.getAngle())));
        a.setAy(a.getA() * Math.sin((a.getAngle())));
    }


    public void UpdateAngle(Ball ball, double ang) {
        double t = Math.toDegrees(Math.atan2(ball.getVy(), ball.getVx()));
        t = t + ang;
        if (t < 0) {
            t += 360;
        }
        ball.setAngle(Math.toRadians(t));
    }


    public void UpdateAngle(Ball ball) {
        double t = Math.toDegrees(Math.atan2(ball.getVy(), ball.getVx()));
        if (t < 0) {
            t += 360;
        }
        ball.setAngle(Math.toRadians(t));
    }


    boolean isTouchtoPocketEdge(double a, double b, double c, double x, double y) {

        double temp = Math.abs(a * x + b * y + c);
        double temp2 = Math.sqrt(a * a + b * b);
        double distance = temp / temp2;

        if (distance <= 10) {
            return true;
        }
        return false;
    }

    public void handlePocketsAndGameUpdate() {
        for (int i = 0; i < 16; i++) {
            double distance1 = Math.sqrt(Math.pow(ball[i].sphere.getLayoutX() - 85, 2) + Math.pow(ball[i].sphere.getLayoutY() - 135, 2));
            double distance2 = Math.sqrt(Math.pow(ball[i].sphere.getLayoutX() - 500, 2) + Math.pow(ball[i].sphere.getLayoutY() - 127, 2));
            double distance3 = Math.sqrt(Math.pow(ball[i].sphere.getLayoutX() - 915, 2) + Math.pow(ball[i].sphere.getLayoutY() - 135, 2));
            double distance4 = Math.sqrt(Math.pow(ball[i].sphere.getLayoutX() - 85, 2) + Math.pow(ball[i].sphere.getLayoutY() - 565, 2));
            double distance5 = Math.sqrt(Math.pow(ball[i].sphere.getLayoutX() - 500, 2) + Math.pow(ball[i].sphere.getLayoutY() - 574, 2));
            double distance6 = Math.sqrt(Math.pow(ball[i].sphere.getLayoutX() - 915, 2) + Math.pow(ball[i].sphere.getLayoutY() - 565, 2));

            if (distance1 < 20 - 2 || distance2 < 20 - 2 || distance3 < 20 - 2 || distance4 < 20 - 2 || distance5 < 20 - 2 || distance6 < 20 - 2) {
                if (turn.getTurn() && ballBundle.solidballsize == 0 && ballBundle.stripeballsize == 0) {
                    if (i < 7) {
                        myballtype = 0;
                        Message.setText("Your Ball Type: Solid");
                    } else if (i > 7 && i < 15) {
                        myballtype = 1;
                        Message.setText("Your Ball Type: Stripe");
                    }
                }

                if (!turn.getTurn() && ballBundle.solidballsize == 0 && ballBundle.stripeballsize == 0) {
                    if (i < 7) {
                        myballtype = 1;
                        Message.setText("Your Ball Type: Stripe");
                    } else if (i > 7 && i < 15) {
                        myballtype = 0;
                        Message.setText("Your Ball Type: Solid");
                    }
                }

                if (turn.getTurn() && i < 7) {
                    ballBundle.pushSolidball(ball[i]);
                    isFaul=false;
                    if (myballtype == 1) {
                        isplay = true;
                        turn.setTurn(false);
                        Message.setText("You should put stripe ball");
                    }
                } else if (!turn.getTurn() && i < 7) {
                    ballBundle.pushSolidball(ball[i]);
                    if (myballtype == 0) {
                        isplay = true;
                        turn.setTurn(true);
                        Message.setText("You should put solid ball");
                    }
                    else turn.setTurn(false);
                }
                else if (turn.getTurn() && i == 15) {
                    ball[15].setVx(0);
                    ball[15].setVy(0);
                    ball[15].setV(0);
                    ball[15].sphere.setLayoutX(500);
                    ball[15].sphere.setLayoutY(350);
                    isFaul=false;
                    turn.setTurn(false);
                } else if (!turn.getTurn() && i == 15) {
                    ball[15].setVx(0);
                    ball[15].setVy(0);
                    ball[15].setV(0);
                    ball[15].sphere.setLayoutX(500);
                    ball[15].sphere.setLayoutY(350);
                    isFaul=true;
                    turn.setTurn(true);
                } else if (turn.getTurn() && i == 7) {
                    if (myballtype == 0 && ballBundle.solidballsize < 7) {
                        ballBundle.pushSolidball(ball[7]);
                        Message.setText("You Lose");
                        isgameOver = true;
                    } else if (myballtype == 1 && ballBundle.stripeballsize < 7) {
                        ballBundle.pushStripeball(ball[7]);
                        Message.setText("You Lose");
                        isgameOver = true;
                    }
                } else if (!turn.getTurn() && i == 7) {
                    if (myballtype == 0 && ballBundle.solidballsize < 7) {
                        ballBundle.pushSolidball(ball[7]);
                        Message.setText("You Win");
                        isgameOver = true;
                    } else if (myballtype == 1 && ballBundle.stripeballsize < 7) {
                        ballBundle.pushStripeball(ball[7]);
                        Message.setText("You Win");
                        isgameOver = true;
                    }
                } else if (turn.getTurn() && myballtype == 0 && ballBundle.solidballsize == 7) {
                    ballBundle.pushSolidball(ball[7]);
                    Message.setText("You Win");
                    isgameOver = true;
                } else if (turn.getTurn() && myballtype == 1 && ballBundle.stripeballsize == 7) {
                    ballBundle.pushStripeball(ball[7]);
                    Message.setText("You Win");
                    isgameOver = true;
                } else if (!turn.getTurn() && myballtype == 0 && ballBundle.solidballsize == 7) {
                    ballBundle.pushSolidball(ball[7]);
                    Message.setText("You Win");
                    isgameOver = true;
                } else if (!turn.getTurn() && myballtype == 1 && ballBundle.stripeballsize == 7) {
                    ballBundle.pushStripeball(ball[7]);
                    Message.setText("You Win");
                    isgameOver = true;
                } else if (turn.getTurn() && i < 15) {
                    ballBundle.pushStripeball(ball[i]);
                    if (myballtype == 0) {
                        isplay = true;
                        turn.setTurn(false);
                        isFaul=false;
                    }
                } else if (!turn.getTurn() && i < 15) {
                    ballBundle.pushStripeball(ball[i]);
                    if (myballtype == 1) {
                        isplay = true;
                        turn.setTurn(true);
                        isFaul=true;
                    }
                    else turn.setTurn(false);
                }
            }
        }
    }

    public void line(Scene scene){
        Indicator.setLayoutX(ball[15].sphere.getLayoutX()+ cueStick.lineLength*Math.cos(Math.toRadians((cueStick.getAngle()+180)%360)));
        Indicator.setLayoutY(ball[15].sphere.getLayoutY()+ cueStick.lineLength*Math.sin(Math.toRadians((cueStick.getAngle()+180)%360)));

        cueStick.line.setLayoutX(ball[15].sphere.getLayoutX());
        cueStick.line.setLayoutY(ball[15].sphere.getLayoutY());
    }



    public void setLineEnlarge(double line){
        cueStick.lineLength=line;
        cueStick.line.setHeight(line);
    }

    public double getLineEnlarge(){
        return cueStick.lineLength;
    }


    public void SpinControll() {
        for (int i = 0; i < 16; i++) {

            Rotate rx = new Rotate(0, 0, 0, 0, Rotate.X_AXIS);
            Rotate ry = new Rotate(0, 0, 0, 0, Rotate.Y_AXIS);
            rx.setAngle(Math.toDegrees(ball[i].getVy() / 10));
            ry.setAngle(-Math.toDegrees(ball[i].getVx() / 10));
            ball[i].sphere.getTransforms().addAll(rx, ry);
        }
    }


    boolean isBoundedbyPool() {

            if (ball[15].sphere.getLayoutX() > 100 + 10 && ball[15].sphere.getLayoutY() > 160 && ball[15].sphere.getLayoutX() < 900 - 10 &&
                    ball[15].sphere.getLayoutY() < 550 - 10) {
                isBound = true;
                return isBound;
            }
            else {
                isBound=false;
                if (ball[15].sphere.getLayoutX() <= 110) {
                    ball[15].sphere.setLayoutX(112);

                }
                if (ball[15].sphere.getLayoutY() <= 160) {
                    ball[15].sphere.setLayoutY(162);
                }
                if (ball[15].sphere.getLayoutX() >= 890) {
                    ball[15].sphere.setLayoutX(888);
                }

                if (ball[15].sphere.getLayoutY() >= 540) {
                    ball[15].sphere.setLayoutY(538);
                }
                    return isBound;
            }
        }


    boolean isTouchForDirection() {
        int i = 0;
        while (i < 16) {
            double xDist = ball[i].sphere.getLayoutX() - Indicator.getLayoutX();
            double yDist = ball[i].sphere.getLayoutY() - Indicator.getLayoutY();
            double dis = Math.sqrt(xDist * xDist + yDist * yDist);
            if (dis <= (Indicator.getRadius() + ball[i].sphere.getRadius()) && (dis + 2) > (Indicator.getRadius() + ball[i].sphere.getRadius())) {
                angle=Math.toDegrees(Math.atan2(yDist,xDist));
                if(angle<0){
                    angle+=360;
                }
                return true;
            } else {
                i++;

            }
        }

        return false;
    }

    public void setDirection(){
        if(isTouchForDirection()){
            if(Indicator!=null) {
                Direction.setLayoutX(Indicator.getLayoutX());
                Direction.setLayoutY(Indicator.getLayoutY());
                Direction.setHeight(100);
                Direction.getTransforms().add(new Rotate((angle - a), Rotate.Z_AXIS));
                a = angle;
            }
        }
        else {
            Direction.setLayoutX(0);
            Direction.setLayoutY(0);
            Direction.setHeight(0);
        }
    }



    public void setValue(double value){
        cueStick.setValue(value);
    }
    public void setAngle(double angle){
        cueStick.setAngle(angle);
        cueStick.setRotateStick(angle);
        cueStick.MoveStick(ball[15].sphere.getLayoutX(), ball[15].sphere.getLayoutY());
    }
    public void setLayout(double x,double y){
        ball[15].sphere.setLayoutX(x);
        ball[15].sphere.setLayoutY(y);
    }

    public void setInitial_v(double v) {
        cueStick.setIniVelocity(v);
    }

    public void setInitialVelocity(double initial_v){
        InitialVelocity =initial_v;
    }

    public double getInitialVelocity() {
        return InitialVelocity;
    }

    public double getLayoutx(){
        return ball[15].sphere.getLayoutX();
    }

    public double getLayouty(){
       return ball[15].sphere.getLayoutY();
    }

    public void setIsmyturn(boolean ismyturn) {
        this.ismyturn = ismyturn;
    }

    public boolean ismyturn() {
        return ismyturn;
    }


}

