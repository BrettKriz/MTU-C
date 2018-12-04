import java.util.Scanner;

/**
 * PROGRAM 4 - CS1131
 * UNO(tm) 
 * 
 * This and other classes
 * create the game of Uno(tm)
 * 
 *! Uno and all variations of the card game !
 *! are the property of Mattel, Inc !
 *
 * I did however write this interpretation
 * of the offical card game.
 * 
 * @author Brett Kriz
 */
public class UnoTM {
    // Creates a terminal user interface
    
    public static void main(String[] args){
        // 
        UnoTM a = new UnoTM();
    }
    
    public UnoTM(){
        // Hahaha
        endGame(startGame());
    }
    private String startGame(){
        // Start a new game
        boolean done = false;
        Scanner in = new Scanner(System.in);
        
        System.out.print("How many players? ");
        int players = in.nextInt();
        System.out.println("Enter -1 to draw a card, 0 to pass");
        
        Game game = new Game(players);
        String gameResults = "";
        
        while(!game.isGameOver()){
            boolean flag = game.playersTurn();
            int roundfeed = game.nextTurn(); // Power the turns

            if (roundfeed != -1 || game.isRoundOver() || Player.HasWinner()){
                // Round winner found
                game.closeRound(); // Create scores
                gameResults = game.roundResults(roundfeed);
                game.newRound();
                System.out.print("~~~~~~~~~~~~~~~~\n\n"+gameResults+"\n\n\n");
            }
        }
        return gameResults;
    }
    
    private void endGame(String results){
        // Done
        System.out.println(results);
    }
}
