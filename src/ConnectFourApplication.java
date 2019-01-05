/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



import java.util.Observable;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import java.util.Random;
import javafx.application.Application;
import java.util.Observer;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;


/**
 * This ConnectFourApplication class implements a connectFour game with a graphical user interface. This class implements the Obsever in
 * @author Omar Jemal
 * @since 05/12/2018
 * @version 1.0
 * 
 */
public class ConnectFourApplication extends Application implements Observer  {
    
    public final static int NUM_COLUMNS = 8;
    public final static int NUM_ROWS = 8;
    public final static int NUM_TO_WIN = 4;
    public final static int BUTTON_SIZE = 20;
    
    private ConnectButton currentButton = null;
    private ConnectFourEnum firstTurn;
    private ConnectFourGame gameEngine; 
    private Alert errorAlert;
    private Alert endGameAlert;
    private Alert instructionsAlert;
    private ConnectButton[][] buttons;
    private Random random;
    private DropShadow shadow;
    
    BorderPane root;
    Scene scene;
    TextField textfield;
    Button submitButton;
    GridPane grid;
    HBox hbox;
    Button instructions;

    /**
     * This method initializes and builds the graphical user interface in order for a user to be able to play connectFour with buttons.
     * @param primaryStage 
     */
    
    public void start(Stage primaryStage ){
        
        random = new Random();
        root = new BorderPane();
        scene = new Scene(root,480,520);
        textfield = new TextField();
        hbox = new HBox();
        submitButton = new Button("Confirm");
        grid = new GridPane();
        instructions = new Button("Instructions");
        
        root.setTop(textfield);
        root.setBottom(hbox);
        
        hbox.getChildren().add(submitButton);
        hbox.getChildren().add(instructions);
        hbox.setStyle("-fx-alignment: center;");
        
        textfield.setDisable(true);
        textfield.setStyle("-fx-opacity: 1.0;");
        
        shadow = new DropShadow();
        submitButton.setOnAction(new EventHandler<ActionEvent>(){
        
            public void handle(ActionEvent event){

                try{
                    if(currentButton == null){
                        throw new IllegalArgumentException("No checker has been selected");
                    }
                    
                    gameEngine.takeTurn(currentButton.getRow(),currentButton.getColumn());
                    
                    if (gameEngine.getGameState() != ConnectFourEnum.IN_PROGRESS){
                        endGameAlert = new Alert(AlertType.INFORMATION);
                        endGameAlert.setTitle("ConnectFour");
                        endGameAlert.setHeaderText("Game Over");

                        if(gameEngine.getGameState() != ConnectFourEnum.DRAW){
                            endGameAlert.setContentText(EnumToString(gameEngine.getGameState()) + " WINS");
                        }else{
                            endGameAlert.setContentText("It's a draw!");
                        }

                        endGameAlert.showAndWait();
                        reset();
                    }else{
                        currentButton.setEffect(null);
                        currentButton.setIsClicked(true);
                        textfield.setText("It's " + EnumToString(gameEngine.getTurn()) + "'s turn");
                    }
                }
                catch(IllegalArgumentException e){
                    errorAlert = new Alert(AlertType.WARNING);
                    errorAlert.setTitle("ConnectFour");
                    errorAlert.setHeaderText("User Input Error");
                    errorAlert.setContentText(e.getMessage());
                    errorAlert.show();
                }
                
            }
        
        });
        
        instructions.setOnAction(new EventHandler<ActionEvent>(){
            
            public void handle(ActionEvent e){
                instructionsAlert = new Alert(AlertType.INFORMATION);
                instructionsAlert.setTitle("ConnectFour");
                instructionsAlert.setHeaderText("Instructions");
                instructionsAlert.setContentText("To play: \n "
                        + "Place a checker at the bottom of the grid, or on top of another checker, and confirm your choice.\n"
                        + "\nTo win:\nA player must complete a vertical column, or diagonal column, or a hortizontal row "
                        + "of four checkers which are the same color.\nIf there is no completed rows or columns and the grid is full, the game ends in a draw. ");
                instructionsAlert.show();
            }
            
            
            
        });
        
        root.setCenter(grid);
   //     grid.setGridLinesVisible(true);
       grid.setStyle("-fx-background-color: dodgerblue;"
               + "-fx-alignment: center;"
               + "-fx-hgap: 10;"
               + "-fx-vgap: 10;"); 
       
        if(random.nextInt(2) == 1){
            firstTurn = ConnectFourEnum.BLACK;
        }else{
            firstTurn = ConnectFourEnum.RED;
        }
        
        gameEngine = new ConnectFourGame(firstTurn);
        
        
        firstTurn = gameEngine.switchTurns(firstTurn);
        
        textfield.setText(EnumToString(gameEngine.getTurn()) + " begins");
        gameEngine.addObserver(this);
        
        buttons = new ConnectButton[NUM_ROWS][NUM_COLUMNS];
        ButtonHandler buttonhandler = new ButtonHandler();
        MouseEventHandler mouseEventHandler = new MouseEventHandler();
        for( int j = 0; j < NUM_ROWS; j++ ){
            for( int i = 0; i < NUM_COLUMNS; i++ ){
           
                buttons[j][i] = new ConnectButton("EMPTY",j,i,false);
                buttons[j][i].setStyle("-fx-background-color: white ;"
                        + "-fx-text-fill: black; "
                        + "-fx-border-color: dodgerblue;"
                        + "-fx-border-radius:100%;"
                        + "-fx-font-size:10;"
                        + "-fx-background-radius:100%;");
                buttons[j][i].setOnAction(buttonhandler);
                buttons[j][i].setOnMouseEntered(mouseEventHandler);
                buttons[j][i].setOnMouseExited(mouseEventHandler);
             //   buttons[j][i].seton
              //  buttons[j][i].seton  // mouse exit mouse enter effects
                buttons[j][i].setMinHeight(46);
                buttons[j][i].setMaxWidth(Double.MAX_VALUE);
                grid.add(buttons[j][i], i ,NUM_ROWS - (j+1));
            }
        }
        
        primaryStage.setTitle("ConnectFour");
        primaryStage.setScene(scene);
        primaryStage.show();
        
    }
    
