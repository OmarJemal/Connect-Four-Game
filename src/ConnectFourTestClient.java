/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.Scanner;

/**
 * The ConnectFourTestClient class implements a game of ConnectFour using the ConnectFourGame class as a game engine.
 * 
 *@author Omar Jemal
 *@since 05/12/2018
 *@version 1.0
 */
public class ConnectFourTestClient {
    
    /**
     * 
     * The main method is a client of the ConnectFourGame class, and allows the user to play connectFour.
     */
    public static void main(String[] args){
        ConnectFourGame game = new ConnectFourGame(ConnectFourEnum.BLACK);
        Scanner scanner = new Scanner(System.in);

        do { 
            System.out.println(game.toString());
            System.out.println(game.getTurn() + ": Where do you place your checker ? Enter row column");
            int row = scanner.nextInt();
            int column = scanner.nextInt();

            try{
            game.takeTurn(row, column);
            }
            catch(IllegalArgumentException e){
                System.out.println(e.getMessage());
                continue;
            }
            
        } while (game.getGameState() == ConnectFourEnum.IN_PROGRESS);
            System.out.println( game.getGameState().toString());
       
    }  
    }

