package application;
	
import java.util.Timer;
import java.util.TimerTask;
import java.util.function.Function;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;


public class Main extends Application {
	
	static int work_minutes = 25;
	static int break_minutes = 5;
	
	static int minutes_remaining = work_minutes;
	static int seconds_remaining = 0;
	static String current_time;
	static String[] work_state = {"Start Work", "Working", "Start Break", "Break"};
	static int work_state_ID = 0;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			

			
			current_time = Integer.toString(minutes_remaining) + ":0" + Integer.toString(seconds_remaining);
			Text timer_display = new Text(current_time); 
			
			Button start_button = new Button("Go");
			Text work_state_display = new Text(work_state[work_state_ID]);
				
			Timer timer = new Timer();
			TimerTask timerStarts = new TimerTask() {
	            @Override
	            public void run(){
	            	if(seconds_remaining == 0 && minutes_remaining != 0) {
	            		minutes_remaining = minutes_remaining - 1;
	            		seconds_remaining = 60;
	            		
	            		current_time = Integer.toString(minutes_remaining) + ":" + Integer.toString(seconds_remaining);
	            		
	            	}
	            	
	            	if(seconds_remaining != 0) {
	            		seconds_remaining = seconds_remaining - 1;
		            	if(seconds_remaining <= 9) {
		            		current_time = Integer.toString(minutes_remaining) + ":0" + Integer.toString(seconds_remaining);
			            	
		            	} else {
		            		current_time = Integer.toString(minutes_remaining) + ":" + Integer.toString(seconds_remaining);
		            	}
	            	}
	            	
	            	if(seconds_remaining == 0 && minutes_remaining == 0) {
	            		current_time = Integer.toString(minutes_remaining) + ":0" + Integer.toString(seconds_remaining);
	            		timer.cancel();
	            	}
	            	

	            	
	            	
	            	timer_display.setText(current_time);
	            }
			};
			
			
			start_button.setOnAction(new EventHandler<ActionEvent>()
	        {
	            public void handle(ActionEvent e)
	            {
	            	timer.schedule(timerStarts, 0, 1000);
	            	work_state_ID++;
	            	if(work_state_ID >= work_state.length) {
	            		work_state_ID = 0;
	            		work_state_display.setText(work_state[work_state_ID]);
	            	}
            		else {
            			work_state_display.setText(work_state[work_state_ID]);
	            		}
	            		
	            	
    		            
            	}
	        });
			
			
			
			
			
			HBox hBox = new HBox(timer_display, start_button, work_state_display);
			Scene scene = new Scene(hBox,150,30);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
//			primaryStage.initStyle(StageStyle.TRANSPARENT);
			primaryStage.setAlwaysOnTop(true);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
