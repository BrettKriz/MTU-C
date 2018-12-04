
import java.lang.Exception;
import java.lang.Throwable;

/**
 * Pile (of cards)
 * Holds the array of cards
 * and handles dealing
 * @author Brett Kriz
 */
public class Pile {
    
    private int cards_index = 0;
    private Card[] cards;
    public static final int MAX_CARDS = 109; //Max cards for a deck, not enforced
    public static int decks_made = 0;
    
    public Pile(int capacity) throws IllegalArgumentException{
        if (capacity < 0){ 
            throw new IllegalArgumentException();
        }
        cards = new Card[capacity];
        clearPile();
    }
    public Pile(){
        // Assumes deck of max size
        cards = new Card[MAX_CARDS];
        clearPile();
    }
    public Pile(Boolean fill){
        if (fill == true && (decks_made == 0)){
            
            cards = new Card[MAX_CARDS];
            clearPile();
            
            
           // try{
                fillPile();
                shuffle();
            //} catch (IllegalArgumentException e){
                //e.printStackTrace();
            //}
            // TEST OUTPUT HERE
            
        } else {
            cards = new Card[MAX_CARDS];
            clearPile();
        }
        condenseCards();
    }
    public int getIndex(){
        return cards_index;
    }
    public void fillPile() throws IllegalArgumentException{
        /*
          SO, 2 of each card action thu 1, no Draw Four
          1 zero of each color
          4 Draw 4 wilds
          4 wild cards
          108 each
        
        */
        int ccount = 0;
        //                          5-2 = 0,1,2,3
        for (int clr = 0; clr < Card.COLORS.length-1; clr++){//
            //int x = 0;
            //                          14-1 = 13 
            for(int x = 0; x < Card.RANKS.length-1; x++){ // -2 because last is Draw Four
                if (x == 0){
                    // 1 card only 
                    Card temp = new Card( Card.COLORS[clr], Card.RANKS[x]);
                    //System.out.println(temp);
                    this.add( temp );
                    ccount++;
                } else {
                    Card t1 = new Card( Card.COLORS[clr], Card.RANKS[x]);
                    Card t2 = new Card( Card.COLORS[clr], Card.RANKS[x]);
                    //System.out.println(t1);
                    //System.out.println(t2);
                     this.add( t1 );
                     this.add( t2 );
                    ccount += 2;
                }
                //x++;
            }// END WHILE
        }
        // Create wilds
        for(int x = 0; x < 4; x++){
                this.add( new Card( "Wild", "") );
                this.add( new Card( "Wild", "Draw Four") );
                ccount += 2;
        }

        decks_made += 1;
    }
    public Pile(Pile pile) throws NullPointerException{
        if (pile == null){
            throw new NullPointerException();
        }
    }
    public void clearPile(){
        // Insert nulls into array
        // And reset index
        for(int x = 0; x < cards.length-1; x++){
            cards[x] = null;
        }
        cards_index = 0;
    }
    public void add(Card card) throws NullPointerException{
        // Add cards to Cards
        if (card == null){
            throw new NullPointerException();
        }
        int x;
        boolean isFull = true;
        for( x = this.cards_index; x < cards.length-1; x++){
            if (cards[x] == null){
                // Open space
                isFull = false;
                break;
            } else {
                System.out.println("> Not open @ "+x);
            }
        }
        
        if (isFull){
            System.out.println("!More than 1 full deck in play! CANNOT ADD!");
        }
        
        cards[x] = card;
        changeCardIndex(1);
        condenseCards();     
    }
    public void remove(int pos) throws IndexOutOfBoundsException{
        if (pos < 0 || pos >= cards.length){
            throw new IndexOutOfBoundsException();
        }
        cards[pos] = null;
        changeCardIndex(-1);// subtract from index
        condenseCards();
    }
    public void remove(Card card) throws NullPointerException, IndexOutOfBoundsException{
        // Find a specified card
        int x = 0;
        while(x < this.cards_index && x < this.cards.length){
            if (this.cards[x].equals(card)){
                // found index
                cards[x] = null;
                changeCardIndex(-1);
                condenseCards();
                return;
            }
            x++;
        }
 
        // Something is wrong if it reaches here
        throw new NullPointerException();
    }
    private void changeCardIndex(int arg){
        this.cards_index = Math.max( Math.min(this.cards_index + arg, Pile.MAX_CARDS) , 0);
    }
    public void moveCardToPile(int pos, Pile otherPile) throws NullPointerException, IndexOutOfBoundsException{
        if (pos < 0 || pos >= cards.length){
            throw new IndexOutOfBoundsException();
        }
        if (otherPile == null){
            throw new NullPointerException();
        }
        // Easy function to transfer cards between piles
        Card temp = this.cardAt(pos);
        this.remove(pos);
        
        otherPile.add(temp); // The deed is done
        
    }
    public String moveCardToDiscard(int pos, Pile otherPile) throws NullPointerException, IndexOutOfBoundsException{

        if (pos < 0 || pos >= cards.length){
            throw new IndexOutOfBoundsException();
        }
        if (otherPile == null || cards[pos] == null){
            throw new NullPointerException();
        }
        // Easy function to transfer cards between piles
        String clr = cardAt(pos).color;
        if (clr.equals("Wild")){
            clr = "Red";
        }
        otherPile.add(cardAt(pos)); // The deed is done
        this.remove(pos);
        
        return clr;
    }
    public Card cardAt(int pos) throws IndexOutOfBoundsException{
        // assumes base 0
        if (pos < 0 || pos > cards.length){
            throw new IndexOutOfBoundsException();
        }
        return cards[pos];
    }
    public void shuffle (){
        // Fisher-Yates shuffle
        for(int i = cards.length-1; i > 0; i-- ){
            int index = (int)(Math.random()*i);
            // Swap
            Card temp = cards[index];
            cards[index] = cards[i];
            cards[i] = temp;
        }
        condenseCards();
    }
    public void condenseCards(){
        // Make sure there arn't nulls randomly about
        Card[] cards_rev = new Card[ cards.length ]; //getIndex()'
        int index = 0;
        for (int x = 0; x < cards.length-1; x++ ){
            if ( cards[x] instanceof Card){
                cards_rev[index] = cards[x];
                index++;
            }
        }
        this.cards = cards_rev;
        this.cards_index = index;
       
    }
    @Override
    public String toString(){
        String ans = "";
        
        for(int x = 0; x < this.getIndex(); x++){
            ans += "  " + (x+1) + "  " + cards[x].toString();
            if ( x != this.getIndex()-1 ){ // last peice
                ans += "\n";
            }
        }
        return ans;
    }
    
    
}
