package Client;

import Animation.AnimationControll;
import BallandCue.BallControll;
import BallandCue.Light;
import GamePackage.ControllerPlayer;
import GamePackage.StartGame;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Player extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root1= FXMLLoader.load(getClass().getResource("PoolBoard.fxml"));
        Group root =  new Group();

        primaryStage.setTitle("8 Ball Pool");
        Scene scene=new Scene(root,1100,650);
        scene.setFill(Color.DARKSLATEGREY);
        primaryStage.setScene(scene);
        primaryStage.show();

        root.getChildren().add(root1);

        AnimationControll Animation = new AnimationControll();
        BallControll ball = new BallControll(root, scene);
        new Light(root);

        ControllerPlayer client = new ControllerPlayer();
        ControllerPlayer server = new ControllerPlayer();

        new Client(client, server, ball, root);

        StartGame startGame = new StartGame(root);
        ball.BallPackage_Add(startGame);

        Animation.runTimer(ball, client, server);
        Animation.runTimeline(ball, scene, client, server, startGame);
        Animation.runAnimationTimer(ball);

    }

    public static void main(String[] args) {
        launch(args);
    }
}
