/**
 * @author Leo Ureel
 * 
 * Extend this class and flesh-out the abstract methods.
 * 
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.nio.charset.StandardCharsets;

/*
 * This class contains methods and data members to support your development
 * of a music recommendation generator.
 */
public abstract class AbstractClass {
   // -------------------------------------------------------------------
   // STUDENT CODE
   // THE METHODS BELOW NEED TO BE FLESHED OUT IN YOUR CLASS
   // -------------------------------------------------------------------
   
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
   abstract double calculateSimilarity( String user1, String user2, String fieldName, String method );   

   /**
    * Calculate the similarity scores between the specified user and every 
    * other user.
    * 
    * @param user
    * @param fieldName - USER, RANK, TITLE, ARTIST, ALBUM, GENRE, PLAYS
    * @param method    - Euclidean, PEARSON
    * @return
    */
   abstract HashMap< String, Double > calculateAllSimilarity( String user, String fieldName, String method  );
   
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
   abstract ArrayList< TrackInfo > makePlaylist( String user, String fieldName, String method, int numberOfTracks );

   // END OF STUDENT SECTION
   // THE DATA AND METHODS COMPRISE THE API FOR YOUR PROJECT.
   // -------------------------------------------------------------------
   
   // -------------------------------------------------------------------
   // DATA MEMBERS
   // -------------------------------------------------------------------
   /*
    * trackDB maintains a list of all tracks.
    */
   private ArrayList< TrackInfo > trackDB = new ArrayList();

   public ArrayList< TrackInfo > getTrackDB( ) {
      return trackDB;
   }

   public void setTrackDB( ArrayList< TrackInfo > trackDB ) {
      this.trackDB = trackDB;
   }

   /*
    * users is a list of all users in the trackDB
    * NO DUPLICATES
    */
   private ArrayList< String > users;

   public ArrayList< String > getUsers( ) {
      return users;
   }

   public void setUsers( ArrayList< String > users ) {
      this.users = users;
   }

   /*
    * titles is a list of all titles in the trackDB
    * NO DUPLICATES
    */
   private ArrayList< String > titles;

   public ArrayList< String > getTitles( ) {
      return titles;
   }

   public void setTitles( ArrayList< String > titles ) {
      this.titles = titles;
   }

   /*
    * artists is a list of all artists in the trackDB
    * NO DUPLICATES
    */
   private ArrayList< String > artists;

   public ArrayList< String > getArtists( ) {
      return artists;
   }

   public void setArtists( ArrayList< String > artists ) {
      this.artists = artists;
   }

   /*
    * artists is a list of all artists in the trackDB
    * NO DUPLICATES
    */
   private ArrayList< String > albums;

   public ArrayList< String > getAlbums( ) {
      return albums;
   }

   public void setAlbums( ArrayList< String > albums ) {
      this.albums = albums;
   }

   /*
    * genres is a list of all genres in the track DB
    * NO DUPLICATES
    */
   private ArrayList< String > genres;

   public ArrayList< String > getGenres( ) {
      return genres;
   }

   public void setGenres( ArrayList< String > genres ) {
      this.genres = genres;
   }

   /*
    * userTrackMap: An instance level variable that contains a HashMap.
    * The HashMap is used to associate a list of TrackInfo with a user.
    */
   private HashMap< String, ArrayList< TrackInfo > > userTrackMap;

   public HashMap< String, ArrayList< TrackInfo > > getUserTrackMap( ) {
      return userTrackMap;
   }

   public void setUserTrackMap(
                  HashMap< String, ArrayList< TrackInfo > > userTrackMap ) {
      this.userTrackMap = userTrackMap;
   }

   // -------------------------------------------------------------------
   // LOAD DATA SET
   // -------------------------------------------------------------------

