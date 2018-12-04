package SSAL;
import java.util.*;

/**
 * SEARCH
 * @author Brett Kriz
 */
public interface Search<E> {
    public List<E> search(Domain<E> domain);
    
}
