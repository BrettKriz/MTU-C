import java.util.Scanner;
import java.lang.Throwable;
import java.lang.Exception;

/**
 * Player class
 * Represents a player of Uno
 * @author Brett Kriz
 */
public class Player {

    private int ID;
    private Pile hand;
    private boolean playable = true; // No AI for now
    private int score = 0;
    public static final int SCORE_CAP = 500;
    private static boolean canWin = true;
    private boolean Winner = false;
    
    public Player(int id){
        ID = id;
        hand = new Pile(false);
    }
    public Player(int id, boolean userPlay){
        ID = id;
        hand = new Pile(false);
        playable = userPlay;
    }
    public boolean isWinner(){
        //if (Winner != null){
            return Winner;
       // } else {
           // return false;
        //}
    }
    public void resetWinner(){
        Winner = false;
    }
    public void WinnerFound(){
        this.Winner = true;
        canWin = false;
    }
    public static void WinnerFound(Player winner){
        winner.Winner = true;
        canWin = false;
    }
    public static boolean HasWinner(){
        return !canWin;
    }
    public static void setCanWin(boolean arg){
        canWin = arg;
    }
    public Pile getHand(){
        return hand;
    }
    public void setHand(Pile pile){
        hand = pile;
    }
    public void addPoint(int point){
        score = Math.max(score + point, 0);
    }
    public int getScore(){
        return score;
    }
    public boolean hasLostGame(){
        return (score >= SCORE_CAP);
    }
    public boolean hasWonRound(){
        return (hand.getIndex() <= 0);
    }
    public Card playCard(int index) throws NullPointerException, IndexOutOfBoundsException{
        Card temp = hand.cardAt(index);
        hand.remove(index);
        return temp;
    }
    public void removeCard(Card card) throws IndexOutOfBoundsException{
        hand.remove(card);
        if (hand.getIndex() <= 0){ // Won round
            Player.WinnerFound(this);
        }
    }   
    public void addCard(Card card) throws NullPointerException{
        if (Winner){
            return;
        }
        if (card == null){
            throw new NullPointerException();
        }
        hand.add(card); 
    }
    public void draw(Pile deck,int amount) throws NullPointerException{
        if ( isWinner() || hasWonRound() || amount == 0 ){
            return;
        }

        if (deck == null){
            throw new NullPointerException();
        }
        
        int count = 0;
        while(count != amount ){
            deck.moveCardToPile(deck.getIndex()-1, hand); //All in 1 function
            count++;
        }
        
    }
    public int play(String topCard){
        // incase of AI
        // But thats for later <,<
        if (Winner){
            return 0;
        }
        // Show the play menu
        if (playable) {
            System.out.println(this.toString());
            System.out.println(topCard);
            System.out.print("Which card would you like to play? ");
            
            return (new Scanner(System.in).nextInt()-1); //for ease
        } else {
            System.out.println(this.toString());
            System.out.println(topCard);
            System.out.print("AI Player " + this.ID + " plays a ");
            
            int temp = (int)(Math.random() * hand.getIndex());
            Card temp2 = hand.cardAt(temp);
            System.out.println(""+temp2+"!");
            
            return temp;
        }// END IF
    }
    public String selectColor(){
        if (playable){
            System.out.println("You have played a wild. What color do you declare? ");
            int len = Card.COLORS.length-1;
            for (int x = 0; x < len; x++){
                System.out.println("  " + x + "  " + Card.COLORS[x]);
            }
            int pick = new Scanner(System.in).nextInt();
            
            pick = Math.max( Math.min(pick, len), 0);
            
            return Card.COLORS[pick];
        } else {
            return Card.COLORS[ (int)(Math.random()*3) ];
        }
    }
    @Override
    public String toString(){
        String ans = "\n- Player " + (this.ID+1) + " -\n";

        return ans + hand.toString();
               
    }
        
}