   /**
    * If the value does not exist in the list,
    * Add value to list in sorted order
    * @param list
    * @param value
    */
   public void addIfNotExists( ArrayList< String > list, String value ) {
      // System.out.println(">> ADDING "+list+" and "+value);
      if (value != null && !"".equals( value ) && !list.contains( value.toUpperCase( ) )) {
         boolean added = false;
         for( int index = 0; index < list.size( ); index++ ) {
            if (list.get( index ).compareTo( value.toUpperCase( ) ) < 0) {
               list.add( index, value.toUpperCase( ) );
               added = true;
               break;
            }
         }
         if (!added) {
            list.add( value.toUpperCase( ) );
         }
      }
   }

   /**
    * Add track information to the supplied HashMap.
    * @param map
    * @param key
    * @param track
    */
   void addTrack( HashMap< String, ArrayList< TrackInfo > > map, String key,
                  TrackInfo track ) {
      ArrayList< TrackInfo > list = map.get( key );
      if (list == null) {
         list = new ArrayList< TrackInfo >( );
      }
      list.add( track );
      map.put( key, list );
   }

   /**
    * Method to read in track information from the supplied file.
    * @param filename
    */
   void readInput( String filename ) {
      File file = new File( filename );
      try {
          //StandardCharsets.UTF_8
          String cs = "UTF8";
          
         Scanner fileScan = new Scanner( file, cs );
         fileScan.nextLine( );
         while( fileScan.hasNext( ) ) {
            String[ ] fields = fileScan.nextLine( ).split( ", " );
            
            String user = fields[ 0 ].trim( ).toUpperCase( );
            Integer rank = Integer.valueOf( fields[ 1 ].trim( ) );
            String title = fields[ 2 ].trim( ).toUpperCase( );
            String artist = fields[ 3 ].trim( ).toUpperCase( );
            String album = fields[ 4 ].trim( ).toUpperCase( );
            String genre = fields[ 5 ].trim( ).toUpperCase( );
            
            Integer plays = Integer.valueOf( fields[ 6 ].trim( ) );
            TrackInfo track = new TrackInfo( user, title, artist, album, genre, rank, plays );
            
            //System.out.println(track);
            
            trackDB.add( track );
            // Adds fo us
            addIfNotExists( users, user );
            addIfNotExists( titles, title );
            addIfNotExists( artists, artist );
            addIfNotExists( genres, genre );
            addTrack( userTrackMap, user, track );
         }
         fileScan.close( );
      } catch( FileNotFoundException e ) {
         e.printStackTrace( );
      }
   }

   // -------------------------------------------------------------------
   // Calculate Similarity
   // -------------------------------------------------------------------
   /**
    * Calculates the likelihood that two users share the same music tastes
    * using the Euclidean Distance algorithm.
    * @param array1
    * @param array2
    * @return
    */
   public double euclideanDistance( ArrayList< Double > array1, ArrayList< Double > array2 ) {
      double sum = 0.0;
      for( int i = 0; i < array1.size( ); i++ ) {
         sum += Math.pow( ( array1.get( i ) - array2.get( i ) ), 2.0 );
      }
      return 1.0 / ( 1.0 + Math.sqrt( sum ) );
   }

   /**
    * Calculates the likelihood that two users share the same music tastes
    * using the Pearson Distance algorithm.
    * @param array1
    * @param array2
    * @return
    */
   public double pearsonDistance( ArrayList< Double > array1, ArrayList< Double > array2 ) {
      double mean1 = 0.0, mean2 = 0.0;
      for( int i = 0; i < array1.size( ); i++ ) {
         mean1 += array1.get( i );
         mean2 += array2.get( i );
      }
      mean1 /= array1.size( );
      mean2 /= array2.size( );
      double sumXY = 0.0, sumX2 = 0.0, sumY2 = 0.0;
      for( int i = 0; i < array1.size( ); i++ ) {
         sumXY += ( ( array1.get( i ) - mean1 ) * ( array2.get( i ) - mean2 ) );
         sumX2 += Math.pow( array1.get( i ) - mean1, 2.0 );
         sumY2 += Math.pow( array2.get( i ) - mean2, 2.0 );
      }
      return ( 1.0 + ( sumXY / ( Math.sqrt( sumX2 ) * Math.sqrt( sumY2 ) ) ) ) / 2.0;
   }
   
}
// END OF ABSTRACT CLASS ================================================