/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 * The ConnectMove class implements a move in the game of connectFour.
 * 
 * @author Omar Jemal
 * @since 05/12/2018
 * @version 1.0
 */
public class ConnectMove {
    private int row;
    private int column;
    private ConnectFourEnum color;
    /**
     * Constructs a connect four move.
     * @param row the row which the player decided to move their checker.
     * @param column the column which the player decided to move their checker.
     * @param color the player who's move was taken.
     */
    public ConnectMove(int row, int column, ConnectFourEnum color) {
        this.row = row;
        this.column = column;
        this.color = color;
    }
    /**
     * this getter method returns the row of the move of the player.
     * @return an integer value of the row which the player decided to move their checker.
     */
    public int getRow() {
        return row;
    }
    /**
     * this getter method returns the column of the move of the player.
     * @return an integer value of the column which the player decided to move their checker.
     */
    public int getColumn() {
        return column;
    }
    /**
     * this getter method returns the color of the checker the player moved, indicating the player who's move was taken.
     * @return an ConnectFourEnum of the player the checker belonged to.
     */
    public ConnectFourEnum getColor() {
        return color;
    }
    
    
}
