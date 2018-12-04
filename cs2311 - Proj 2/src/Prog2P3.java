import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Scanner;


/**
 * Program 2 Part 3
 * 
 * @author Brett Kriz
 */
public class Prog2P3 extends AbstractClass {
    
    // Lets read it in!
    ArrayList<String> Lines = new ArrayList<String>();
    // TrackInfo

    //public final String 
    public enum field {
        //USER, RANK, TITLE, ARTIST, ALBUM, GENRE, PLAYS
        USER ("USER"),
        RANK ("RANK"),
        TITLE ("TITLE"),
        ARTIST ("ARTIST"),
        ALBUM ("ALBUM"),
        GENRE ("GENRE"),
        PLAYS ("PLAYS");
        
        public final String value;
        field(String value){
            this.value = value;
        }
    }
    
    public enum method {
        EUCLIDEAN ("EUCLIDEAN"),
        PEARSON ("PEARSON"),
        CUSTOM ("OTHER"),
        OTHER ("OTHER");
        
        public final String value;
        method(String value){
            this.value = value;
        }
    }

    public static void main(String[] args){
        Prog2P3 test = new Prog2P3();
        
        String aa = "X2127793203, ";
        
        double dis = test.calculateSimilarity( "X2006067033", "X1326146154", "GENRE", method.EUCLIDEAN.value );
        double diss = test.calculateSimilarity( "X2006067033", "X1326146154", "GENRE", method.PEARSON.value );
        double diss2 = test.calculateSimilarity( "X2006067033", "X1326146154", "GENRE", method.OTHER.value );
        System.out.println(""+ dis + " vs " + diss + " vs " + diss2);
        
        
        
        HashMap<String, Double> dis2 = test.calculateAllSimilarity(aa, "GENRE", method.CUSTOM.value);
        double check  = dis2.get("X517339486");

        Iterator at = dis2.entrySet().iterator();

        
        System.out.println();
        
        HashMap<String, Double> dis3 = test.calculateAllSimilarity(aa, "GENRE", method.EUCLIDEAN.value);
        Iterator at2 = dis3.entrySet().iterator();
        int xxx2 = 0;
        while (at2.hasNext() && at.hasNext()){
            Map.Entry<String, Double> cc = (Map.Entry<String, Double>)at.next();
            Map.Entry<String, Double> cc2 = (Map.Entry<String, Double>)at2.next();
System.out.println("#" + xxx2 + " \t"+ cc.getKey() + "  1: " + cc.getValue() + " | 2: "+cc2.getValue());
            xxx2++;
        }
        
        System.out.println();
        System.out.println("Check! 0.1725? "+check);
        ArrayList<TrackInfo> meow = test.makePlaylist(aa , "GENRE", "PEARSON", 20);
        
        for (TrackInfo rr : meow){
            System.out.println(rr);
        }
        
    }
    
    public double taxi( ArrayList< Double > array1, ArrayList< Double > array2 ) {
        double ans = 0.0;
        double acc = 0.0;
        int x;
        
        for (x = 0; x < array1.size(); x++){
            // Crunch em
            double z = Math.abs( array2.get(x) - array1.get(x) );
            acc += z;
        }
        
        double zz = Math.max(acc/(x*x), 0.0);
        ans = Math.min(zz, 1.0);
        
       
        return ans;
    }
    
    public static String DB = "program_data.csv";
    
    public Prog2P3(){
        // Nothing really

        setTrackDB( new ArrayList() );
        setAlbums(  new ArrayList() );
        setArtists( new ArrayList() );
        setGenres( new ArrayList() );
        setTitles( new ArrayList() );
        setUserTrackMap( new HashMap() );
        setUsers( new ArrayList() );

        readInput( DB );
    }
	
	public Prog2P3(String FP){
            DB = FP;

            setTrackDB( new ArrayList() );
            setAlbums(  new ArrayList() );
            setArtists( new ArrayList() );
            setGenres( new ArrayList() );
            setTitles( new ArrayList() );
            setUserTrackMap( new HashMap() );
            setUsers( new ArrayList() );

            readInput( DB );
	}

