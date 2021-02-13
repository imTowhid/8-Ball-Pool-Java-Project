package Animation;

import BallandCue.BallControll;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.util.Duration;

import java.util.Timer;
import java.util.TimerTask;


import GamePackage.ControllerPlayer;
import GamePackage.StartGame;

import static java.lang.Thread.sleep;

public class AnimationControll {


    public void runAnimationTimer(BallControll ball){
        new AnimationTimer() {
            @Override
            public void handle(long now) {

                if (!ball.isgameOver) {
                    ball.handleBalltoBallCollision();
                    ball.detectWallCollision();
                    ball.cueBallVelocityControll();
                    ball.detectBallPocketEdgeCollision();
                    ball.handlePocketsAndGameUpdate();
                    ball.SpinControll();
                }

            }
        }.start();
    }

    public void runTimer(BallControll ball, ControllerPlayer client, ControllerPlayer server){
        Timer timer = new Timer();

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                ball.setVelocityofCueball();
                if (ball.cueStick.getIniVelocity() > 1) {
                    client.setInitial_v(ball.cueStick.getIniVelocity());
                    try {
                        sleep(1000);
                        ball.cueStick.setIniVelocity(0);
                        client.setInitial_v(0);

                    } catch (Exception e) {
                    }
                }
            }
        }, 0, 30);
    }

    public void runTimeline(BallControll ball, Scene scene, ControllerPlayer client, ControllerPlayer server, StartGame startGame){
        Timeline gameLoop = new Timeline();
        gameLoop.setCycleCount(Timeline.INDEFINITE);

        KeyFrame kf = new KeyFrame(
                Duration.seconds(.03),
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent ae) {
                        if(!ball.isgameOver) {
                            ball.UpdateState(scene);
                            ball.line(scene);
                            ball.setDirection();
                            ball.handlePocketsAndGameUpdate();

                            if (ball.turn.getTurn()) {
                                startGame.setText("Your Turn");
                                client.setValue(ball.cueStick.getValue());
                                client.setAngle(ball.cueStick.getAngle());
                                client.setLineEnlarge(ball.getLineEnlarge());

                            } else {
                                startGame.setText("Opponent Turn");
                                ball.setValue(server.getValue());
                                ball.setAngle(server.getAngle());
                                ball.setLineEnlarge(server.getLineEnlarge());
                                ball.setInitialVelocity(server.getInitial_v());

                            }
                        }
                        else startGame.setText("Game Over!!!");
                    }
                });

        gameLoop.getKeyFrames().add(kf);

        startGame.start(gameLoop);


    }
}
