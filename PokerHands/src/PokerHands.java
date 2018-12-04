import java.util.Scanner;
import java.lang.*;

public class PokerHands{
    
    public static void main (String[] args){
    Scanner in = new Scanner(System.in);
    int rowsWanted = in.nextInt();


    //stars on the top row
    int starsWanted = 1;
    //spaces on the top row
    int spacesWanted = (rowsWanted * 2) + 3;

    for (int rows = 0; rows < rowsWanted; rows++) {
            for (int spaces = 0; spaces < spacesWanted; spaces++) {
                System.out.print(" ");
            }
            for (int stars = 0; stars < starsWanted; stars++) {
                System.out.print("x");
            }
            System.out.println();
            starsWanted += 2;
            spacesWanted--;
    
    }
    /* ------------------------------------------------------------------- */
            starsWanted = 3;

            spacesWanted = rowsWanted;

        for (int rows = 0; rows < rowsWanted; rows++) {
            for (int spaces = 0; spaces < spacesWanted + 2; spaces++) {
                System.out.print(" ");
            }
            for (int stars = 0; stars < starsWanted; stars++) {
                System.out.print("x");
            }

            for (double spaces = 0; spaces < (spacesWanted * 2) - 3; spaces++) {
                System.out.print(" ");
            }
            for (int stars = 0; stars < starsWanted; stars++) {
                System.out.print("x");
            }
            System.out.println();
            starsWanted += 2;
            spacesWanted--;

        }      
    }
}