   /**
    * Uses the distance methods to calculate the similarity between two
    * users with respect to a particular data field.
    * 
    * @param user1
    * @param user2
    * @param fieldName - USER, RANK, TITLE, ARTIST, ALBUM, GENRE, PLAYS
    * @param method    - Euclidean, PEARSON
    * @return
    */
   double calculateSimilarity( String user1, String user2, String fieldName, String method ){
       boolean b1 = fieldName.equals("TITLE");
       boolean b2 = fieldName.equals("ARTIST");
       boolean b3 = fieldName.equals("GENRE");
       boolean b4 = fieldName.equals("PLAYS");

       if (user1.equalsIgnoreCase(user2)){
           System.out.println(">> Same user detected << " + user1 + " & " + user2);
           return 1.0;
       }
       
       if (!(b1 || b2 || b3 || b4)){
           throw new IllegalArgumentException("Bad field argument: "+fieldName);
       }
       
       ArrayList< TrackInfo > log = this.getTrackDB();
       HashMap<String, Double> tbl1 = new HashMap();
       HashMap<String, Double> tbl2 = new HashMap();
       //       field, freq
       
       // Compile marks
       for (TrackInfo cur : log){
           String arg = cur.getFieldValue(fieldName);
           
           // Whos is it
           if ( user1.equals(cur.getUser()) ){
               double x = 1;
               if (tbl1.get(arg) != null){
                   x = tbl1.get(arg) + 1;
                   tbl1.put(arg,x);
               }
               tbl1.put(arg, x);
               
           }else if ( user2.equals(cur.getUser()) ){
               double x = 1;
               if (tbl2.get(arg) != null){
                   x = tbl2.get(arg) +1;
                   tbl2.put(arg,x);
               }
               tbl2.put(arg, x);
           }
           cur.getFieldValue(fieldName);
       }
       
       // compair strings for similarities
       ArrayList<Double> list1 = new ArrayList();
       ArrayList<Double> list2 = new ArrayList();
       // Find the larger map to read from
       ArrayList<String> keys = new ArrayList();
       keys.addAll(tbl1.keySet());
       //keys.addAll(tbl2.keySet());
       
       for (String we : tbl2.keySet()){
           if (!keys.contains(we)){
               // Add it
               keys.add(we);
           }
       }
       
       ArrayList<String> done = new ArrayList();

       int size = 0;
       // Make sure keys exists for both?
       for (String cur : keys){
           double c1 = 0;
           double c2 = 0;
           
           if (done.contains(cur)){
               // No need
               continue;
           }
           // How many for each key?
           if (tbl1.get(cur) != null){
               c1 = tbl1.get(cur);
           }
           if (tbl2.get(cur) != null){ 
               c2 = tbl2.get(cur);
           }

            list1.add(size, c1);

            list2.add(size, c2);

           size++;
           done.add(cur);
       }

       double ans = -1337.0;
       
       if ("EUCLIDEAN".equals( method )){
           ans = this.euclideanDistance(list1, list2);
       }else if ("PEARSON".equals( method )){
           ans = this.pearsonDistance(list1, list2);
       }else if ("OTHER".equals( method )){
           // Custom!
           ans = this.taxi(list1, list2);
       }else{
           throw new UnsupportedOperationException("No such function!");
       }
       
       
       if (Double.isNaN(ans) && false){
           // No!
           
           throw new Error("NaN");
       }
       
       return ans;
   }

   /**
    * Calculate the similarity scores between the specified user and every 
    * other user.
    * 
    * @param user
    * @param fieldName - USER, RANK, TITLE, ARTIST, ALBUM, GENRE, PLAYS
    * @param method    - Euclidean, PEARSON
    * @return
    */
   HashMap< String, Double > calculateAllSimilarity( String user, String fieldName, String method  ){
       // All to one
       ArrayList< TrackInfo > log       = this.getTrackDB();
       ArrayList<String> visitTbl       = new ArrayList();
       HashMap<String,Double> resTbl    = new HashMap();
       // Iterate
       for (TrackInfo cur : log){
           String otherU = cur.getUser();
           
           if ( !user.equals(otherU) && !visitTbl.contains(otherU) ){
                Double x = this.calculateSimilarity(user, otherU, fieldName, method);
                resTbl.put(otherU, x);
                visitTbl.add(otherU);
           }
       }

       return resTbl;
   }
   
