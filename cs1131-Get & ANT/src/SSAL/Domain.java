package SSAL;
import java.util.*;


/**
 * SSAL
 * @author Brett Kriz
 */
public interface Domain<E>{
    // Starts project lol
    public E start();
    public boolean isGoal(E e);
    public Set<E> repeat(E e);
}