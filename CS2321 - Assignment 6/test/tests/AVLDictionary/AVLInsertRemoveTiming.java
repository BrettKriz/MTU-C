package tests.AVLDictionary;


import org.junit.*;
import jug.*;
import cs2321.*;
import net.datastructures.*;
import java.util.Random;
import java.util.Iterator;

public class AVLInsertRemoveTiming implements DataSeries {

	private AVLDictionary<Integer, Integer> TARGET = init();
	private AVLDictionary<Integer, Integer> T = init();

	public AVLDictionary<Integer, Integer> init() {
		return new AVLDictionary<Integer, Integer>();
	}

       
	public double[] xAxis() throws Throwable {
		
           double[] xAxis = {1, 2, 4, 8, 16, 32, 64, 128, 256, 512, 1024, 2048, 4096 };
           return xAxis;  
         
	}
        
        
	public double[] yAxis() throws Throwable {
		
		int REPS = 1000;
		double[] xAxis = xAxis();
		double[] yAxis = new double[xAxis.length];

		AVLDictionary<Integer, Integer> lAVL = new AVLDictionary<Integer, Integer>();

		for ( int lIndex = 0; lIndex < xAxis.length; lIndex++ )
		{
		    long start = System.currentTimeMillis();

		    for ( int lIteration = 0; lIteration < REPS; lIteration++ )
		    {
			for ( int lInsertIndex = 1; lInsertIndex < xAxis[lIndex]; lInsertIndex++ )
			{
				lAVL.insert( lInsertIndex, lInsertIndex );
			}
			for ( int lInsertIndex = 1; lInsertIndex < xAxis[lIndex]; lInsertIndex++ )
			{
				lAVL.remove( lAVL.find( lInsertIndex ) );
			}			
		    }

		    
		    yAxis[lIndex] = (double)(System.currentTimeMillis() - start) / REPS;

		}
		return yAxis;
         
	}
}
