import java.lang.Throwable;
import java.util.Objects;

/**
 * Card Class
 * Holds data for cards to interact with Pile
 * @author Brett Kriz
 */
public class Card {
    protected String color;
    protected String rank;
    public static final String[] RANKS = {"0","1", "2", "3", "4", "5", "6", "7", "8", "9",
        "Skip", "Reverse", "Draw Two", "Draw Four"};
    public static final String[] COLORS = {"Red","Green","Blue","Yellow","Wild"};
    protected static final String WILD = "Wild"; // for slight ease
    
    public Card(String color, String rank) throws IllegalArgumentException{
        boolean rankFlag = false, colorFlag = false;
        
        // Catch wild cases right off the bat
        if ((rank.equals("") || rank.equals("Draw Four")) && color.equals(WILD)){
            this.color = color;
            this.rank = rank;
            return;
        }
        
        // Screen the input
        for(int x = 0; x < RANKS.length-1; x++){
            if (RANKS[x].equals(rank) && !RANKS[x].equals("Draw Four")){
                rankFlag = true; // Its fine
                break;
            }
        }
        for (int x = 0; x < COLORS.length-1; x++){
            if (COLORS[x].equals(color) && !COLORS[x].equals(WILD)){
                colorFlag = true;
                break;
            }
        }
        
        if (!(rankFlag && colorFlag)){
            // Bad card
            
            throw new IllegalArgumentException();
        }
        
        this.color = color;
        this.rank = rank;
        
    }
    public String getColor(){
        return this.color;
    }
    public String getRank (){
        return this.rank;
    }
    public boolean stackable(Card onCard, String gamesColorArg) throws NullPointerException{
        // THIS can stack on given card?
        if (onCard == null){
            throw new NullPointerException();
        }
        boolean flag = ( this.color.equals(onCard.color) || this.rank.equals(onCard.rank) );
        
        if (this.color.equals(WILD)){
            return true;
        }
        if (onCard.color.equals(WILD)){
            // Wild is down, can THIS stack?
            if (gamesColorArg.equals("")){
                gamesColorArg = "Red";
            }
            
            return ( this.color.equals(gamesColorArg) );
        }
        
        return flag;
    }
    @Override
    public boolean equals(Object object){
        if (!(object instanceof Card)){
            return false; // Bad type
        }
        Card card = (Card)object;
        
        return ( this.color.equals(card.color) && this.rank.equals(card.rank) );
    }

    @Override
    public int hashCode() { // Netbeans autofill is so nice
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.color);
        hash = 29 * hash + Objects.hashCode(this.rank);
        return hash;
    }
    @Override
    public String toString (){
        return this.color + " " + this.rank;
    }
}
