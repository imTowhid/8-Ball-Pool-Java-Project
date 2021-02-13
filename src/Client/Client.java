package Client;

import BallandCue.BallControll;
import GamePackage.ControllerPlayer;
import GamePackage.Turn;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import netThread.ReadThread;
import netThread.WriteThread;
import netThread.networkObjStream;


public class Client implements Runnable{
    private Thread t;
    private Boolean turnid;
    private ControllerPlayer client;
    private ControllerPlayer server;
    private BallControll ball;
    private final Group root;
    private TextField Ready;

    Turn turn;
   public static networkObjStream nc;

   public Client( ControllerPlayer client, ControllerPlayer server, BallControll ball, Group root) {
        this.ball = ball;
        this.client = client;
        this.server = server;
        this.root = root;
        Ready = new TextField();
        Ready.setText("Waiting for Opponent Player.....");
        Ready.setLayoutX(0);
        Ready.setLayoutY(267);
        Ready.setPrefHeight(132);
        Ready.setPrefWidth(1100);
        Ready.setAlignment(Pos.CENTER);
        Ready.setEditable(false);
        DropShadow dropShadow = new DropShadow();
        Ready.setFont(Font.font("Calibri", FontWeight.BOLD,50));
        Ready.setEffect(dropShadow);
        Ready.setStyle("-fx-text-inner-color: white;");
        Paint value0 = Paint.valueOf("#033D6C");
        Ready.setBackground(new Background(new BackgroundFill(value0, CornerRadii.EMPTY, Insets.EMPTY)));

        root.getChildren().add(Ready);
        t=new Thread(this);
        t.start();
       System.out.println("reached to Client");
    }

     @Override
        public void run() {
         {
             String str2 = null;
             String str = null;
             String serverAddress = "127.0.0.1";
             int serverPort = 33333;
             nc = new networkObjStream(serverAddress, serverPort);
             System.out.println("nc started");

             while(str==null){
                 str=(String)nc.read();
             }
             System.out.println("string received");
             nc.write("server");

             while (str2==null){
                 str2 = (String)nc.read();
             }
             nc.write("ok");

             if(str.equals("true")){
                ball.turn.setTurn(true);
                ball.isFaul = true;
             }
             else{
                 ball.turn.setTurn(false);
                 ball.isFaul = false;
             }

             System.out.println("turn is set");

             Ready.setText("Ready");
             Ready.setLayoutY(510);
             Ready.setLayoutX(946+50);
             Ready.setPrefWidth(67);
             Ready.setPrefHeight(29);
             Ready.setFont(Font.font(15));
             Paint value0 = Paint.valueOf("#033D6C");
             Ready.setStyle("-fx-text-inner-color: white;");
             Ready.setBackground(new Background(new BackgroundFill(value0, CornerRadii.EMPTY, Insets.EMPTY)));


             System.out.println("okkkk!");

             new WriteThread(nc, client);
             new ReadThread(nc, server,turn);
             System.out.println("reached to Thread");

            while (true);
            }

        }
}
