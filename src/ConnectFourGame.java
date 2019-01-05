



/**
 * The ConnectFourGame class implements a game of ConnectFour including adjustable board size, number of checkers to win, and an adjustable 
 * staring player. The class extends the Observable class, and makes use of the observe pattern.
 * 
 *@author Omar Jemal
 *@since 05/12/2018
 *@version 1.0
 */
import java.util.Scanner;
import java.util.Observable;

public class ConnectFourGame extends Observable {

    
    private int nRows;
    private int nColumns;
    private int numToWin;
    private ConnectFourEnum[][] grid;
    private ConnectFourEnum turn;
    private ConnectFourEnum gameState;
    private int nMarks;
    
    public ConnectMove connectMove;
    
    /**
     * This constructor constructs ConnectFour object with default values for number of rows,  number of columns, symbols to win, and an empty grid for the game board.
     * @param initialTurn The symbol of the player with the starting turn.
     * @throws IllegalArgumentException if the user doesn't enter a ConnectFourEnum that is one of the players.
     */
    public ConnectFourGame(ConnectFourEnum initialTurn) {
        super();
        
        if (initialTurn != ConnectFourEnum.BLACK && initialTurn != ConnectFourEnum.RED ) {
            throw new IllegalArgumentException ("Put a proper Turn value");
        }
        this.nRows = 8;
        this.nColumns = 8;
        this.numToWin = 4;
        this.nMarks = 0;
        this.grid = new ConnectFourEnum[nRows][nColumns];
        this.turn = initialTurn;
        this.gameState = ConnectFourEnum.IN_PROGRESS;
        
        for(int row = 0; row < nRows; row++){
            for(int col = 0; col < nColumns; col++){
                grid[row][col] = ConnectFourEnum.EMPTY;
            }
        }
            
    }
    
    /**
     * This constructor constructs ConnectFour object  with user specified values for rows, columns, number of symbols to win, an empty grid, and the symbol of the player with the starting turn.
     * @param nRows The number of rows for the game board.
     * @param nColumns The number of columns for the game board.
     * @param numToWin The number of symbols that need to be aligned to win for a player.
     * @param initialTurn The symbol of the of the player with the starting turn.
     * @throws IllegalArgumentException if the user enters a value of row or columns that is less than 1, or if the number of symbols needed is not possible within the gameboard, or if the input for the initial turn is not a connectFourEnum of one of the players.
     */
    public ConnectFourGame( int nRows, int nColumns, int numToWin, ConnectFourEnum  initialTurn){
         super();
        if (nRows < 1) {
            throw new IllegalArgumentException ("Put a nice number of rows");
        } 
        if (nColumns < 1) {
            throw new IllegalArgumentException ("Put a nice number of columns");
        } 
        if (numToWin < 1 && !(numToWin > nRows) && !(numToWin > nColumns)) {
            throw new IllegalArgumentException ("Put a nice number of spaces required to win");
        } 
        if (initialTurn != ConnectFourEnum.BLACK && initialTurn != ConnectFourEnum.RED ) {
            throw new IllegalArgumentException ("Put a proper Turn value");
        }
       
        this.nRows = nRows;
        this.nColumns = nColumns;
        this.numToWin = numToWin;
        this.turn = initialTurn;
        this.nMarks = 0;
        this.grid = new ConnectFourEnum[nRows][nColumns];
        this.gameState = ConnectFourEnum.IN_PROGRESS;
        
        for(int row = 0; row < nRows; row++){
            for(int col = 0; col < nColumns; col++){
                grid[row][col] = ConnectFourEnum.EMPTY;
            }
        }
    }
    
    /**
     * This method resets the game board to the initial values that the user specified, and clears the game board.
     * 
     * @param initialTurn The symbol of the player with the starting turn.
     */
    public void reset(ConnectFourEnum initialTurn){
        
        
        this.turn = initialTurn;
        this.nMarks = 0;
        this.gameState = ConnectFourEnum.IN_PROGRESS;
    
        for(int row = 0; row < nRows; row++){
            for(int col = 0; col < nColumns; col++){
                grid[row][col] = ConnectFourEnum.EMPTY;
            }
        }
        
    }
    
    /**
     * This getter method gets the current turn value.
     * @return Returns the symbol for player who's turn it is currently.
     */
    public ConnectFourEnum getTurn(){
        return turn;
    }
    
