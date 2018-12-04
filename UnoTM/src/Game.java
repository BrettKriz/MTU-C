
/**
 * Game class
 * A class to represent the progress of a game
 * @author Brett Kriz
 */
public class Game {

    protected Pile deck, discard;
    protected int turn = 0;
    public Player[] PLAYERS;
    protected String color;
    private int rotation = 1; //CLOCKWISE
    private Boolean isTie = null;
    private boolean game_Over = false;
    private int[] move_History;
    
    public static final int DISCARD_SIZE = Pile.MAX_CARDS*2;
    public static final int STARTING_HAND_SIZE = 2;
    public static final int ACTION_CARD_POINT_VAL = 10;

    public Game(Pile Deck , int players) throws IllegalArgumentException{
        if (players <= 1 || players > 8 || Deck == null){ // 2-8 players
            throw new IllegalArgumentException();
        }
        PLAYERS = new Player[players]; //
        setupRound(Deck);
    }
    // This is more suitable for a new game
    public Game(int players) throws IllegalArgumentException{ 
        
        if (players <= 1 || players > 8){ // 2-8 players
            throw new IllegalArgumentException();
        }
        PLAYERS = new Player[players]; //
        setupRound();
    }
    public void setupRound(Pile Deck){
        setupRound();
        deck = Deck;
    }
    public void setupRound(){
        deck = new Pile(true);
        isTie = null;
        discard = new Pile(false);
        Pile.decks_made = 0;
        turn = 0;
        move_History = new int[PLAYERS.length];
        for (int x = 0; x < PLAYERS.length-1; x++){
            move_History[x] = -2; //Everyone just drew cards
        }
        setupPlayers();
        color = deck.moveCardToDiscard(deck.getIndex()-1, discard);
    }
    public boolean isRoundOver (){ // @@@
        boolean playerFlag = false;
        for(int x = 0; x < PLAYERS.length-1; x++){
            if (PLAYERS[x].hasWonRound()){
                playerFlag = true;
                break;
            }
        }
        return (isTie != null || playerFlag); // At player 1
    }
    public boolean isGameOver(){
        // Loop through player scores
        if (PLAYERS == null){
            return false;
        }
        
        for (int x = 0; x < PLAYERS.length-1; x++){
            boolean b2 = (PLAYERS[x].hasLostGame());
            if (b2 ){
                Player.setCanWin(true);
                return true;
            }
        }
        return false;
    }
    public void setupPlayers(){
        // Create all players needed
        // Give out hands & ID's
        int[] scores = new int[PLAYERS.length];
        for (int z = 0; z < PLAYERS.length; z++){
            if (PLAYERS[z] != null){
                scores[z] = PLAYERS[z].getScore();
            }
            PLAYERS[z] = new Player(z);
            for (int x = 0; x < STARTING_HAND_SIZE; x++){ // GIVE 7 cards
                if (deck.getIndex() <= 0){
                    // Out of cards]
                    Pile.decks_made = 0;
                    deck = new Pile(true);
                    deck.shuffle();
                }
                Card cur = deck.cardAt( deck.getIndex()-1 );
               PLAYERS[z].addCard( cur );
               deck.remove(deck.getIndex()-1);
            }
        }
        
        for (int y = 0; y < PLAYERS.length; y++){
            PLAYERS[y].addPoint(scores[y]);
        }
    }
    public String roundResults(int winner){

        // Compute scores
        int[] scores = new int[PLAYERS.length];
        String ans = "Player " + (winner+1) + " wins the round.\n";
        if (isTie != null && isTie == true){ // Thats a tie signal
            ans = "Tie round!\n";
        }
        
        for (int x = 0; x < PLAYERS.length; x++){
            scores[x] = PLAYERS[x].getScore();
            ans += "Player " + (x+1) + " -- " + scores[x];
            if (x != PLAYERS.length-1){
                ans += "\n";
            }
        }

        int lowest = Integer.MAX_VALUE;
        int lowestIndex = -1, otherIndex = -1;
        for (int x = 0; x < PLAYERS.length-1; x++){
            if (scores[x] < lowest){
                lowest = scores[x];
                lowestIndex = x;
            }else if (scores[x] == lowest){
                otherIndex = x;
                isTie = true; // To be checked
            }
        }// END FOR
        
        if (isGameOver()){
            String winSTR = "No-one";
            // Make sure the lowest was tied
            if (isTie != null && isTie == true){
                if (scores[otherIndex] == lowest && otherIndex != lowestIndex ){
                    // The tie is real...
                    winSTR = "There is a tie between Player "+(lowestIndex+1)+" and Player "+(otherIndex+1);
                } else {
                    isTie = false;
                    Player.WinnerFound(PLAYERS[lowestIndex]);
                    winSTR = "Player "+(lowestIndex+1)+" wins the game!";
                }
            } else {
                Player.WinnerFound(PLAYERS[lowestIndex]);
                winSTR = "Player "+(lowestIndex+1)+" wins the game!";
            }
            
            ans += "\n" + winSTR;
        }// END IF
        
        return ans;
    }
    public int players (){
        return PLAYERS.length;
    }
    public Pile pile (){
        return discard;
    }
    public Pile deck (){
        return deck;
    }
    public Pile hand(int player) throws IllegalArgumentException{
        // Reference players by their NON-index point
        if (player < 0 || player >= PLAYERS.length ){
            throw new IllegalArgumentException();
        }
        
        return PLAYERS[player].getHand();
    }
    public int[] scores (){
        int[] result = new int[PLAYERS.length];
        
        for(int x = 0; x < PLAYERS.length-1; x++){
            result[x] = PLAYERS[x].getScore();
        }
        
        return result;
    }
    public void closeRound(){
        // Close the current round
        // Tally scores
        for(int x = 0; x < PLAYERS.length; x++){
            Pile pile = PLAYERS[x].getHand();
            for(int c = 0; c < pile.getIndex()-1; c++ ){
                Card temp = pile.cardAt(c);
                
                if (temp != null){
                    // Calculate card value
                    String arg = temp.getRank();
                    String arg2 = temp.getColor();
                    int points;

                    if (arg.equals("Skip") || arg.contains("Draw") || arg.equals("Reverse") || arg2.equals("Wild")){
                        points = ACTION_CARD_POINT_VAL;
                    }else{
                        points = Integer.parseInt( arg );
                    }

                    PLAYERS[x].addPoint(points);
                }//END IF
            }
            
        }
    }
    public void newRound(){
        // Reset for new round
        if (discard.getIndex() > Pile.MAX_CARDS){
            discard.clearPile();
        }
        // Clear winners and other stats, NOT SCOREs
        Player.setCanWin(true);
        for(int z = 0; z < PLAYERS.length-1; z++ ){
            if (PLAYERS[z].isWinner()){
                PLAYERS[z].resetWinner();
            }
        }
        setupRound();
    }
    public int nextTurn(){
        // Before next turn
        // Check for 'out of cards' or game loosers
        int rwc = -1;
        int passcount = 0;
        for(int x = 0; x < PLAYERS.length; x++){
            if (move_History[x] == -1){
                passcount++;
            }
            if (PLAYERS[x].isWinner() || PLAYERS[x].hasWonRound()){
                rwc = x;
            }
            if (PLAYERS[x].hasLostGame()){
                game_Over = true;
            }
        }
        
        
        if ( rwc == -1 && (passcount == PLAYERS.length) ){
            isTie = true;
            rwc = PLAYERS.length;
        }
        
        changeTurn(); // prevent player one from being skipped on start

        return rwc;
    }