   /**
    * Make a playlist for the specified user comprised of a specified
    * number of music tracks from the most similar users.
    * 
    * @param user
    * @param fieldName - USER, RANK, TITLE, ARTIST, ALBUM, GENRE, PLAYS
    * @param method    - Euclidean, PEARSON
    * @param numberOfTracks
    * @return
    */
    @Override
   ArrayList< TrackInfo > makePlaylist( String user, String fieldName, String method, int numberOfTracks ){
       
       ArrayList< TrackInfo > ans       = new ArrayList();
       ArrayList< TrackInfo > log       = this.getTrackDB();
       HashMap<String, Double> resTbl   = this.calculateAllSimilarity(user, fieldName, method);
       ArrayList< TrackInfo >  ours     = new ArrayList();
       ArrayList< String > ourGenres    = new ArrayList();
       for (TrackInfo aa : log){
           // Get OUR list
           if (aa.getUser().equals(user)){
               ours.add(aa);
               ourGenres.add(aa.getGenre());
           }
       }
       
       
       final int q = resTbl.size(); // wrote for debuging
       int size = 0;
       int NoT = 0;
       KeyValuePair2<String, Double>[] top = new KeyValuePair2[q];
       
       // fIND TOP MATCHES
       Iterator at =  resTbl.entrySet().iterator();
       while ( at.hasNext() ){
           Map.Entry<String, Double> ent = (Map.Entry)at.next();
           // Cycle thru all to find if best
           KeyValuePair2<String, Double> cur2 = new KeyValuePair2<String, Double>( ent.getKey(), ent.getValue() );
           top[size] = cur2;
           size++;
           
       }
       
       // Sort the top
       MergeSort h = new MergeSort();
       h.sort( top );
       
       //for (KeyValuePair2<String, Double> dis : top){
       //    System.out.println("CHECKS>"+dis.getKey()+" , "+dis.getValue());
      // }
       
       System.out.println();
       
       ArrayList<String> usher = new ArrayList<String>();
       
       // Scan thru all top 
       for (KeyValuePair2 n : top){
           
            for (TrackInfo cur : log){
                String name = (String)cur.getUser();
                String genre = (String) cur.getGenre();
                if (n.getKey().equals(name) && !usher.contains(n) && ourGenres.contains(genre)){
                    // Add these tracks
                    ans.add(cur);
                    usher.add( cur.getTitle() );
                    NoT++;
                    
                }
                
                if (NoT >= numberOfTracks){
                    // Were done here
                    return ans;
                }
            }

       }
       return ans;
   }
   
   class MergeSort<E extends Comparable<E>>{

        public void sort(E[] array) {
            // find median
            this.MergeSort( array, 0, array.length - 1 );
        }
        public E[] MergeSort(E[] A, int p, int r){
            if (p < r){
                int q  = (int)Math.floor( (p+r)/2 );
                MergeSort(A, p, q);
                MergeSort(A, q+1, r);
                Merge(A,p,q,r);
            }
            return A;
        }
        public E[] Merge(E[] A, int p, int q, int r){
            int n1 = q-p+1;
            int n2 = r-q;
            E[] L = (E[])new Comparable[n1];
            E[] R = (E[])new Comparable[n2];

            for (int i = 0; i < n1; i++){
                L[i] = A[p+i];
            }
            for (int i = 0; i < n2; i++){
                R[i] = A[p+1+i];
            }

            int i = 0, j = 0, k = 0;
            while (i < n1 && j < n2){
                if ( L[i].compareTo( R[j]) != 1 ){
                    A[k] = L[i];
                    i++;
                }else{
                    A[k] = R[j];
                    j++;
                }
                k++;
            }

            while (i < n1){
                A[k] = L[i];
                i++;
                k++;
            }
            while (j < n2){
                A[k] = R[j];
                j++;
                k++;
            }
            return A;
        }

        public E[] Merge2(E[] A, int p, int q, int r){
            int n1 = q-p+1;
            int n2 = r-q;
            E[] L = (E[]) new Comparable[n1+1];
            E[] R = (E[]) new Comparable[n2+1];

            for (int i = 0; i < n1; i++){
                L[i] = A[p+i];
            }
            for (int i = 0; i < n2; i++){
                R[i] = A[p+1+i];
            }
            return null; // WIP
        }
    }
   
   class KeyValuePair2< String, Double > implements Comparable{
   // -------------------------------------------------------------------
   // DATA MEMBERS WITH GETTER AND SETTERS
   // -------------------------------------------------------------------
   /**
    * The key
    */
   private String key = null;
   public String getKey( ) {
      return key;
   }
   public void setKey( String key ) {
      this.key = key;
   }
   
   /*
    * The value associated with the key
    */
   private Double value = null;
   public Double getValue( ) {
      return value;
   }
   public void setValue( Double value ) {
      this.value = value;
   }

   // -------------------------------------------------------------------
   // METHODS
   // -------------------------------------------------------------------

   // -------------------------------------------------------------------
   // CONSTRUCTORS
   // -------------------------------------------------------------------
   
   /**
    * Constructor
    * @param key
    * @param value
    */
   public KeyValuePair2 ( String key, Double value ) {
      setKey( key );
      setValue( value );
   }

    @Override
    public int compareTo(Object t) {
        if (!(t instanceof KeyValuePair2)){
            throw new Error("Bad cast");
        }
        KeyValuePair2 arg = (KeyValuePair2)t;
        // checks
        int ans;
        ans = ((Comparable)this.value).compareTo(arg.value);

        return ans;
    }
   
}

}
