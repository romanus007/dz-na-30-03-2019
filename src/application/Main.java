package application;
import java.util.Random;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;


public class Main extends Application {
	int x =0;
	int y =0;
	
	private static final int width = 800;
	private static final int height = 600;

	private static final int RACKET_WIDTH = 10;
	private static final int RACKET_HEIGHT = 90;
	
	private static final int BALL_RAD = 30;
	
	double playerX=0;
	double playerY = height/2;
	
	double compX = width - RACKET_WIDTH;
	double compY = height/2;
	
	double ballX = width/2;
	double ballY = height/2;
	
	GraphicsContext gc;
	
	double ballYSpeed = 1;
	double ballXSpeed =3;
	
	boolean gameStarted;
	
	private void drawTable() {

		gc.setFill(Color.GREEN);
		gc.fillRect(0, 0, width, height);			

		gc.setFill(Color.YELLOW);
		gc.fillRect(width/2, 0, 2, height);		

		gc.setStroke(Color.YELLOW);
		gc.setTextAlign(TextAlignment.CENTER);
		gc.strokeText("Score " +"0:"+x, width/4, height/4);	
		gc.strokeText("you hit the ball "+y+" times", width/4, height/4 +40);	
		if(gameStarted) {
			ballX+=ballXSpeed;
			ballY+=ballYSpeed;

			if(ballX < width-width/8) {
				compY = ballY - RACKET_HEIGHT/2;
			}
			if (ballX>width-RACKET_WIDTH-BALL_RAD) {
				ballXSpeed=-1*ballXSpeed;
			}
			if (ballX<RACKET_WIDTH) {
				if (playerY<ballY+BALL_RAD  && playerY+RACKET_HEIGHT>ballY-BALL_RAD && ballX>0) {
				ballXSpeed=-1*ballXSpeed;
				y+=1;
				}else {
					if (ballX<0) {
					x+=1;
					ballYSpeed = 0;
					ballXSpeed = 0;
					ballX = width/2-BALL_RAD/2;
					ballY = height/2-BALL_RAD/2;	
					}
				}
			}
			if (ballXSpeed == 0 && ballYSpeed == 0 ) {
				gc.strokeText("You lose", width/4, height/4 +80);
				}
			if (ballY<0) {
				ballYSpeed=-1*ballYSpeed;
			}
			if (ballY>height-BALL_RAD) {
				ballYSpeed=-1*ballYSpeed;
			}
			gc.fillOval(ballX, ballY, BALL_RAD, BALL_RAD);
		} else {
			gc.strokeText("Click to start", width/4, height/4 -40);	
		}
	

		gc.fillRect(playerX, playerY, RACKET_WIDTH, RACKET_HEIGHT);
		gc.fillRect(compX, compY, RACKET_WIDTH, RACKET_HEIGHT);	
	}
	
	@Override
	public void start(Stage root) {
		Canvas canvas = new Canvas(width,height);
		gc = canvas.getGraphicsContext2D();
		drawTable();		
		Timeline t1 = new Timeline(new KeyFrame(Duration.millis(10), e->drawTable()));
		t1.setCycleCount(Timeline.INDEFINITE);
		
		canvas.setOnMouseClicked(e -> gameStarted = true);
		canvas.setOnMouseMoved(e -> playerY = e.getY());
		
		root.setScene(new Scene(new StackPane(canvas)));
		root.setTitle("Ping-pong");
		root.show();
		t1.play();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}