    /**
     * This method resets the game engine for connect four, and resets all the buttons on the graphical user interface.
     */
    private void reset(){
        
        gameEngine.reset(firstTurn);
        firstTurn = gameEngine.switchTurns(firstTurn);
        
        for(int i = 0; i < NUM_ROWS; i++){
            for(int j = 0; j < NUM_COLUMNS; j++){
        //        buttons[i][j].setDisable(false);
                buttons[i][j].setText("EMPTY");
                buttons[j][i].setStyle("-fx-background-color: white ;"
                        + "-fx-text-fill: black; "
                        + "-fx-border-color: dodgerblue;"
                        + "-fx-border-radius:100%;"
                        + "-fx-font-size:10;"
                        + "-fx-background-radius:100%;");
                buttons[j][i].setIsClicked(false);

            }
        }
        
        textfield.setText(EnumToString(gameEngine.getTurn()) + " begins");
        
    }
    
    /**
     * this method updates the graphical user interface when a change is made in the observable object.
     * @param obs a reference to the observable object.
     * @param arg an object containing the information for the update from the observable object, which is a ConnectMove object which symbolizes the move a player made in connectFour.
     */
    public void update(Observable obs, Object arg){
            
            
            if(arg instanceof ConnectMove){
                ConnectMove move = (ConnectMove) arg;
                
                buttons[move.getRow()][move.getColumn()].setLabel(move.getColor().toString());
                if(move.getColor()== ConnectFourEnum.BLACK){
                    buttons[move.getRow()][move.getColumn()].setStyle("-fx-background-color: #000000 ;"
                        + "-fx-text-fill: white; "
                        + "-fx-border-color: dodgerblue;"
                        + "-fx-border-radius:100%;"
                        + "-fx-font-size:10;"
                        + "-fx-background-radius:100%;");
                }
                if(move.getColor()== ConnectFourEnum.RED){
                    buttons[move.getRow()][move.getColumn()].setStyle("-fx-background-color: #FF0000 ;"
                        + "-fx-text-fill: black; "
                        + "-fx-border-color: dodgerblue;"
                        + "-fx-border-radius:100%;"
                        + "-fx-font-size:10;"
                        + "-fx-background-radius:100%;");
                }
               // buttons[move.getRow()][move.getColumn()].setDisable(true);
           }
        }
        
    /**
     * This method takes a ConnectFourEnum value and returns the associated string value for the enum.
     * @param Enum The connectFourEnum that is being switched to a string.
     * @return a the associated string for the ConnectFourEnum passed in. 
     */
    private String EnumToString(ConnectFourEnum  Enum){
        
        if(Enum == ConnectFourEnum .BLACK){
            return "BLACK";
        }
        if(Enum == ConnectFourEnum.RED){
            return "RED";
        }
        if(Enum == ConnectFourEnum.EMPTY){
            return "EMPTY";
        }
        return "IN_PROGRESS";
    }
    /**
     * this main method launches the graphical user interface.
     * @param args 
     */
    public static void main(String[] args) {
         launch(args);
    }
    /**
     * This inner class implements the handler for the connectButtons, and implements the EventHandler interface.
     */
    class ButtonHandler implements EventHandler<ActionEvent>{
        
        public void handle(ActionEvent event){
            
            Object o = event.getSource();
            
            if(o instanceof ConnectButton){
                currentButton = (ConnectButton) o;
                for( int j = 0; j < NUM_ROWS; j++ ){
                    for( int i = 0; i < NUM_COLUMNS; i++ ){
                        buttons[j][i].setEffect(null);
                
                    }   
                }   
                        
                currentButton.setEffect(shadow);
                
                
                
            }
           
            
        }
        
        
    }
    
    class MouseEventHandler implements EventHandler<MouseEvent>{
        
        public void handle(MouseEvent event){
            Object o = event.getSource();
            
            if(o instanceof ConnectButton){
                ConnectButton button = (ConnectButton) o;
                
                
                if(event.getEventType() == MouseEvent.MOUSE_ENTERED && button.getIsClicked() == false){
                    button.setEffect(shadow);
                }
                
                if(event.getEventType() == MouseEvent.MOUSE_EXITED && button != currentButton){
                    button.setEffect(null);
                }
            
            }
        }
        
    }
    
}
