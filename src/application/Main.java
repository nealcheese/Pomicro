package application;
	
import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;


public class Main extends Application {
	
	//Set duration for work states, will add an input field in a dialog box for these
	static int work_minutes = 1;
	static int break_minutes = 1;
	
	//Declare variable used to store the current time left, define the starting values on startup	
	static int minutes_remaining = work_minutes;
	static int seconds_remaining = 0;
	//Declare a string to store the current time left in the required format, before displaying on screen
	static String current_time;
	
	
	//Boolean which indicates whether the timer is running, this will be checked when clicking the start/stop button
	static boolean timer_is_on = false;
		
	//Define the notifications for the current work state, store in an array, work_state_ID will be used to cycle through them
	static String[] work_state = {"Start Work", "Working", "Start Break", "Break"};
	static int work_state_ID = 0;
	
	static TimerTask timerStarts;
	static Timer timer;
	
	static Button start_button; 
	
	//TBH I don't understand why this syntax is required for JavaFX, is an error catch required? Look into this further
	@Override
	public void start(Stage primaryStage) {
		try {
			
			//On start, set the current time string then create a timer display text node and set it to the current time
			current_time = Integer.toString(minutes_remaining) + ":0" + Integer.toString(seconds_remaining);
			Text timer_display = new Text(current_time);
			timer_display.getStyleClass().add("textbox");
			timer_display.setWrappingWidth(40);
			
			//On start, create the start button that says go
			start_button = new Button();
		    ImageView goImage = new ImageView(new Image("C:/Users/neala/eclipse-workspace/Pomicro/src/application/Play Green Triangle.png", 10, 10, false, false));
		    ImageView stopImage = new ImageView(new Image("C:/Users/neala/eclipse-workspace/Pomicro/src/application/Stop Red Circle.png", 10, 10, false, false));
		    ImageView skipImage = new ImageView(new Image("C:/Users/neala/eclipse-workspace/Pomicro/src/application/Skip Yellow Triangle.png", 10, 10, false, false));
		    start_button.setGraphic(goImage);
			start_button.setMinWidth(20);
			start_button.setMaxHeight(20);
			Button skip_button = new Button();
			skip_button.setGraphic(skipImage);
			skip_button.setMinWidth(20);
			skip_button.setMaxHeight(20);
			
			//On start, create a text node that displays the work state (working, break etc.)
			Text work_state_display = new Text(work_state[work_state_ID]);
			work_state_display.getStyleClass().add("textbox");
			work_state_display.setWrappingWidth(65);
				
			//On start, create a timer object - the timer will be used to run a timertask every second, which updates the current time left
			
			//On start, create timertask that updates the current time left
						
			//Set start button function on click
			start_button.setOnAction(new EventHandler<ActionEvent>()
	        {
	            public void handle(ActionEvent e)
	            {
	            	if(timer_is_on == true) {
	            		timer.cancel();
	            		timer_is_on = false;
	            		System.out.println(timer_is_on);
	            		start_button.setGraphic(goImage);
	            	}
	            	
	            	else {
	            		createTT(timer_display, start_button, work_state_display, goImage);
	            	timer_is_on = true;
	            	System.out.println(timer_is_on);
	            	start_button.setGraphic(stopImage);
	            	if(work_state_display.getText() == "Start Work") {
	            		work_state_display.setText("Work");
	            	}
	            	if(work_state_display.getText() == "Start Break") {
	            		work_state_display.setText("Break");
	            	}

	            	}	
	            	
    		            
            	}
	        });
			
			skip_button.setOnAction(new EventHandler<ActionEvent>()
	        {
	            public void handle(ActionEvent e)
	            {	
	            	nextWorkState(work_state_display, timer_display, start_button, goImage);
	            	
            	}
	        });
			
			
			HBox hBox = new HBox(timer_display, work_state_display, start_button, skip_button);
			Scene scene = new Scene(hBox,170,20);
			hBox.getStyleClass().add("hbox");
			//hBox.setAlignment(Pos.CENTER);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.initStyle(StageStyle.TRANSPARENT);
			primaryStage.setAlwaysOnTop(true);
			
			Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
			primaryStage.setX(primaryScreenBounds.getMinX() + primaryScreenBounds.getWidth() - 500);
			primaryStage.setY(primaryScreenBounds.getMinY());
			

			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void createTT(Text timer_display, Button start_button, Text work_state_display, ImageView goImage) {
		timer = new Timer();
		timerStarts = new TimerTask() {
            @Override
            public void run(){
            	
            	//A series of if statements checks what the current time is and changes the time accordingly after each second
            	
            	//If seconds go to 0,  minus 1 from the minutes and start seconds at 60 e.g. 25:00 to 24:59
            	if(seconds_remaining == 0 && minutes_remaining != 0) {
            		minutes_remaining = minutes_remaining - 1;
            		//for some reason, if you put this as 59 (what you'd expect) the timer goes to 58 seconds when code is run
            		seconds_remaining = 60;            		
            		current_time = Integer.toString(minutes_remaining) + ":" + Integer.toString(seconds_remaining);          		
            	}
            	
            	//If seconds aren't 0, just minus 1 from the seconds remaining i.e. in the middle of a minutes
            	if(seconds_remaining != 0) {
            		seconds_remaining = seconds_remaining - 1;
            		//Check if seconds remaining are less than 9, seconds remaining is stored as int initially and we want numbers less than 9 to show with a 0 in front e.g. 09 not 9
	            	if(seconds_remaining <= 9) {
	            		current_time = Integer.toString(minutes_remaining) + ":0" + Integer.toString(seconds_remaining);           	
	            	} else {
	            		current_time = Integer.toString(minutes_remaining) + ":" + Integer.toString(seconds_remaining);
	            	}
            	}
            	
              	//when timer reaches 0, updates timer display and end the timertask
            	if(seconds_remaining == 0 && minutes_remaining == 0) {
            		current_time = Integer.toString(minutes_remaining) + ":0" + Integer.toString(seconds_remaining);
            		nextWorkState(work_state_display, timer_display, start_button, goImage);
            		

            	}
            	//time remaining is stored as a string with the required format in each if statement, set timer display to string
            	timer_display.setText(current_time);
            }
		};
		timer.scheduleAtFixedRate(timerStarts, 1000, 1000);
	};
	
	public void nextWorkState(Text work_state_display, Text timer_display,  Button start_button, ImageView goImage) { 
//		goImage = new ImageView(new Image("C:/Users/neala/eclipse-workspace/Pomicro/src/application/Play Green Triangle.png", 10, 10, false, false));
		start_button.setText("OK");
		System.out.println("Timer Finished");
		
		if(work_state_display.getText() == "Start Work" || work_state_display.getText() == "Work") {
    		work_state_display.setText("Start Break");
    		minutes_remaining = break_minutes;
    		seconds_remaining = 0;
    		current_time = Integer.toString(minutes_remaining) + ":00";
    		timer_display.setText(current_time);
    		timer_is_on = false;
    		timer.cancel();
    	}
		else {
			work_state_display.setText("Start Work");
    		minutes_remaining = work_minutes;
    		seconds_remaining = 0;
    		current_time = Integer.toString(minutes_remaining) + ":00";
			timer_display.setText(current_time);
			timer_is_on = false;
			timer.cancel();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
