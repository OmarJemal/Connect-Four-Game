/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import javafx.scene.control.Button;
/**
 * This ConnectButton class implements a ConnectFour checker for the connectfourApplication class, and includes the button's position (row, column), and the label of the button.
 * @author Omar Jemal
 * @since 05/12/2018
 * @version 1.0
 */
public class ConnectButton extends Button {
    private int row;
    private int column;
    private String label;
    private boolean isClicked;
    
    /**
     * Constructs a connectFour button for the ConnectFourApplication.
     * @param label the label of the checker, empty if the checker doesn't belong to anyone or has the color of the player
     * @param row the row the checker is on.
     * @param column the column the checker is on.
     */
    public ConnectButton( String label, int row, int column, boolean isClicked){
        super(label);
        this.label = label;
        this.column = column;
        this.row = row;
        this.isClicked = isClicked;
        
    }
    /**
     * This getter returns the row the button is on.
     * @return the integer value of the row the button is on.
     */
    public int getRow(){
        return row;
    }
    /**
     * This getter returns the column the button is on.
     * @return the integer value of the column the button is on.
     */
    public int getColumn(){
        return column;
    }
    /**
     * This setter sets the label on the button.
     * @param label the string value that the button's label will be set to.
     */
    public void setLabel(String label) {
        this.label = label; /// needs to change the parent class's label for the button gg dude
        this.setText(label);
    }
    
    public void setIsClicked( boolean isClicked){
        this.isClicked = isClicked;
    }
    
    public boolean getIsClicked(){
        return this.isClicked;
    }
    /**
     * This method returns a string of the buttons coordinates.
     * @return a string of the button's coordinates in the form (<row>,<column>).
     */
    public String toString(){
        return "(" + Integer.toString(row) + "," + Integer.toString(column) + ")";
    }
    
}
