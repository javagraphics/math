/*
 * @(#)MathGTest.java
 *
 * $Date: 2014-03-13 09:15:48 +0100 (Do, 13 Mär 2014) $
 *
 * Copyright (c) 2011 by Jeremy Wood.
 * All rights reserved.
 *
 * The copyright of this software is owned by Jeremy Wood. 
 * You may not use, copy or modify this software, except in  
 * accordance with the license agreement you entered into with  
 * Jeremy Wood. For details see accompanying license terms.
 * 
 * This software is probably, but not necessarily, discussed here:
 * https://javagraphics.java.net/
 * 
 * That site should also contain the most recent official version
 * of this software.  (See the SVN repository for more details.)
 */
package com.bric.math;

import java.util.Random;

import junit.framework.TestCase;

/** Tests that measure the accuracy of the MathG calculations
 * based on values returned by <code>java.lang.Math</code>.
 *
 */
public class MathGTest extends TestCase {
	
	public void testSine() {
		for(double range = .1; range<2e10; range*=10) {
			double[] values = createRandomValues(-range, range);

			double maxError = -1;
			int maxIndex = -1;
			for(int b = 0; b<values.length; b++) {
				double error = MathG.sin01(values[b])-Math.sin(values[b]);
				if(error<0) error = -error;
				if(error>maxError) {
					maxIndex = b;
					maxError = error;
				}
			}
			assertTrue("error for MathG.sin01("+values[maxIndex]+")="+MathG.sin01(values[maxIndex])+" is too large ("+maxError+")", maxError<0.010792 );
			
			maxError = -1;
			maxIndex = -1;
			for(int b = 0; b<values.length; b++) {
				double error = MathG.sin00004(values[b])-Math.sin(values[b]);
				if(error<0) error = -error;
				if(error>maxError) {
					maxIndex = b;
					maxError = error;
				}
			}
			assertTrue("error for MathG.sin00004("+values[maxIndex]+")="+MathG.sin00004(values[maxIndex])+" is too large ("+maxError+")", maxError<3.58785E-5 );
		}
	}

	public void testArcCosine() {
		double[] values = createRandomValues(-1, 1);

		double maxError = -1;
		int maxIndex = -1;
		for(int a = 0; a<values.length; a++) {
			double error = MathG.acos(values[a])-Math.acos(values[a]);
			if(error<0) error = -error;
			if(error>maxError) {
				maxIndex = a;
				maxError = error;
			}
		}
		assertTrue("error for MathG.acos("+values[maxIndex]+")="+MathG.acos(values[maxIndex])+" is too large ("+maxError+")", maxError<4.20256E-5 );
	}
	
	public void testCeil() {
		for(double range = .1; range<1e10; range*=10) {
			double[] values = createRandomValues(-range, range);

			double maxError = -1;
			int maxIndex = -1;
			for(int b = 0; b<values.length; b++) {
				double error = MathG.ceilInt(values[b])-Math.ceil(values[b]);
				if(error<0) error = -error;
				if(error>maxError) {
					maxIndex = b;
					maxError = error;
				}
			}
			assertTrue("error for MathG.ceilInt("+values[maxIndex]+")="+MathG.ceilInt(values[maxIndex])+" is too large ("+maxError+")", maxError<.00000000000001 );
			
			//ceilDouble vs ceilInt is a kind of failed experiment in efficiency,
			//but the return values should be identical:
			maxError = -1;
			maxIndex = -1;
			for(int b = 0; b<values.length; b++) {
				double error = MathG.ceilDouble(values[b])-Math.ceil(values[b]);
				if(error<0) error = -error;
				if(error>maxError) {
					maxIndex = b;
					maxError = error;
				}
			}
			assertTrue("error for MathG.ceilDouble("+values[maxIndex]+")="+MathG.ceilDouble(values[maxIndex])+" is too large ("+maxError+")", maxError<.00000000000001 );
		}
	}
	
	public void testFloor() {
		for(double range = .1; range<1e10; range*=10) {
			double[] values = createRandomValues(-range, range);

			double maxError = -1;
			int maxIndex = -1;
			for(int b = 0; b<values.length; b++) {
				double error = MathG.floorInt(values[b])-Math.floor(values[b]);
				if(error<0) error = -error;
				if(error>maxError) {
					maxIndex = b;
					maxError = error;
				}
			}
			assertTrue("error for MathG.floorInt("+values[maxIndex]+")="+MathG.floorInt(values[maxIndex])+" is too large ("+maxError+")", maxError<.00000000000001 );
			
			maxError = -1;
			maxIndex = -1;
			for(int b = 0; b<values.length; b++) {
				double error = MathG.floorDouble(values[b])-Math.floor(values[b]);
				if(error<0) error = -error;
				if(error>maxError) {
					maxIndex = b;
					maxError = error;
				}
			}
			assertTrue("error for MathG.floorDouble("+values[maxIndex]+")="+MathG.floorDouble(values[maxIndex])+" is too large ("+maxError+")", maxError<.00000000000001 );
		}
	}
	
	public void testRound() {
		for(double range = .1; range<1e10; range*=10) {
			double[] values = createRandomValues(-range, range);

			double maxError = -1;
			int maxIndex = -1;
			for(int b = 0; b<values.length; b++) {
				double error = MathG.roundInt(values[b])-Math.round(values[b]);
				if(error<0) error = -error;
				if(error>maxError) {
					maxIndex = b;
					maxError = error;
				}
			}
			assertTrue("error for MathG.roundInt("+values[maxIndex]+")="+MathG.roundInt(values[maxIndex])+" is too large ("+maxError+")", maxError<.00000000000001 );
			
			maxError = -1;
			maxIndex = -1;
			for(int b = 0; b<values.length; b++) {
				double error = MathG.roundDouble(values[b])-Math.round(values[b]);
				if(error<0) error = -error;
				if(error>maxError) {
					maxIndex = b;
					maxError = error;
				}
			}
			assertTrue("error for MathG.roundDouble("+values[maxIndex]+")="+MathG.roundDouble(values[maxIndex])+" is too large ("+maxError+")", maxError<.00000000000001 );
		}
	}
	
	private double[] createRandomValues(double min,double max) {
		Random r = new Random(0);
		double range = max-min;
		int intValues = (int)range;
		if(intValues>1000) intValues = 1000;
		double[] array = new double[50000+intValues+2];
		array[0] = min;
		array[1] = max;
		for(int a = 0; a<intValues; a++) {
			array[a+2] = (int)(min+a+.5);
		}
		for(int a = intValues+2; a<array.length; a++) {
			array[a] = min+range*r.nextDouble();
		}
		return array;
	}
}