    public void applyDrawCard(int whoPlayed, int numberOfCards){
        // rotation affects the direction
        int target = mod(whoPlayed + rotation, PLAYERS.length);
        //                  (turn + rot % #ofPLAYERS)
        penalizePlayer( target , numberOfCards );
    }
    public void reverseRotation(){
        rotation = rotation*(-1);
    }
    public void penalizePlayer(int player, int numberOfCards){
        PLAYERS[player].draw(deck, numberOfCards);
    }
    public boolean playersTurn() throws IllegalArgumentException{
        // External call
        return playersTurn(turn);
    }
    private boolean playersTurn(int player) throws IllegalArgumentException{
        if (player < 0 || player >= PLAYERS.length){
            throw new IllegalArgumentException();
        }
        // Find the player
        String str = lastCardPlayedSTR();
        int index = PLAYERS[player].play(str);
        
        // For debuging purposes
        if (index == -1338 || index == -1337){
            // WIN NOW
            Player.WinnerFound(PLAYERS[player]);
            PLAYERS[player].getHand().clearPile();
            //for (int x = 0; x < PLAYERS.length-1; x++ ){
            System.out.println("CHEATS USED: WIN NOW");
            return true;
        }else if (index == -102){
            // FAT DECK
            Pile.decks_made = 0;

            for (int x=0; x<50; x++){
                PLAYERS[player].getHand().fillPile() ; // add a deck
            }
            //for (int x = 0; x < PLAYERS.length-1; x++ ){
            System.out.println("CHEATS USED: FAT DECK");
            return true;
        }
        move_History[turn] = index;
        
        boolean check1 = (index > PLAYERS[player].getHand().getIndex());
        boolean check2 = (index <= -1 && index != -2);
        
        if ( check2 || check1){ // pass is 0 in line, - 1
            System.out.println("Player " + (player+1) + " has chose to pass.");
            pass(); 
        } else if (index == -2 ) { // DRAW is -1, - 1
            System.out.println("Player " + (player+1) + " has chose to draw a card.");
            PLAYERS[player].draw(deck, 1);
        } else {
            // PLAY CARD - Section has ghost halts
                if (index < 0 || index > PLAYERS[turn].getHand().getIndex()){
                    throw new IllegalArgumentException();
                }
                Card card = PLAYERS[turn].getHand().cardAt(index);
                System.out.println("Player " + (player+1) + " has chose to play "+card);
                
                if ( !card.stackable( lastCardPlayed(), color ) ){
                    // PENALTY
                    System.out.println("\n! Penalty +2 cards for Player "+ (turn+1) +"!");
                    penalizePlayer(turn, 2);
                    return false;
                }
                // Check the play that was made
                if ( card.getColor().equals("Wild")  ){
                    // Configure wilds
                    this.setColor( PLAYERS[turn].selectColor() );
                    if ( card.getRank().equals("Draw Four")  ){
                        applyDrawCard( turn, 4);
                    }
                }else if( card.getRank().equals("Draw Two") ){
                    applyDrawCard( turn, 2 );
                }else if( card.getRank().equals("Skip") ){
                    skip();
                }else if( card.getRank().equals("Reverse") ){
                    reverseRotation();
                }

                // Since its passed the checks, we now move the card
                discard.add(card);
                PLAYERS[turn].removeCard(card);
            // END PLAY
                
        }// END IF BLOCK

        return true;// for testing purposes
    }
    public void pass() throws IllegalArgumentException{
        // We need to check if the last card
        // was a wild & the color wasnt set
        if ( lastCardPlayed().getColor().equals("Wild") ){
            if( color.equals("Wild") || color.equals("") || color == null ){
                 throw new IllegalArgumentException();
                 // 
            }
        }
    }
    protected void setColor(String clr) throws IllegalArgumentException{
        boolean go = false;
        for (int x = 0; x < Card.COLORS.length-1; x++){
            if (Card.COLORS[x].equals(clr)){
                go = true;
                break;
            }
        }
        
        if (!go) {
            throw new IllegalArgumentException();
        }
        color = clr;
    }
    public Card lastCardPlayed(){
        int i = discard.getIndex()-1;
        Card temp = discard.cardAt(i);
        return temp;
    }
    public String lastCardPlayedSTR() throws IllegalArgumentException{
        Card temp = lastCardPlayed();
        String ans = "The top of the pile is a ";
        if (temp == null){
            System.out.println("NULLS????");
            throw new IllegalArgumentException();
        }
        String clr = temp.toString();
        
        // Consider instances of wilds
        if (temp.getColor().equals("Wild")){
            clr = "Wild.  The chosen color is "+color;
        }
        ans += clr+".\n";
        
        return ans;
    }
    private void changeTurn(){
        changeTurn(1); // 1 is the ammount
    }
    private void skip(){
        changeTurn(2); // 1 would be the next persons turn
    }
    private void changeTurn(int spaces){ // Dominant changeTurn/skip
        int temp = mod(turn + (spaces*rotation), PLAYERS.length);
        // for debuging
        turn = temp;
    }
    private int mod(int base, int limit){
        return ((base % limit) + limit) % limit; //Modulus
    }
}