    /**
     * This getter method gets the current gamestate.
     * @return Returns the current TicTacToeEnum value for the gamestate.
     */
    public ConnectFourEnum getGameState(){
        return gameState;
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
     * This method takes in a ConnectFourEnum player and returns the opposite player.
     * @param lastPlayer An ConnectFourEnum of the last player who had their turn.
     * @return the player how had their turn next.
     */
    public ConnectFourEnum switchTurns(ConnectFourEnum lastPlayer){
        if(lastPlayer == ConnectFourEnum.RED){
            return ConnectFourEnum.BLACK;
        }
       else{
            return ConnectFourEnum.RED;
        }
    }
    /**
     * This method checks if the row and column input for the turn are within boundaries or if the spot the player
     * is trying to place their symbol is already taken, before changing the grid for the gameboard to complete the turn. The method also 
     * checks if someone won the game, and notifies observers about changes in the game.
     * @param row The row the player wants to place their symbol.
     * @param column The column the player wants to place their symbol.
     * @throws IllegalArgumentException if the turn taken is not on the game board, or not at the bottom or directly on top of another connectFour checker.
     * @return Returns the current TicTacToeEnum value after the turn is taken.
     */
    public ConnectFourEnum takeTurn( int row, int column){
     
        if (row < 0 || (row >= nRows) ) {
            throw new IllegalArgumentException ("Put a nice number of rows");
        } 
        if (column < 0 || (column >=nColumns) ) {
            throw new IllegalArgumentException ("Put a nice number of columns");
        }
        if(row != 0){
            if(grid[row-1][column] == ConnectFourEnum.EMPTY ){
                throw new IllegalArgumentException ("You can only place checkers directly above other checkers or at the bottom of the board.");
            }
        }
        
        if(grid[row][column] != ConnectFourEnum.EMPTY){
            throw new IllegalArgumentException ("That position was already taken, Please enter the checker at a different row and column"); 
        }else{
            grid[row][column] = turn;
            this.setChanged();
            
            connectMove = new ConnectMove(row,column,turn);
            
            this.notifyObservers(connectMove);
            
            this.gameState = findWinner(row,column);

            nMarks++;
        
            if((nRows * nColumns) == nMarks){           
                return gameState = ConnectFourEnum.DRAW;
            }
            turn = switchTurns(turn);
           
        }
        
       return gameState;
    }
    
    /**
     * This method checks if any of the players won by checking if enough symbols aligned to win, for both horizontal rows and vertical columns.
     * @return Returns the current TicTacToeEnum value after checking if any player won.
     */
    private ConnectFourEnum findWinner(int row, int column){
        
        int counter = 0;
        
        
        // checks the row the checker was placed on
        for( int col = 0; col < nColumns ; col++){                           
            if( grid[row][col] == turn){              
                counter++;
            }else{
                counter = 0;
            }
            if( counter == numToWin){
                return turn;

            }
           
        } 
        counter = 0;
         
       
        
        //checks the column the checker was placed in
        
        for( int rows = 0; rows < nRows; rows++){
            if( grid[rows][column] == turn ){
                counter++;
            }else{
                counter = 0;
            }
            if( counter == numToWin){
                return turn;
            }
            
        } 
        counter = 0; 
        
        //finds the first checker on the upper right to bottom left diagonal column
        int initialRow = row;
        int initialColumn = column;
        while((initialRow + 1) < nRows && (initialColumn - 1) > -1){
            initialRow++;
            initialColumn--;
        }

        //checks the diagonal column starting from the checker found in the loop above
        for(int shift = 0; (initialRow - shift) > -1 && (initialColumn + shift) < nColumns; shift++){
            if( grid[initialRow - shift][initialColumn + shift] == turn ){
                counter++;
            }else{
                counter = 0;
            }
            if( counter == numToWin){
                return turn;
            }
        }
        counter = 0;
        
        initialRow = row;
        initialColumn = column;
        //finds the first checker on the upper left to bottom right diagonal column
        while((initialRow + 1) < nRows && (initialColumn + 1) < nColumns){
            initialRow++;
            initialColumn++;
        }
      
        //checks the diagonal column starting from the checker found in the loop above
        for(int shift = 0; (initialRow - shift) > -1 && (initialColumn - shift) > -1; shift++){
            if( grid[initialRow - shift][initialColumn - shift] == turn ){
                counter++;
            }else{
                counter = 0;
            }
            if( counter == numToWin){
                return turn;
            }
        }
        counter = 0;
       
        
       
        return ConnectFourEnum.IN_PROGRESS;
    }
    
    /**
     * This method constructs a string of the grid for the players to have a visual representation of the current gameboard.
     * @return Returns a string of the current game board.
     */
    public String toString(){
     
        String gameboard= "";
        
        for(int row = nRows - 1; row >= 0; row--){
            for( int col = 0; col < nColumns; col++){ 
                gameboard += EnumToString(grid[row][col]) + " | ";
            }
            gameboard+= "\n";
        }
        return gameboard;
    }
    
}
