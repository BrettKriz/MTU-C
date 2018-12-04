
/**
 *
 * @author Brett Kriz
 */
public class Enums {
    enum Direction{// STATIC FINAL REFERENCES IN DIRECTION
        /*
        Direction.N.xOff()
        Direction.E.yOff()
        2nd to Last homework will require enumerations
        
        Direction.values(); // Produces iterable collection of each element
            ordered by definition in code
        Direction.S.euqals(dir) // Will work because theres only 1 instance of S
        Direction dir = Directio.W; // Equals the reference
        
        Some limitations:
            Enum explicitly extends a class called Enumerations, so no double extending
            However, Enumes can implement
            
        
        */
        N (0,1)     ,
        E (1,0)     , 
        W (-1,0)    , 
        S (0,-1)     ;
    }
}
