/**
 * @author Leo Ureel
 * 
 * This class is used to store information about music tracks.
 * The following info is stored:
 * user: An ID associated with the user who entered the track info.
 * rank: The rank (1-10) given to the music by the user.
 * title: The title of the music track.
 * artist: The name of the artist or group who recorded the track.
 * album: The name of the album (if any) in which the track was released.
 * genre: The genre of the music track.
 * plays: The number of times the user has played the music track.
 */

public class TrackInfo {
   // -------------------------------------------------------------------
   // DATA MEMBERS WITH GETTERS AND SETTERS
   // -------------------------------------------------------------------
   /*
    * String user: An ID associated with the user who entered the track info.
    */
   private String user = "";
   public String getUser( ) { return user; }
   public void setUser( String value ) { user = value; }
   
   /*
    * int rank: The rank (1-10) given to the music by the user.
    */
   private int rank = 0;
   public int getRank( ) { return rank; }
   public void setRank( int value ) { rank = value; }
   
   /*
    * String title: The title of the music track.
    */
   private String title = "";
   public String getTitle( ) { return title; }
   public void setTitle( String value ) { title = value; }
   
   /*
    * String artist: The name of the artist or group who recorded the track.
    */
   private String artist = "";
   public String getArtist( ) { return artist; }
   public void setArtist( String value ) { artist = value; }
   
   /*
    * String album: The name of the album (if any) in which the track was released.
    */
   private String album = "";
   public String getAlbum( ) { return album; }
   public void setAlbum( String value ) { album = value; }
   
   /*
    * String genre: The genre of the music track.
    */
   private String genre = "";
   public String getGenre( ) { return genre; }
   public void setGenre( String value ) { genre = value; }
   
   /*
    * int plays: The number of times the user has played the music track.
    */
   private int plays = 0;
   public int getPlays( ) { return plays; }
   public void setPlays( int value ) { plays = value; }
   
   /**
    * Gets the value of the requested field.
    * @param fieldName - USER, RANK, TITLE, ARTIST, ALBUM, GENRE, PLAYS
    * @return
    */
   public String getFieldValue( String fieldName ) {
      switch ( fieldName.toUpperCase( ) ) {
         case "USER"  : return getUser( );
         case "RANK"  : return "" + getRank( );
         case "TITLE" : return getTitle( );
         case "ARTIST": return getArtist( );
         case "ALBUM" : return getAlbum( );
         case "GENRE" : return getGenre( );
         case "PLAYS" : return "" + getPlays( );
      }
      return null;
   }
   
   /* (non-Javadoc)
    * @see java.lang.Object#toString()
    */
   public String toString( ) {
        String ans = "";
        String s = " , ";
        ans += "<"+getUser()+s+getRank()+s+getTitle()+s+getArtist()+s+getAlbum()+s+getGenre()+s+getPlays()+">";
       
        return  ans;
              
              
              
   }

   /*
    * CONSTRUCTOR: This method is used to initialize the data members when
    * creating a new instance of this class.
    * USAGE: new TrackInfo( args... )
    */
   public TrackInfo( String user, String title, String artist, String album, String genre, int rank, int plays ) {
      setUser( user );
      setRank( rank );
      setTitle( title );
      setArtist( artist );
      setAlbum( album );
      setGenre( genre );
      setPlays( plays );
   }
   
}
// END OF TrackInfo CLASS -----------------------------------------------
