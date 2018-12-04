/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Test.Package;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import java.util.Random;
import cs2321.*;
import net.datastructures.*;
import org.junit.runner.JUnitCore;

/**
 *
 * @author Brett Kriz
 */
public class HashMapTest<T extends HashMap> {
    
    public static final int RANGE   = 123;
    public static final int SIZE    = 50;
    
    public HashMapTest() {
        
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void full(){
        int size = 100;
        T  arg          = (T)new HashMap();
        
        this.noitems(arg);
        this.oneitem(arg);
                    arg = (T)new HashMap();
        this.twoitem(arg);
                    arg = (T)new HashMap();
        this.multiitem(arg);
                    arg = (T)new HashMap();
        this.multiitem2(arg);
                    arg = (T)new HashMap(SIZE);
        this.onekitems(arg);
        this.freeform(arg);
    }
    
    public void freeform(T arg){
        // Hmmmmm
        //this.onekitems(arg);
        Integer put = (Integer)arg.put(50, 1337); // v
        int h = arg.hash(50);
        int h2 = (Integer)arg.get(50);// v
        
        
        System.out.println( arg.toString() );
        
        
    }
    
    public void noitems(T arg ) {
        //Dictionary arg;
        
        // Empty Test CASE
        //arg = new Logfile<String,String>();
        assertTrue(arg.size() == 0);
        assertTrue("Basic Empty Test",arg.isEmpty() == true);
        String e = (String)arg.put("50", "A");
        assertNull(e);
        // PUTS
        arg.put("50", "B");
        arg.remove("50");
        //0
        
        assertTrue(arg.size() == 0);
        assertEquals("Find imaginary",arg.get("1"),null);
        
        
                
    }
    
    public void oneitem(T arg){
        //Logfile arg;
        // One Item List
        //arg = new Logfile<String,String>();
       
        arg.put("1", "A");
        assertTrue(arg.size() == 1);
        assertTrue(arg.isEmpty() == false);
        
        assertTrue(arg.get("1").equals("A"));
        arg.remove("1"); // Verify removal
        assertNull(arg.remove("1"));

        String res3 = (String)arg.put("1", "B");
        QEntry e2 = new QEntry("1","B");

        String res4 = (String)arg.get("1");
        assertTrue(res4.equals("B"));
        
        //assertTrue(((LinkedSequence<Entry<String,String>>)arg.findAll("1")).size() == 2);
        //System.out.println(arg.findAll("1").toString());
        
        
    }
    
    public void twoitem(T arg){
        //Logfile arg;
        // 2 Item List
        arg.put("1", "A");
        arg.put("2", "B");
        
        assertTrue("<Set> " + arg.toString(),arg.size() == 2);
        
        System.out.println("<> " + arg.toString());
        
    }
    
    public void multiitem(T arg){
       arg.put("3", "C");
       arg.put("1", "A");
       arg.put("2", "B");
       arg.put("1", "D");
       
       assertTrue("Size & Entries: "+arg.entrySet().toString(),arg.size() == 3);
       
       System.out.println("Entries: "+arg.entrySet().toString());
       
    }
    
    public void multiitem2(T arg){
       arg.put("3", "C");
       arg.put("1", "A"); // 1
       arg.put("2", "B");
       arg.put("1", "D"); // Overrides 1
       
       arg.remove("2");
       
       assertTrue("Size & Entries: "+arg.entrySet().toString(),arg.size() == 2);
       
       System.out.println("Entries (W/o 2): "+arg.entrySet().toString());
       
    }
    
    public void onekitems(T arg){ // N N
        Random r = new Random();
        LinkedSequence<Integer> keys = new LinkedSequence<Integer>();
        keys.addFirst(123);
        for (int x = 0; x < 1000-0; x++){
            Integer n = r.nextInt(RANGE);
            keys.addLast(n);
            arg.put(n, x);
        }
        // THEN
        System.out.println("Find all elements");
        for (Integer cur : keys){
            if (cur != null){
                System.out.println(cur.toString()+" -> "+arg.get(cur));
                assertNotNull( cur.toString()+" = ", arg.get(cur) );
               
            }
            
        }
        for (Integer cur : keys){
            if (cur != null){
                //System.out.println(cur.toString()+" -> "+arg.);
                arg.remove(cur);
            }
        }
                assertTrue(arg.isEmpty() == true);
    }
    
}
