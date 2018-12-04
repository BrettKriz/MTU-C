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
public class LogFileTest<T extends LookupTable> {
    
    public LogFileTest() {
        
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
        T  arg          = (T)new LookupTable();
        
        this.noitems(arg);
        this.oneitem(arg);
                    arg = (T)new LookupTable();
        this.twoitem(arg);
                    arg = (T)new LookupTable();
        this.multiitem(arg);
                    arg = (T)new LookupTable();
        this.multiitem2(arg);
                    arg = (T)new LookupTable();
        this.onekitems(arg);
    }
    
    public void noitems(T arg ) {
        //Dictionary arg;
        
        // Empty Test CASE
        //arg = new Logfile<String,String>();
        assertTrue(arg.size() == 0);
        assertTrue("Basic Empty Test",arg.isEmpty() == true);
        Entry e = arg.insert("50", "A");
        assertEquals(e.getKey(),"50");
        assertEquals(e.getValue(),"A");
        arg.remove(e);
        assertTrue(arg.size() == 0);
        assertEquals("Find imaginary",arg.find(1),null);
        
        
                
    }
    
    public void oneitem(T arg){
        //Logfile arg;
        // One Item List
        //arg = new Logfile<String,String>();
       
        arg.insert("1", "A");
        assertTrue(arg.size() == 1);
        assertTrue(arg.isEmpty() == false);
        assertTrue(arg.find("1").getValue().equals("A"));
        LinkedSequence<Entry<String,String>> ans = new LinkedSequence<Entry<String,String>>();
        ans.addFirst(new QEntry("1","A"));
        LinkedSequence<Entry<String,String>> t1 = (LinkedSequence<Entry<String,String>>)arg.findAll("1");
        //assertTrue("find All is right?",(t1).getFirst().equals(ans.first()));
        
        arg.insert("1", "B");
        QEntry e2 = new QEntry("1","B");
        ans.addLast(e2);
        
        assertTrue(((LinkedSequence<Entry<String,String>>)arg.findAll("1")).size() == 2);
        System.out.println(arg.findAll("1").toString());
        //assertTrue("find All 2 1s is right?",((LinkedSequence<Entry<String,String>>)arg.findAll("1")).equals(ans));
        System.out.println("<>insert, remove, size");
        arg.insert("1", "C");
        Entry<String,String> q1 = arg.remove(e2);
        
        ans.removeLast();
        ans.addLast(new QEntry("1","C"));
        assertTrue("Size2? "+arg.size(),arg.size() == 2);
        Iterable i2 = arg.findAll("1");
        System.out.println( i2 );
        //assertTrue("insert, remove, size",((LinkedSequence<Entry<String,String>>)arg.findAll("1")).equals(ans));
        
    }
    
    public void twoitem(T arg){
        //Logfile arg;
        // 2 Item List
        arg.insert("1", "A");
        arg.insert("2", "B");
        
        assertTrue("<> " + arg.toString(),arg.size() == 2);
        
        System.out.println("<> " + arg.toString());
        
    }
    
    public void multiitem(T arg){
       arg.insert("3", "C");
       arg.insert("1", "A");
       arg.insert("2", "B");
       arg.insert("1", "D");
       
       assertTrue("Size & Entries: "+arg.entries().toString(),arg.size() == 4);
       
       System.out.println("Entries: "+arg.toString());
       
    }
    
    public void multiitem2(T arg){
       arg.insert("3", "C");
       arg.insert("1", "A");
       arg.insert("2", "B");
       arg.insert("1", "D");
       
       arg.remove(arg.find("2"));
       
               
       
       assertTrue("Size & Entries: "+arg.entries().toString(),arg.size() == 3);
       
       System.out.println("Entries: "+arg.entries().toString());
       
    }
    
    public void onekitems(T arg){
        Random r = new Random();
        LinkedSequence<Integer> keys = new LinkedSequence<Integer>();
        for (int x = 0; x < 1000; x++){
            int n = r.nextInt(123);
            keys.addLast(n);
            arg.insert(n, x);
        }
        // THEN
        System.out.println("Find all elements");
        for (Integer cur : keys){
            if (cur != null){
                //System.out.println(cur.toString()+" -> "+arg.findAll(cur));
                assertNotNull( cur.toString()+" = ", arg.find(cur) );
                assertTrue(arg.find(cur).getKey().equals(cur));
            }
            
        }
    }
    
}
