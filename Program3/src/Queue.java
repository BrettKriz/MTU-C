import java.util.ArrayList;
import java.util.Iterator;

// This code developed in class is buggy
// and will need modification to work for
// the programming assignment.
// Among other things, the enqueue method is nonfunctional
public class Queue<V> implements Iterable<V> {
   @Override
   public Iterator< V > iterator( ) {
      ArrayList<V> list = new ArrayList<V>(  );
      if ( !isEmpty() ) {
         Node current = front;
         do {
            list.add( current.getValue( ) );
            current = current.getNext( );
         } while ( current.getNext( ) != null );
      }
      return list.iterator();
   }

   public class Node {
      V value = null;
      Node next = null;

      public V getValue( ) {
         return value;
      }

      public void setValue( V value ) {
         this.value = value;
      }

      public Node getNext( ) {
         return next;
      }

      public void setNext( Node next ) {
         this.next = next;
      }

      public Node( V value ) {
         this.value = value;
      }

      public Node( V value, Node next ) {
         this.value = value;
         this.next = next;
      }
   }

   Node front = null;
   Node back = null;

   int size = 0;
   int maxSize = 0;

   public void makeEmpty( ) {
      front = back = null;
      size = 0;
   }

   public void enqueue( V value ) {
      Node newNode = new Node( value );
      if ( isFull( ) ) {
         throw new RuntimeException( "The Queue Is Full" );
      }
      if ( isEmpty() ) {
         front = newNode;
         back = newNode;
      }
      back.setNext( newNode );
      back = newNode;
      size++;
   }

   public V dequeue( ) {
      if ( isEmpty() ) {
         throw new RuntimeException( "The Queue is Empty." );
      }
      Node resultNode = front;

      if ( size == 0 ) {
         front = null;
         back = null;
      } else {
         front = front.getNext( );
      }

      size--;

      return resultNode.getValue();
   }

   public V front( ) {
      if ( isEmpty() ) {
         throw new RuntimeException( "The Queue is Empty" );
      }
      return front.getValue();
   }

   public boolean isEmpty( ) {
      return size == 0;
   }

   public boolean isFull( ) {
      return size == maxSize;
   }

   public int size( ) {
      return size;
   }

   public String toString( ) {
      String build = "";

      Node current = front;
      if (current != null) {
        while(null != current.getNext()  ) {
           build = current.getValue() + ", " + build;
           current = current.getNext();
        }
      }
      return "[" + build + "]";
   }

   public Queue( int maxSize ) {
      this.maxSize = maxSize;
   }
}
